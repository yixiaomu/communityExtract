/**
 * Copyright (C) 2015 Creditease All rights reserved.
 */
package cn.passionshark.project.community.api.dto.common;

import java.io.Serializable;

/**
 * 交易签名请求参数.
 *
 * @author pierre
 * @version $ v1.0 June 25, 2015 $
 */
public class SignatureParam implements Serializable {

    private static final long serialVersionUID = 5078495684984634676L;

    private String bizCode;
    private String merchantCode;
    private String source;

    public SignatureParam() {
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"bizCode\":\"").append(bizCode)
               .append("\",\"merchantCode\":\"").append(merchantCode)
               .append("\",\"source\":\"").append(source)
               .append("\"}");
        return builder.toString();
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
