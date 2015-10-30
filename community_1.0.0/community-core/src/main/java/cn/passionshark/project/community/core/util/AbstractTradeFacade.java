package cn.passionshark.project.community.core.util;

import org.springframework.beans.factory.annotation.Autowired;

import cn.passionshark.project.community.api.dto.common.ResponseDto;
import cn.passionshark.project.community.api.enums.ExceptionEnum;
import cn.passionshark.project.community.api.exception.CommunityException;
import cn.passionshark.project.community.common.log.Logger;
import cn.passionshark.project.community.common.log.LoggerFactory;
import cn.passionshark.project.community.common.util.StringUtil;
import cn.passionshark.project.community.core.dto.BaseCommunityRequestDto;
import cn.passionshark.project.community.core.dto.BaseCommunityResponseDto;


/**
 * Abstract trade request layer, including operation:
 * <ul>
 * <li>receive request</li>
 * <li>common check</li>
 * <li>signature verification and sign response</li>
 * <li>business(Not implemented)</li>
 * <li>send response</li>
 * </ul>
 *
 * @author pierre
 * @version $ v1.0 June 25, 2015 $
 */
public abstract class AbstractTradeFacade {
    private static final Logger logger = LoggerFactory.getLogger("TRADE", AbstractTradeFacade.class);


    /**
     * Callback interface for trade business.
     */
    public interface TradeCallback<R, T> {
        /**
         * Gets called by AbstractTradeFacade.execute within a trade context.
         *
         * @param request
         *            transaction request
         * @return business result
         */
        T doInTransaction(R request);
    }

    /**
     * Execute the action specified by the given callback object within a TradeContext.
     *
     * @param request
     * @param callback
     * @return
     */
    public <R extends BaseCommunityRequestDto, T extends BaseCommunityResponseDto> ResponseDto<T> execute(R request,
            TradeCallback<R, T> callback) {
        logger.info("Receive trade request: {}", request);

        ResponseDto<T> response = new ResponseDto<T>();
        try {
            // 1. pre process
            String securityKey = preprocess(request);

            // 2. do business logic
            T result = callback.doInTransaction(request);

            // 3. post process
            postprocess(result, securityKey);
            response.succ(result);
        } catch (CommunityException e) {
            logger.error("Failed to do transaction, caused by " + e, e);
            response.err(e);
        } catch (Exception e) {
            logger.error("Unexpected system err caught, caused by " + e, e);
            response.err(e);
        } finally {
            CommunityContext.clear();
        }

        logger.info("Send community response: {}", response);
        return response;
    }

    

    /**
     * System pre check, check the external system
     * 
     * @param tradeRequestDto
     * @return
     * @throws Exception
     */
    private String preprocess(BaseCommunityRequestDto tradeRequestDto) throws Exception {
        // 1. parameter validation
        String err = BaseInfoValidator.validate(tradeRequestDto, true);
        if (!StringUtil.isEmpty(err)) {
            throw new CommunityException(ExceptionEnum.ARG_ERR, err);
        }

        // 2. merchant access
        String securityKey = null ;//= merchantInfoService.accessMerchant(tradeRequestDto.getMerchantCode(),
//                tradeRequestDto.getBizCode());
//        if (StringUtil.isEmpty(securityKey)) {
////            throw new CommunityException(ExceptionEnum.MERCHANT_SECURITY_KEY_ERR);
//        }

        // 3. sign
        if (!SignUtil.checkSign(CommunityContext.getSignedSource(), tradeRequestDto.getSign(), securityKey)) {
            throw new CommunityException(ExceptionEnum.SIGN_CHECK_ERR);
        }
        return securityKey;
    }


    private void postprocess(BaseCommunityResponseDto tradeResponseDto, String securityKey) {
        tradeResponseDto.setSign(SignUtil.creatResSignBean(tradeResponseDto, securityKey));
    }
}
