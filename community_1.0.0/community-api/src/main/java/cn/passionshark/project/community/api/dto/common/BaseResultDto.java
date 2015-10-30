/**
 * Copyright (C) 2015 Creditease All rights reserved.
 */
package cn.passionshark.project.community.api.dto.common;

import java.io.Serializable;

import cn.passionshark.project.community.api.enums.ExceptionEnum;
import cn.passionshark.project.community.api.exception.CommunityException;

/**
 * 响应基类，提供状态.
 *
 * @author pierre
 * @version $ v1.0 Jun 25, 2015 $
 */
public class BaseResultDto implements Serializable {

    private static final long serialVersionUID = 4862076246292487661L;

    public static final String ACCPET = "1";
    public static final String SUCCESS = "2";
    public static final String FAILURE = "3";

    /**
     * 状态
     */
    private String status;
    
    /**
     * 错误代码
     */
    private String errCode;
    
    /**
     * 错误消息
     */
    private String errMsg;

    public BaseResultDto() {
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"status\":\"").append(status).append("{\"errCode\":\"").append(errCode)
                .append("{\"errMsg\":\"").append(errMsg).append("\"}");
        return builder.toString();
    }

    public BaseResultDto err(Exception err) {
        return err(ExceptionEnum.SYS_ERR);
    }

    public BaseResultDto err(CommunityException err) {
        return err(err.getErrCode(), err.getErrChineseMsg());
    }

    public BaseResultDto err(ExceptionEnum err) {
        return err(err.getCode(), err.getChineseMessage());
    }

    public BaseResultDto err(String errCode, String errMsg) {
        setStatus(FAILURE);
        setErrCode(errCode);
        setErrMsg(errMsg);
        return this;
    }

    public boolean isSuccess() {
        return SUCCESS.equals(status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
}

