/**
 * Copyright (C) 2015 Creditease All rights reserved.
 */
package cn.passionshark.project.community.api.dto.common;


/**
 * 交易签名结果.
 *
 * @author pierre
 * @version $ v1.0 June 25, 2015 $
 */
public class SignatureResultDto extends BaseResultDto {

    private static final long serialVersionUID = 5281406467062060098L;

    /** 签名 */
    private String signature;

    public SignatureResultDto() {
    }

    public SignatureResultDto suc(String signature) {
        setStatus(SUCCESS);
        setSignature(signature);
        return this;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"signature\":\"").append(signature)
               .append("\",\"status\":\"").append(getStatus())
               .append("\",\"errCode\":\"").append(getErrCode())
               .append("\",\"ErrMsg\":\"").append(getErrMsg())
               .append("\"} ");
        return builder.toString();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
