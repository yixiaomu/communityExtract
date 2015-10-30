/*
 * Copyright (C) 2015 Creditease All rights reserved.
 */
package cn.passionshark.project.community.common.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import cn.passionshark.project.community.common.log.Logger;
import cn.passionshark.project.community.common.log.LoggerFactory;
import cn.passionshark.project.community.common.util.ClassUtil;
import cn.passionshark.project.community.common.util.DateUtil;
import cn.passionshark.project.community.common.util.StringUtil;


/**
 * 工具类, 检查请求信息是否改变.
 *
 * @author pierre
 * @version $ v1.0 Jan 19, 2015 $
 */
@SuppressWarnings({ "rawtypes", "unchecked"})
public class IdempotentSupport {
    private static final Logger logger = LoggerFactory.getLogger("ENC", IdempotentSupport.class);

    public static boolean check(String source, String target) throws Exception {
        if (StringUtil.isEmpty(source)) {
            throw new IllegalArgumentException("Source Object can not be empty.");
        }

        if (StringUtil.isEmpty(target)) {
            return false;
        }

        return source.equalsIgnoreCase(target);
    }

    public static boolean check(String source, Object target) throws Exception {
        if (null == source) {
            throw new IllegalArgumentException("Source Object can not be empty.");
        }
        
        if (null == target) {
            return false;
        }
        
        TreeMap<String, String> idempotentMap = new TreeMap<String, String>();
        serializeObject(Idempotent.class, target, target.getClass(), idempotentMap, null);
        return source.equals(EncryptionUtil.MD5Encode(serializeSignedMap(idempotentMap), "UTF-8"));
    }

    public static String encryptRequest(Object source) throws Exception {
        TreeMap<String, String> idempotentMap = new TreeMap<String, String>();
        serializeObject(Idempotent.class, source, source.getClass(), idempotentMap, null);
        return EncryptionUtil.MD5Encode(serializeSignedMap(idempotentMap), "UTF-8");
    }

    public static <T extends Annotation> void serializeObject(Class<T> annotationClz, Object serializedObj,
                                                               Class<?> serializedClz, TreeMap<String, String> serializedMap,
                                                               String prefix) throws Exception {
        if (StringUtil.isEmpty(serializedObj)) {
            throw new IllegalArgumentException("Serialized Object could not be empty.");
        }
        
        if (StringUtil.isEmpty(prefix)) {
            prefix = "";
        }
        
        Field[] declaredFields = serializedClz.getDeclaredFields();
        for (Field field : declaredFields) {
            T annotation = field.getAnnotation(annotationClz);
            if (null != annotation) {
                String fieldName = field.getName();
                Class<?> innerFieldType = field.getType();
                Method m = ClassUtil.getMethodByField(fieldName, serializedClz);
                if (null == m) {
                    throw new NoSuchMethodException(fieldName);
                }

                Object obj = m.invoke(serializedObj);
                if (StringUtil.isEmpty(obj)) {
                    continue;
                }
                
                if (ClassUtil.isPrimitiveOrWrapper(innerFieldType, obj)) {
                    String val = null;
                    if (innerFieldType.equals(Date.class)) {
                        val = DateUtil.convert((Date) obj);
                    }
                    else if (innerFieldType.equals(BigDecimal.class)) {
                        BigDecimal t = (BigDecimal) obj;
                        t.setScale(2, RoundingMode.HALF_UP);
                        val = t.toString();
                    }
                    else {
                        val = obj.toString();
                    }
                    serializedMap.put(prefix + fieldName, val);
                }
                else if (innerFieldType.isArray() || innerFieldType.equals(List.class) || innerFieldType.equals(Set.class)) { // array
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
                        serializeObject(annotationClz, objs[i], objs[i].getClass(), serializedMap, prefix + fieldName + "[" + i + "].");
                    }
                }
                else {
                    serializeObject(annotationClz, obj, obj.getClass(), serializedMap, prefix + fieldName + ".");
                }
            }
        }

        // Do recursive to retrieve all validatable fields
        Class<?> superClass = serializedClz.getSuperclass();
        if (null != superClass && !Object.class.equals(superClass)) {
            serializeObject(annotationClz, serializedObj, superClass, serializedMap, prefix);
        } else if (serializedClz.isInterface()) {
            for (Class<?> superIfc : serializedClz.getInterfaces()) {
                serializeObject(annotationClz, serializedObj, superIfc, serializedMap, prefix);
            }
        }
    }

    public static String serializeSignedMap(TreeMap<String, String> signedMap) {
        StringBuffer rc = new StringBuffer(1024);
        for (Entry<String, String> entry : signedMap.entrySet()) {
            String value = entry.getValue();
            if (value != null && value.length() > 0) {
                rc.append("&").append(entry.getKey().trim()).append("=").append(value);
            }
        }

        if (rc.length() > 0) {
            rc.deleteCharAt(0);
        }

        logger.debug("enc source: {}", new Object[] { rc });
        return rc.toString();
    }

    /**
     * Serialize complex Object(including List<String>) to idempotent source.
     *
     * @param params serialized Map
     * @return serialized idempotent source
     */
    public static String serializeComplexSource(TreeMap<String, Object> params) {
        StringBuffer rc = new StringBuffer(2048);
        for (Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (!StringUtil.isEmpty(value)) {
                if (value instanceof List) {
                    List<String> col = (List<String>) value;
                    Collections.sort(col);
                    for (String s : col) {
                        rc.append("&").append(entry.getKey().trim()).append("=").append(s);
                    }
                }
                else {
                    rc.append("&").append(entry.getKey().trim()).append("=").append(value);
                }
            }
        }

        if (rc.length() > 0) {
            rc.deleteCharAt(0);
        }
        
        logger.debug("enc source: {}", new Object[] { rc });
        return rc.toString();
    }
}
