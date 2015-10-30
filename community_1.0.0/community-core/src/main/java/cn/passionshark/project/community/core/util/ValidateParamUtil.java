package cn.passionshark.project.community.core.util;

import java.math.BigDecimal;

import cn.passionshark.project.community.common.util.StringUtil;
import cn.passionshark.project.community.api.enums.ExceptionEnum;
import cn.passionshark.project.community.api.exception.CommunityException;

/**
 * 参数校验工具
 */
public class ValidateParamUtil {

    // 因为每次是new出来的对象，所以可以使用全局的变量
    private String variableName;
    private Object object;

    /**
     * 校验参数非空，若校验不通过会抛出业务异常
     * 
     * @param variableName
     * @param obj
     * @return
     */
    public static ValidateParamUtil checkEmpty(String variableName, Object obj) {
        ValidateParamUtil vUtil = new ValidateParamUtil();
        vUtil.setVariableName(variableName);
        vUtil.setObject(obj);
        if (obj instanceof String) {
            if (StringUtil.isEmpty((String) obj)) {
                throw new CommunityException(ExceptionEnum.ARG_ERR,
                        ExceptionEnum.ARG_ERR.getMessage() + ":" + variableName + " is null");
            }
        }
        if (StringUtil.isEmpty(obj)) {
            throw new CommunityException(ExceptionEnum.ARG_ERR, ExceptionEnum.ARG_ERR.getMessage()
                    + ":" + variableName + " is null");
        }
        return vUtil;
    }

    /**
     * 校验金钱是否为负数，若为负数则抛出业务异常
     * 
     * @param variableName
     * @param amount
     * @return
     */
    public ValidateParamUtil checkMoney() {
        String variableName = this.getVariableName();
        Object amount = this.getObject();
        if (amount instanceof Money) {
            if (((Money) amount).lessOrEquals(new Money(0))) {
                throw new CommunityException(ExceptionEnum.AMOUNT_MINUS_ERR,
                        ExceptionEnum.AMOUNT_MINUS_ERR.getMessage() + ":" + variableName + " is minus or 0");
            }
        } else if (amount instanceof BigDecimal) {
            Money money = new Money((BigDecimal) amount);
            if ((money).lessOrEquals(new Money(0))) {
                throw new CommunityException(ExceptionEnum.AMOUNT_MINUS_ERR,
                        ExceptionEnum.AMOUNT_MINUS_ERR.getMessage() + ":" + variableName + " is minus or 0");
            }
        }
        return this;
    }

    @SuppressWarnings("unused")
	private void checkMoneyAccuracy(String str) {
        String[] strings = str.split("\\.");
        if (strings.length > 1 && strings[1].length() != 2) {
            throw new CommunityException(ExceptionEnum.AMOUNT_ACCURACY_ERR);
        }
    }

    private String getVariableName() {
        return variableName;
    }

    private void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    private Object getObject() {
        return object;
    }

    private void setObject(Object object) {
        this.object = object;
    }

    public static void main(String[] args) {
        try {
            ValidateParamUtil.checkEmpty("aa", "xxx");
            System.out.println("xxxxxxxxxxxxxxxxx");
            ValidateParamUtil.checkEmpty("money", new BigDecimal("100.11")).checkMoney();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
