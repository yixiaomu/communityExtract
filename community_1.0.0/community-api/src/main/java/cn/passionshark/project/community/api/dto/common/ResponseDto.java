/**
 * Copyright (C) 2015 Creditease All rights reserved.
 */
package cn.passionshark.project.community.api.dto.common;

import java.io.Serializable;

import cn.passionshark.project.community.api.enums.ExceptionEnum;
import cn.passionshark.project.community.api.exception.CommunityException;


/**
 *
 * @author pierre
 * @version $ v1.0 June 25, 2015 $
 */
public class ResponseDto<T> implements Serializable {

    private static final long serialVersionUID = -3929841095415186645L;

    private boolean ret;
    private T data;
    private String errcode;
    private String errmsg;

    public ResponseDto<T> err(ExceptionEnum err) {
        return err(err.getCode(), err.getChineseMessage());
    }

    public ResponseDto<T> err(CommunityException err) {
        return err(err.getErrCode(), err.getErrChineseMsg());
    }

    public ResponseDto<T> err(Exception err) {
        return err(ExceptionEnum.SYS_ERR);
    }

    public ResponseDto<T> err(String errcode, String errmsg) {
        setRet(false);
        setErrcode(errcode);
        setErrmsg(errmsg);
        return this;
    }

    public ResponseDto<T> succ(T data) {
        setRet(true);
        setData(data);
        return this;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"ret\":\"").append(ret)
               .append("\",\"data\":\"").append(data)
               .append("\",\"errcode\":\"").append(errcode)
               .append("\",\"errmsg\":\"").append(errmsg)
               .append("\"} ");
        return builder.toString();
    }

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
