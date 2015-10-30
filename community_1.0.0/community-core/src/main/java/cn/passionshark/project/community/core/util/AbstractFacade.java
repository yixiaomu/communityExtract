package cn.passionshark.project.community.core.util;

import cn.passionshark.project.community.api.dto.common.BaseRequestDto;
import cn.passionshark.project.community.api.dto.common.BaseResponseDto;
import cn.passionshark.project.community.api.enums.ExceptionEnum;
import cn.passionshark.project.community.api.exception.CommunityException;
import cn.passionshark.project.community.common.log.Logger;
import cn.passionshark.project.community.common.log.LoggerFactory;
import cn.passionshark.project.community.common.util.StringUtil;

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
public abstract class AbstractFacade {
    private static final Logger logger = LoggerFactory.getLogger("TRADE", AbstractFacade.class);


    /**
     * Callback interface for trade business.
     */
    public interface Callback<R, T> {
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
    public <R extends BaseRequestDto, T extends BaseResponseDto> T execute(R request, T response,
    		Callback<R, T> callback) {
        logger.info("Receive hessian request: {}", request);

//        response.setBizCode(request.getBizCode());
//        response.setMerchantCode(request.getMerchantCode());
//        response.setVersion(request.getVersion());

        try {
            // 1. pre process
            preprocess(request);
            
            // 2. do business logic
            response = callback.doInTransaction(request);
            
        }catch(CommunityException e){
        	logger.error("Failed to do transaction, caused by " + e, e);
            response.buildTradeExceptionField(e);
        }catch(Exception e){
        	logger.error("Unexpected system err caught, caused by " + e, e);
            response.buildSysExceptionField(e);
        }finally{
        	CommunityContext.clear();
        }

        logger.info("Send trade response: {}", response);
        return response;
    }

    

    /**
     * System pre check, check the external system
     * 
     * @param tradeRequestDto
     * @return
     * @throws Exception
     */
    private void preprocess(BaseRequestDto requestDto) throws Exception {
        // 1. parameter validation
        String err = BaseInfoValidator.validate(requestDto, true);
        if (!StringUtil.isEmpty(err)) {
            throw new CommunityException(ExceptionEnum.ARG_ERR, err);
        }
    }
}
