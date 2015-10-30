package cn.passionshark.project.community.api.dto.common;

import java.io.Serializable;

import constant.LengthConstant;
import constant.Version;

public class BaseRequestDto implements Serializable {

    private static final long serialVersionUID = -550088676899195495L;

    @Validatable(value = "版本号", enumScope = Version.class)
    @Signable
    private String version;

    @Validatable(value = "业务编号", maxLength = LengthConstant.BIZ_CODE_MAX_LENGTH)
    @Signable
    private String bizCode;

    @Validatable(value = "商户编号", maxLength = LengthConstant.MERCHANT_CODE_MAX_LENGTH)
    @Signable
    private String merchantCode;

    public BaseRequestDto() {
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

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"version\":\"").append(version)
               .append("\",\"bizCode\":\"").append(bizCode)
               .append("\",\"merchantCode\":\"").append(merchantCode)
               .append("\"} ");
        return builder.toString();
    }
}
