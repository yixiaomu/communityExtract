package cn.passionshark.project.community.common.validate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import cn.passionshark.project.community.common.util.ClassUtil;
import cn.passionshark.project.community.common.util.DateUtil;
import cn.passionshark.project.community.common.util.StringUtil;

/**
 * Signment convenient methods.
 */
@SuppressWarnings("rawtypes")
public abstract class SignatureUtil {
    public static String serializedSignedObject(Object signedObject) throws Exception {
        TreeMap<String, String> signMap = new TreeMap<String, String>();
        serializedSignedObject(signedObject, signedObject.getClass(), signMap, null);
        return serializeSignedMap(signMap);
    }

    private static void serializedSignedObject(Object signedObject, Class<?> signedClaz, TreeMap<String, String> signMap, String prefix) throws Exception {
        if (StringUtil.isEmpty(signedObject)) {
            throw new IllegalArgumentException("SignedObject could not be empty.");
        }

        if (StringUtil.isEmpty(prefix)) {
            prefix = "";
        }

        Field[] declaredFields = signedClaz.getDeclaredFields();
        for (Field field : declaredFields) {
            Signable signable = field.getAnnotation(Signable.class);
            if (null != signable) {
                String fieldName = field.getName();
                Class<?> innerFieldType = field.getType();
                Method m = ClassUtil.getMethodByField(fieldName, signedClaz);
                if (null == m) {
                    throw new NoSuchMethodError(fieldName);
                }

                Object obj = m.invoke(signedObject);
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
                        val = t.setScale(2, RoundingMode.HALF_UP).toString();
                    }
                    else {
                        val = obj.toString();
                    }
                    signMap.put(prefix + fieldName, val);
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
                        serializedSignedObject(objs[i], objs[i].getClass(), signMap, prefix + fieldName + "[" + i + "].");
                    }
                }
                else {
                    serializedSignedObject(obj, obj.getClass(), signMap, prefix + fieldName + ".");
                }
            }
        }

        // Do recursive to retrieve all validatable fields
        Class<?> superClass = signedClaz.getSuperclass();
        if (null != superClass && !Object.class.equals(superClass)) {
            serializedSignedObject(signedObject, superClass, signMap, prefix);
        } else if (signedClaz.isInterface()) {
            for (Class<?> superIfc : signedClaz.getInterfaces()) {
                serializedSignedObject(signedObject, superIfc, signMap, prefix);
            }
        }
    }

    private static String serializeSignedMap(TreeMap<String, String> signedMap) {
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

        return rc.toString();
    }
}
