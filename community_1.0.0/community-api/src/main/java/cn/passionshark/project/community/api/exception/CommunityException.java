package cn.passionshark.project.community.api.exception;

import cn.passionshark.project.community.api.enums.ExceptionEnum;

public class CommunityException extends RuntimeException {

    
    /**
     * 
     */
    private static final long serialVersionUID = 1899665672611535298L;
    
    /** a defined error code */
    private String errCode;
    /** human-readable error message */
    private String errChineseMsg;

    public CommunityException(String message, Throwable cause) {
        super(message, cause);
        this.errCode = ExceptionEnum.SYS_ERR.getCode();
        this.errChineseMsg = message;
    }

    public CommunityException(Throwable cause) {
        super(cause);
        this.errCode = ExceptionEnum.SYS_ERR.getCode();
        this.errChineseMsg = ExceptionEnum.SYS_ERR.getChineseMessage();
    }

    public CommunityException(String errCode, String message) {
        this(errCode, message, message);
    }

    public CommunityException(ExceptionEnum errEnum) {
        this(errEnum.getCode(), errEnum.getMessage(), errEnum.getChineseMessage());
    }

    public CommunityException(ExceptionEnum errEnum, String message) {
        this(errEnum.getCode(), message, message);
    }

    public CommunityException(String errCode, String message, String chineseMsg) {
        super(message);
        this.errCode = errCode;
        this.errChineseMsg = message;
        this.errChineseMsg = chineseMsg;
    }

    public String getErrCode() {
        return errCode;
    }
    
    public String getErrChineseMsg() {
        return errChineseMsg;
    }
}
