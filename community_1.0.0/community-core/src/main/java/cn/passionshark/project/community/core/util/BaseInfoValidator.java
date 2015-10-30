/*
 * Copyright (C) 2015 Creditease All rights reserved.
 */
package cn.passionshark.project.community.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

import cn.passionshark.project.community.common.log.Logger;
import cn.passionshark.project.community.common.log.LoggerFactory;
import cn.passionshark.project.community.common.util.ClassUtil;
import cn.passionshark.project.community.common.util.DateUtil;
import cn.passionshark.project.community.common.util.StringUtil;
import cn.passionshark.project.community.common.validate.Signable;
import cn.passionshark.project.community.common.validate.Validatable;
import cn.passionshark.project.community.common.validate.ValidateUtil;


/**
 * 基本信息校验服务.
 * <p>对外界(Controller, RPC Client)传递的参数进行校验
 * <ul>
 * <li>支持类型：Integer, Long, String, BigDecimal, Date, List, 自定义对象.</li>
 * <li>支持对象继承及多层嵌套</li>
 * </ul>
 *
 * @author pierre
 * @version $ v1.0 Jun 25, 2015 $
 */
@SuppressWarnings({"rawtypes"})
public abstract class BaseInfoValidator {
    private static final Logger logger = LoggerFactory.getLogger("VALIDATE", BaseInfoValidator.class);

    private static Map<String, Pattern> expressionPatternMap = new HashMap<String, Pattern>();
    private static ReentrantLock patternLock = new ReentrantLock();

    private static final String ENUM_SCOPE_METHOD = "toEnum";

    public static class ValidateErr {
        public String errmsg;
        public String errcode;

        public ValidateErr(String errcode, String errmsg) {
            this.errcode = errcode;
            this.errmsg = errmsg;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{\"errmsg\":\"").append(errmsg).append("\",\"errcode\":\"").append(errcode).append("\"}");
            return builder.toString();
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        public String getErrcode() {
            return errcode;
        }

        public void setErrcode(String errcode) {
            this.errcode = errcode;
        }
    }

    public static String serializeErr(List<ValidateErr> validateResults) {
        if (null == validateResults || validateResults.isEmpty()) {
            return null;
        }

        StringBuffer rc = new StringBuffer(1024);
        for (ValidateErr err : validateResults) {
            rc.append(err.getErrmsg()).append(",");
        }

        if (rc.length() > 0) {
            rc.deleteCharAt(rc.length() - 1);
        }

        return rc.toString();
    }

    /**
     * Validate the parameters which is annotated with <code>@Validatable</code> is regal.
     *
     * @param validatedObj the validated object
     * @param entrance is the validation port(will set the result to ThreadLocal)
     * @return error message
     * @throws Exception
     */
    public static String validate(Object validatedObj, boolean entrance) throws Exception {
        List<ValidateErr> errs = new ArrayList<BaseInfoValidator.ValidateErr>();
        validate(validatedObj, validatedObj.getClass(), errs, null, null, entrance);
        return serializeErr(errs);
    }

    /**
     * Just check the parameters, more to see {@link #validate(Object, Class, List, String, String, boolean)}
     */
    public static String validate(Object validatedObj) throws Exception {
        List<ValidateErr> errs = new ArrayList<BaseInfoValidator.ValidateErr>();
        validate(validatedObj, validatedObj.getClass(), errs, null, null, false);
        return serializeErr(errs);
    }

    public static void validate(Object validatedObj, List<ValidateErr> validateResults) throws Exception {
        validate(validatedObj, validatedObj.getClass(), validateResults, null, null, true);
    }

    private static boolean doNext(Validatable validatable, Signable signable, Idempotent idempotent) {
        return null != validatable || null != signable || null != idempotent;
    }

