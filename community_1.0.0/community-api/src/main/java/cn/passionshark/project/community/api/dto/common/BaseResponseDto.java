package cn.passionshark.project.community.api.dto.common;

import java.io.Serializable;

import cn.passionshark.project.community.api.enums.ExceptionEnum;
import cn.passionshark.project.community.api.exception.CommunityException;


public class BaseResponseDto implements Serializable {

    private static final long serialVersionUID = -7349676239929277347L;
    
    public static final Integer ACCPET = 1;
    public static final Integer SUCCESS = 2;
    public static final Integer FAILURE = 3;
    

    /** 版本号 */
    private String version;

    /** 业务线代码 */
    private String bizCode;

    /** 商户号 */
    private String merchantCode;

    /** 状态【1-已受理；2-成功；3-失败】 */
    private Integer status;

    /** 错误码 */
    private String errCode;

    /** 错误信息 */
    private String errMsg;

    public BaseResponseDto() {
    }

    public void buildCommonField(Integer status, String errCode, String errMsg) {
        this.status = status;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /**
     * 当出现TradeException异常时，将错误信息，错误码，错误状态设置到BaseTradeResponseDto中
     * 
     * @param e
     */
    public void buildTradeExceptionField(CommunityException e) {
        buildCommonField(FAILURE, e.getErrCode(), e.getErrChineseMsg());
    }

    /**
     * 当出现TradeException异常时，将错误信息，错误码，错误状态设置到BaseTradeResponseDto中
     * 
     * @param e
     */
    public void buildTradeExceptionProcess(CommunityException e) {
        buildCommonField(ACCPET, e.getErrCode(), e.getErrChineseMsg());
    }

    /**
     * 当出现Exception异常时，将错误信息，错误码，错误状态设置到BaseTradeResponseDto中
     * 
     * @param e
     */
    public void buildSysExceptionField(Exception e) {
        buildExceptionEnumField(ExceptionEnum.SYS_ERR);
    }

    public void buildSuccessField() {
        buildCommonField(SUCCESS, null, null);
    }

    public void buildExceptionEnumField(ExceptionEnum e) {
        buildCommonField(FAILURE, e.getCode(), e.getChineseMessage());
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"version\":\"").append(version)
               .append("\",\"bizCode\":\"").append(bizCode)
               .append("\",\"merchantCode\":\"").append(merchantCode)
               .append("\",\"status\":\"").append(status)
               .append("\",\"errCode\":\"").append(errCode)
               .append("\",\"errMsg\":\"").append(errMsg)
               .append("\"}");
        return builder.toString();
    }
}
