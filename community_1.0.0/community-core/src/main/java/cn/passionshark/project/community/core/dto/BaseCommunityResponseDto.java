package cn.passionshark.project.community.core.dto;

import java.io.Serializable;

import cn.passionshark.project.community.api.enums.ExceptionEnum;
import cn.passionshark.project.community.api.exception.CommunityException;
import cn.passionshark.project.community.common.validate.Signable;
import cn.passionshark.project.community.core.enums.StatusEnum;


public class BaseCommunityResponseDto implements Serializable {

    private static final long serialVersionUID = -7349676239929277347L;

    /** 版本号 */
    @Signable
    private String version;

    /** 状态【0-已受理；1-成功；2-失败】 */
    @Signable
    private Integer status;

    /** 错误码 */
    @Signable    
    private String errCode;

    /** 错误信息 */
    @Signable    
    private String errMsg;

    /** 签名
     */
    private String sign;
    

    public BaseCommunityResponseDto() {
    }

    public void buildCommonField(Integer status, String errCode, String errMsg) {
        this.status = status;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /**
     * 当出现CommunityException异常时，将错误信息，错误码，错误状态设置到BaseTradeResponseDto中
     * 
     * @param e
     */
    public void buildCommunityExceptionField(CommunityException e) {
        buildCommonField(StatusEnum.STATUS_FAILED.getCode(), e.getErrCode(), e.getErrChineseMsg());
    }

    /**
     * 当出现CommunityException异常时，将错误信息，错误码，错误状态设置到BaseTradeResponseDto中
     * 
     * @param e
     */
    public void buildCommunityExceptionProcess(CommunityException e) {
        buildCommonField(StatusEnum.STATUS_PROCESSING.getCode(), e.getErrCode(), e.getErrChineseMsg());
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
        buildCommonField(StatusEnum.STATUS_SUCCESS.getCode(), null, null);
    }

    public void buildExceptionEnumField(ExceptionEnum e) {
        buildCommonField(StatusEnum.STATUS_FAILED.getCode(), e.getCode(), e.getChineseMessage());
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"version\":\"").append(version)
               .append("\",\"status\":\"").append(status)
               .append("\",\"errCode\":\"").append(errCode)
               .append("\",\"errMsg\":\"").append(errMsg)
               .append("\",\"sign\":\"").append(sign)
               .append("\"}");
        return builder.toString();
    }
}