    protected static void validate(Object validatedObj, Class<?> validatedClz, List<ValidateErr> validateResults,
                                   String chinesePrefix, String prefix, boolean entrance) throws Exception {
        if (null == validatedObj || null == validateResults) {
            throw new IllegalArgumentException("The validated Object and ErrorResult Container can not be null.");
        }

        if (StringUtil.isEmpty(chinesePrefix)) chinesePrefix = "";
        if (StringUtil.isEmpty(prefix)) prefix = "";

        Field[] declaredFields = validatedClz.getDeclaredFields();
        for (Field field : declaredFields) {
            Signable signable = field.getAnnotation(Signable.class);
            Idempotent idempotent = field.getAnnotation(Idempotent.class);
            Validatable validatable = field.getAnnotation(Validatable.class);
            String fieldName = field.getName();

            Object obj = null;
            if (doNext(validatable, signable, idempotent)) {
                Method method = ClassUtil.getMethodByField(fieldName, validatedClz);
                if (null == method) {
                    throw new RuntimeException(String.format("Could not find getter method[%s]", fieldName));   
                }

                obj = method.invoke(validatedObj);
            }
            else {
                continue;
            }

            if (doNext(validatable, signable, idempotent)) {
                // do validate
                if (null != validatable) {
                    if (entrance) {
                        if (!CommunityContext.setValidatedSource(prefix + fieldName, null)) {
                            continue;
                        }
                    }

                    // if the field type is primitive/primitive array and passed validation
                    if (!doValidate(validatedObj, validatedClz, fieldName, obj, validatable, validateResults, chinesePrefix)) {
                        continue;
                    }
                }

                if (StringUtil.isEmpty(obj)) {
                    continue;
                }

                String prev = "";
                if (null != validatable) prev = validatable.value();
                // If the field is not the primitive type or primitive array, resolve the hierarchical
                Class<?> innerFieldType = field.getType();
                if (!ClassUtil.isPrimitiveOrWrapper(field.getType(), obj)) {

                    if (innerFieldType.isArray() || innerFieldType.equals(List.class) || innerFieldType.equals(Set.class)) { // array
                        logger.debug("Found array type: {}", new Object[] { field.getGenericType() });

                        // do iteration
                        Object[] objs = null;
                        if (obj instanceof Collection) {
                            Collection rawCollection = (Collection) obj;
                            objs = rawCollection.toArray();
                        }
                        else {
                            objs = (Object[]) obj;
                        }

                        for (int i = 0; i < objs.length; i++) {
                            validate(objs[i], objs[i].getClass(), validateResults,
                                     chinesePrefix + prev + "[" + (i + 1) + "].", prefix + fieldName + "[" + i + "].", entrance);
                        }
                    }
                    else {
                        logger.debug("{} is customized Object, validate hierarchically", new Object[] { validatedClz.getName() });
                        validate(obj, obj.getClass(), validateResults, chinesePrefix + prev + ".", prefix + fieldName + ".", entrance);
                    }
                }
                // if the field type is primitive/primitive array
                else {
                    if (entrance) {
                        String val = null;
                        if (innerFieldType.equals(Date.class)) {
                            val = DateUtil.convert((Date) obj);
                        }
                        else if (innerFieldType.equals(BigDecimal.class)) {
                            BigDecimal t = (BigDecimal) obj;
                            val = t.setScale(2, RoundingMode.HALF_UP).toString();
                        }
                        else {
                            val = obj.toString();
                        }

                        if (null != signable) {
                            CommunityContext.setSignedSource(filterParamPrefix(prefix) + fieldName, val);
                        }

                        if (null != idempotent) {
                            String filteredPrefix = filterParamPrefix(prefix);
                            logger.debug("FILTER", "{} -> {}", new Object[] { prefix + fieldName, filteredPrefix + fieldName });
                            CommunityContext.setIdempotentSource(filteredPrefix + fieldName, val);
                        }
                    }
                }
            }
        }

        // Do recursive to retrieve all validatable fields
        Class<?> superClass = validatedClz.getSuperclass();
        if (null != superClass && !Object.class.equals(superClass)) {
            validate(validatedObj, superClass, validateResults, chinesePrefix, prefix, entrance);
        }
        else if (validatedClz.isInterface()) {
            for (Class<?> superIfc : validatedClz.getInterfaces()) {
                validate(validatedObj, superIfc, validateResults, chinesePrefix, prefix, entrance);
            }
        }
    }

    private static String filterParamPrefix(String prefix) {
        if (StringUtil.isEmpty(prefix)) return "";
        int leftBracket = prefix.indexOf("[");
        int rightBracket = prefix.indexOf("]");
        if (leftBracket == -1 || rightBracket == -1) return prefix;
        return filterParamPrefix(prefix.substring(0, leftBracket) + prefix.substring(rightBracket + 1));
    }

