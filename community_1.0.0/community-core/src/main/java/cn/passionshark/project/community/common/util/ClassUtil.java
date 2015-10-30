package cn.passionshark.project.community.common.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public abstract class ClassUtil
{
  private static final Logger logger = Logger.getLogger(ClassUtil.class.getName());
  private static final Set<Class<?>> primitiveWrapperTypeSet = new HashSet();

  public static Class getGenericClass(Class clazz, int index)
  {
    Type genType = clazz.getGenericSuperclass();
    if ((genType instanceof ParameterizedType)) {
      Type[] params = ((ParameterizedType)genType).getActualTypeArguments();

      if ((params != null) && (params.length > index)) {
        return (Class)params[index];
      }
    }
    return null;
  }

  public static Class getGenericClass(Class clazz) {
    return getGenericClass(clazz, 0);
  }

  public static boolean isPrimitiveWrapper(Class<?> clazz) {
    return primitiveWrapperTypeSet.contains(clazz);
  }

  public static boolean isPrimitiveOrWrapper(Class<?> clazz, Object val) {
    return (clazz.isPrimitive()) || (isPrimitiveWrapper(clazz)) || ((val instanceof Enum));
  }

  public static boolean isPrimitiveArray(Class<?> clazz) {
    return (clazz.isArray()) && (clazz.getComponentType().isPrimitive());
  }

  public static boolean isPrimitiveWrapperArray(Class<?> clazz) {
    return (clazz.isArray()) && (isPrimitiveWrapper(clazz.getComponentType()));
  }

  public static Method getMethodByField(String fieldName, Class<?> clz) {
    String methodPostfix = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    String getter = "get" + methodPostfix;
    Method method = null;
    try {
      method = getMethod(getter, clz, new Class[0]);
      if (null == method) {
        getter = "is" + methodPostfix;
        method = getMethod(getter, clz, new Class[0]);
      }
    }
    catch (Exception ex) {
      logger.warning(String.format("Counld not find method by field: %s", new Object[] { fieldName }));
    }
    return method;
  }

  public static Method getMethod(String name, Class<?> clz, Class<?>[] parameterTypes) {
    Method method = null;
    try {
      method = clz.getDeclaredMethod(name, parameterTypes);
    } catch (NoSuchMethodException e) {
      logger.warning(String.format("Counld not find method: %s from class %s", new Object[] { name, clz.getName() }));
      try {
        method = clz.getSuperclass().getDeclaredMethod(name, parameterTypes);
      } catch (NoSuchMethodException e1) {
        logger.warning(String.format("Counld not find method: %s from class %s", new Object[] { name, clz.getSuperclass().getName() }));
      }
    }
    return method;
  }

  static
  {
    primitiveWrapperTypeSet.add(Boolean.class);
    primitiveWrapperTypeSet.add(Byte.class);
    primitiveWrapperTypeSet.add(Character.class);
    primitiveWrapperTypeSet.add(Double.class);
    primitiveWrapperTypeSet.add(Float.class);
    primitiveWrapperTypeSet.add(Integer.class);
    primitiveWrapperTypeSet.add(Long.class);
    primitiveWrapperTypeSet.add(Short.class);

    primitiveWrapperTypeSet.add(String.class);
    primitiveWrapperTypeSet.add(Date.class);
    primitiveWrapperTypeSet.add(BigDecimal.class);
    primitiveWrapperTypeSet.add(Enum.class);
  }
}