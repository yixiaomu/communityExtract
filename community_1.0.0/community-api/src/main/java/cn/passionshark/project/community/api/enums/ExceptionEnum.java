/**
 * Copyright (C) 2015 Creditease All rights reserved.
 */
package cn.passionshark.project.community.api.enums;

/**
 * 
 */
public enum ExceptionEnum {
    // 系统类异常 01
    SUCESS("00000", "SUCCESS", "成功"),
    SIGN_CHECK_ERR("00001", "SIGN_CHECK_ERR", "验证签名不正确"),
    ARG_ERR("00002", "PARMA_CHECK_ERR", "参数校验失败"),
    SYS_ERR("01001", "SYS_ERR", "系统异常，请稍后重试"),
    SYS_TIMEOUT("01105", "SYS_TIMEOUT", "系统超时，请稍后重试"),
    UNKNOWN_STATUS_ERR("01106", "UNKNOWN_STATUS_ERR", "状态未知"),
    UNKNOWN_COMMAND_ERR("01107", "UNKNOWN_COMMAND_ERR", "未能识别的命令类型"),
    SIGN_ERROR("01108", "SIGN_ERROR","加签失败"),
    UNAUTHORIZED_AC_ERR("01109", "UNAUTHORIZED_AC_ERR","无权限访问"),
    SYS_DUP_ORDER("01104", "SYS_DUP_ORDER", "重复提交，该订单已存在"),
    // 结算系统异常
    SETTLEMENT_BIZTYPE_NOT_EXIST("30001", "SETTLEMENT_BIZTYPE_NOT_EXIST","结算配置不存在"),
    PARAMETER_NOT_EQUALS_REPAYMENT("30002", "PARAMETER_NOT_EQUALS_REPAYMENT","还款记录与传入参数不一致"),
    PARAMETER_NOT_EQUALS_ORDER("30003", "PARAMETER_NOT_EQUALS_ORDER","结算单与传入参数不一致"),
    AMOUNT_MINUS_ERR("30004", "AMOUNT_MINUS_ERR","金钱为负数"),
    AMOUNT_ACCURACY_ERR("30005", "AMOUNT_ACCURACY_ERR", "金额精度错误，不是2位精度"),
    ORDER_NOT_CHANGE("30006", "ORDER_NOT_CHANGE", "结算单状态为付款中或付款成功，不能重新生成结算单"),
    SETTLEMENT_ORDER_IS_PROCESSING("30007", "SETTLEMENT_ORDER_IS_PROCESSING", "结算单状态为生成中，不能重复执行"),
    ORDER_STATUS_NOT_SUCCESS("30008", "ORDER_STATUS_NOT_SUCCESS", "结算单生成状态未成功，不能进行还款"),
    REPAYMENT_NOT_ZERO("30009", "REPAYMENT_NOT_ZERO", "请求还款积分为0，不能进行还款"),
    // 通知错误
    NOTIFY_ERR("18001", "NOTIFY_ERR", "后台通知失败"),
    
    // 其他异常
    SYS_IOEXCEPTION("20001", "SYS_IOEXCEPTION", "系统IO异常, 请稍后重试"),
    SYS_KEYMANAGEMENTEXCEPTION("20002", "SYS_KEYMANAGEMENTEXCEPTION", "系统安全异常，HTTPS授权失败"),
    SYS_NOSUCHALGORITHMEXCEPTION("20003", "SYS_NOSUCHALGORITHMEXCEPTION", "系统算法异常");
    private final String code;
    private final String message;
    private final String chineseMessage;

    private ExceptionEnum(String code, String desc, String chineseMessage) {
        this.code = code;
        this.message = desc;
        this.chineseMessage = chineseMessage;
    }

    /**
     * Get the error code.
     * @return error code
     */
    public String getCode() {
        return code;
    }

    /**
     * Description of the error.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Chinese description.
     * @return the chineseMessage
     */
    public String getChineseMessage() {
        return chineseMessage;
    }
}