    private static boolean doValidate(Object validatedObj, Class<?> clz, String fieldName, Object obj,
                                   Validatable validatable, List<ValidateErr> validateResults,
                                   String prefix) {
        try {
            boolean isEmpty = StringUtil.isEmpty(obj);
            // Value of the annotated field is nullable
            if (validatable.nullable()) {
                // if the value is null, do not check
                if (null == obj) {
                    return false;
                }
                logger.debug("Although the {} is required, but validate it.", new Object[] { fieldName });
            }

            // Check dependencies
            if (!StringUtil.isEmpty(validatable.dependesOn())) {
                // if dependency return false, do not check
                if (!checkDependencies(validatable, clz, validatedObj)) return true;
            }

            if (isEmpty) {
                validateResults.add(newValidateResult(prefix, fieldName, validatable.value() + "不能为空"));
                return false;
            }

            // check scope
            Class<?> enumScope = validatable.enumScope();
            if (null != enumScope && !enumScope.equals(Void.class)) { // default to skip
                if (null == checkValScope(enumScope, obj)) {
                    validateResults.add(newNotWellFormedResult(prefix, fieldName, validatable));
                    return false;
                }
                return true;
            }

            if (obj instanceof Integer) {
                Integer tempInt = (Integer) obj;
                if (tempInt.intValue() > validatable.maxLength() || tempInt.intValue() < validatable.minLength()) {
                    validateResults.add(newValidateResult(
                            prefix,
                            fieldName,
                            validatable.value() + "长度范围[" + validatable.minLength() + ", "
                                    + validatable.maxLength() + "]"));
                    return false;
                }
            }
            if (obj instanceof Long) {
                Long tempLong = (Long) obj;
                if (tempLong.intValue() > validatable.maxLength() || tempLong.intValue() < validatable.minLength()) {
                    validateResults.add(newValidateResult(
                            prefix,
                            fieldName,
                            validatable.value() + "长度范围[" + validatable.minLength() + ", "
                                    + validatable.maxLength() + "]"));
                    return false;
                }
            }
            else if (obj instanceof BigDecimal) {
                BigDecimal tempBigDecimal = (BigDecimal) obj;
                if (tempBigDecimal.compareTo(new BigDecimal(validatable.minValue())) < 0 ||
                        tempBigDecimal.compareTo(new BigDecimal(validatable.maxValue())) > 0) {
                    // tips: 字符长度应在xx-xxx间
                    validateResults.add(newValidateResult(
                            prefix,
                            fieldName,
                            validatable.value() + "长度范围[" + validatable.minLength() + ", "
                                    + validatable.maxLength() + "]"));
                    return false;
                }
                else if (validatable.isCurrency() && !ValidateUtil.isAmount(tempBigDecimal.toString())) {
                    validateResults.add(newNotWellFormedResult(prefix, fieldName, validatable));
                    return false;
                }
            }
            else if (obj instanceof Collection) {
                Collection c = (Collection) obj;
                if (c.isEmpty()) {
                    validateResults.add(newValidateResult(validatable.value(), fieldName, "集合为空"));
                    return false;
                }
            }
            else if (clz.isArray()) {
                Object[] arr = (Object[]) obj;
                if (arr.length == 0) {
                    validateResults.add(newValidateResult(validatable.value(), fieldName, "数组为空"));
                    return false;
                }
            }
            else if (obj instanceof String) {
                String val = obj.toString();

                // regular expression first
                String expr = validatable.expression();
                if (!StringUtil.isEmpty(expr)) {
                    Pattern pattern = null;
                    try {
                        patternLock.lockInterruptibly();
                        pattern = expressionPatternMap.get(expr);
                        if (null != pattern) {
                            logger.debug("Firstly, compile pattern: {} and cache it.", new Object[] { pattern });
                            pattern = Pattern.compile(expr);
                            expressionPatternMap.put(expr, pattern);
                        }
                    }
                    finally {
                        patternLock.unlock();
                    }

                    if (!pattern.matcher(val).matches()) {
                        validateResults.add(newNotWellFormedResult(prefix, fieldName, validatable));
                        return false;
                    }
                }

                if (StringUtil.isBlank(val)) {
                    validateResults.add(newNotWellFormedResult(prefix, fieldName, validatable));
                    return false;
                }

                if (val.length() < validatable.minLength() || val.length() > validatable.maxLength()) {
                    // tips: 字符长度应在xx-xxx间
                    validateResults.add(newValidateResult(
                            prefix,
                            fieldName,
                            validatable.value() + "长度范围[" + validatable.minLength() + ", "
                                    + validatable.maxLength() + "]"));
                    return false;
                }
                else if (validatable.isNumeric() && (!ValidateUtil.isNumeric(val) || !isValidNumeric(val, validatable))) {
                    validateResults.add(newNotWellFormedResult(prefix, fieldName, validatable));
                    return false;
                }
                else if (validatable.isDate() && !ValidateUtil.isDate(val)) {
                    validateResults.add(newNotWellFormedResult(prefix, fieldName, validatable));
                    return false;
                }
                else if (validatable.isCurrency() && (!ValidateUtil.isAmount(val) || !isValidCurrency(val, validatable))) {
                    validateResults.add(newNotWellFormedResult(prefix, fieldName, validatable));
                    return false;
                }
                else if (validatable.isEmail() && !ValidateUtil.isEmail(val)) {
                    validateResults.add(newNotWellFormedResult(prefix, fieldName, validatable));
                    return false;
                }
                else if (validatable.isIdentity() && !ValidateUtil.isCardNo(val)) {
                    validateResults.add(newNotWellFormedResult(prefix, fieldName, validatable));
                    return false;
                }
                else if (validatable.isPhone() && !ValidateUtil.isMobileNO(val)) {
                    validateResults.add(newNotWellFormedResult(prefix, fieldName, validatable));
                    return false;
                }
                else if (validatable.isUrl() && !ValidateUtil.isUrl(val)) {
                    validateResults.add(newNotWellFormedResult(prefix, fieldName, validatable));
                    return false;
                }
            }
        }
        catch (Exception e) {
            logger.error("Could not get value of the declared method " + fieldName, e);
            throw new RuntimeException(e);
        }
        return true;
    }

    private static Object checkValScope(Class<?> scopeEnum, Object val) throws Exception {
        if (Enum.class.isAssignableFrom(scopeEnum)) {
            Method toEnumMethod = null;
            try {
                toEnumMethod = scopeEnum.getDeclaredMethod(ENUM_SCOPE_METHOD, String.class);
                return toEnumMethod.invoke(null, String.valueOf(val));
            }
            catch (NoSuchMethodException e) {
                toEnumMethod = scopeEnum.getDeclaredMethod(ENUM_SCOPE_METHOD, Integer.class);
                return toEnumMethod.invoke(null, Integer.parseInt(String.valueOf(val)));
            }
        }
        throw new RuntimeException("value of scopeEnum is not a valid Enum class.");
    }

    private static boolean checkDependencies(Validatable validatable, Class<?> clz, Object validatedObj) {
        String dependMethod = validatable.dependesOn();
        try {
            Method dependencyMethod = clz.getMethod(dependMethod, new Class<?>[] {});
            return (Boolean) dependencyMethod.invoke(validatedObj, new Object[] {});
        }
        catch (Exception e) {
            logger.error("Can not find dependency method: " + dependMethod);
            throw new RuntimeException(e);
        }
    }

    private static boolean isValidCurrency(String val, Validatable validatable) {
        BigDecimal amount = new BigDecimal(val).setScale(2, RoundingMode.HALF_UP);
        return amount.compareTo(new BigDecimal(validatable.minAmount()).setScale(2, RoundingMode.HALF_UP)) >= 0;
    }

    private static boolean isValidNumeric(String val, Validatable validatable) {
        Integer num = Integer.parseInt(val);
        return (num >= validatable.minNumeric() && num <= validatable.maxNumeric());
    }

    private static ValidateErr newValidateResult(String prefix, String fieldName, String msg) {
        return new ValidateErr(fieldName.toUpperCase() + "_INVALID", prefix + msg);
    }

    private static ValidateErr newNotWellFormedResult(String prefix, String fieldName, Validatable validatable) {
        return new ValidateErr(fieldName, prefix + validatable.value() + "格式不正确");
    }
}
