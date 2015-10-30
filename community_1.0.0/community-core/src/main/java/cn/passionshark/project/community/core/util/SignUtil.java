package cn.passionshark.project.community.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;

import cn.passionshark.project.community.common.log.Logger;
import cn.passionshark.project.community.common.log.LoggerFactory;
import cn.passionshark.project.community.common.util.Constants;
import cn.passionshark.project.community.common.util.StringUtil;
import cn.passionshark.project.community.common.validate.EncryptionUtil;
import cn.passionshark.project.community.common.validate.SignatureUtil;
import cn.passionshark.project.community.api.enums.ExceptionEnum;
import cn.passionshark.project.community.api.exception.CommunityException;

/**
 * 应用系统动态密钥安全操作类.
 *
 * @author miller
 * @author pierre
 * @revision v2.1
 */
public class SignUtil {

    private static Logger logger = LoggerFactory.getLogger("SIGN", SignUtil.class);

    public static boolean checkSign(String source, String targetSignature, String signKey) {
        try {
            if (source.isEmpty() || StringUtil.isEmpty(targetSignature)) {
                return false;
            }

            String signedStr = KeyedDigestMD5.getKeyedDigestUTF8(source, signKey);
            return (signedStr != null && signedStr.equals(targetSignature));
        }
        catch (Exception e) {
            logger.error(String.format("Failed to sign %s, with key: %s", EncryptionUtil.encryptWithBase64(source), signKey), e);
            throw new CommunityException(e);
        }
    }

    /**
     * 对接口请求进行验证
     * 
     * @param sourceMap 原字符串
     * @param sourceSign 商家请求的摘要
     * @param signKey 商家静态KEY
     * @return true 验证成功 false 验证失败
     */
    public static boolean checkReqSign(Map<String, String> sourceMap, String sourceSign, String signKey) {
        if (sourceMap.isEmpty() || StringUtil.isEmpty(sourceSign)) {
            return false;
        }

        return checkSign(retrieveSignedSource(sourceMap), sourceSign, signKey);
    }

    public static boolean checkReqSignFromGateWay(Map<String, String> sourceMap, String targetSignature, String appInitKey) {
        try {
            if (sourceMap.isEmpty() || StringUtil.isEmpty(targetSignature)) {
                return false;
            }

            String signString = KeyedDigestMD5.getKeyedDigestUTF8(retrieveSignedSource(sourceMap) + "&" + appInitKey, "");
            return (signString != null && signString.equals(targetSignature));
        }
        catch (Exception e) {
            throw new CommunityException(e);
        }
    }

    /**
     * 对接口请求进行验证,接受HttpServletRequest，将request中的参数转换成map，不包括sign字段
     *
     * @param request
     * @param targetSignature 商家请求的摘要
     * @param signKey 商家静态KEY
     * @return
     */
    public static boolean checkReqSign(HttpServletRequest request, String targetSignature, String signKey) {
        return checkReqSign(getRequestMap(request, "sign", "signInfo"), targetSignature, signKey);
    }

    /**
     * 对接口请求进行验证,接受HttpServletRequest，将request中的参数转换成map，不包括sign字段(校验新结算异步返回参数)
     * 
     * @param request
     * @param hmac 商家请求的摘要
     * @param appInitKey 商家静态KEY
     * @return
     */
    public static boolean checkReqSignFromGateway(HttpServletRequest request, String hmac, String appInitKey) {
        return checkReqSignFromGateWay(getRequestMap(request, "sign", "signInfo"), hmac, appInitKey);
    }

    /**
     * 对接口响应参数进行加密生成
     * 
     * @author gengsi
     * @param sourceMap
     * @param signKey 商家静态KEY
     * @return 接口输出串
     */
    public static String creatResSignMap(Map<String, String> sourceMap, String signKey) {
        try {
            if(null == sourceMap || sourceMap.isEmpty() || StringUtil.isEmpty(signKey)){
                throw new IllegalArgumentException("resParam can not be empty!");
            }

            return KeyedDigestMD5.getKeyedDigestUTF8(retrieveSignedSource(sourceMap), signKey);
        } catch (Exception e) {
             logger.error("Failed to do sign, due to " + e, e);
             throw new CommunityException(e);
        }
    }

    /**
     * 根据新结算平台加签规则获取签名 （空值不参与，原串后拼接key）
     * 
     * @param resParam
     * @param appInitKey 商家静态KEY
     * 
     * @return 接口输出串
     */
    public static String creatResSignMapToGateWay(Map resParam, String appInitKey) {
        try {
            // 对参数名按照ASCII升序排序
            Object[] key = resParam.keySet().toArray();
            Arrays.sort(key);

            // 生成加密原串
            StringBuffer res = new StringBuffer(128);
            for (int i = 0; i < key.length; i++) {
                if (resParam.get(key[i]) instanceof String && resParam.get(key[i]) != null
                        && !"".equals(resParam.get(key[i]))) {
                    res.append(key[i] + "=" + resParam.get(key[i]) + "&");
                } else if (resParam.get(key[i]) instanceof String[]) {
                    String[] value = (String[]) resParam.get(key[i]);
                    if (value[0] != null && !"".equals(value[0])) {
                        res.append(key[i] + "=" + value[0] + "&");
                    }
                } else if (resParam.get(key[i]) instanceof Integer && resParam.get(key[i]) != null
                        && !"".equals(resParam.get(key[i]))) {
                    res.append(key[i] + "=" + resParam.get(key[i]) + "&");
                } else if (resParam.get(key[i]) instanceof Double && resParam.get(key[i]) != null
                        && !"".equals(resParam.get(key[i]))) {
                    res.append(key[i] + "=" + resParam.get(key[i]) + "&");
                }
            }

            String rStr = res.substring(0, res.length() - 1) + "&" + appInitKey;
            return KeyedDigestMD5.getKeyedDigestUTF8(rStr, "");

        } catch (Exception e) {
            throw new CommunityException(e);
        }
    }

    public static String creatResSignBean(Object obj, String appInitKey) {
        try {
            String signedStr = SignatureUtil.serializedSignedObject(obj);
            logger.debug("begin to create sign, the signed string is : " + signedStr);
            return KeyedDigestMD5.getKeyedDigestUTF8(signedStr, appInitKey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CommunityException(ExceptionEnum.SIGN_ERROR);
        }
    }

    /**
     * 对resParam二维数组加密
     * 
     * @param resParam
     * @param appInitKey
     *            商家静态KEY
     * @return 加密后的串
     */
    public static String creatHmacSignUTF8(Map resParam, String appInitKey) {
        try {
            // 对参数名按照ASCII升序排序
            Object[] key = resParam.keySet().toArray();
            Arrays.sort(key);

            // 生成加密原串
            StringBuffer res = new StringBuffer(128);
            for (int i = 0; i < key.length; i++) {
                if (resParam.get(key[i]) instanceof String) {
                    res.append(key[i] + "=" + resParam.get(key[i]) + "&");
                } else if (resParam.get(key[i]) instanceof String[]) {
                    String[] value = (String[]) resParam.get(key[i]);
                    res.append(key[i] + "=" + value[0] + "&");
                } else if (resParam.get(key[i]) instanceof Integer) {
                    res.append(key[i] + "=" + resParam.get(key[i]) + "&");
                }
            }
            String rStr = res.substring(0, res.length() - 1);
            return KeyedDigestMD5.getKeyedDigestUTF8(rStr, appInitKey);

        } catch (Exception e) {
            throw new CommunityException(e);
        }
    }

    public static Map<String, String> getRequestMap(HttpServletRequest request, String... ignores) {
        Map<String, String> map = new HashMap<String, String>();
        List<String> ignoreList = null;
        if (ignores != null) {
            ignoreList = Arrays.asList(ignores);
        }

        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String name = e.nextElement();
            if (ignoreList != null && ignoreList.contains(name)) {
                continue;
            } else {
                String value = request.getParameter(name);
                map.put(name, value);
            }
        }

        return map;
    }

    public static Map<String, Object> translateToMap(Object obj, String... ignores) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> ignoreList = null;
        if (ignores != null) {
            ignoreList = Arrays.asList(ignores);
        }

        try{
            PropertyDescriptor[] propertys = BeanUtils.getPropertyDescriptors(obj.getClass());
            for (PropertyDescriptor property : propertys) {
                if (ignoreList != null && ignoreList.contains(property.getName())) {
                    continue;
                }

                Method readMethod = property.getReadMethod();
                if(readMethod != null){
                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                    }
                    Object value = readMethod.invoke(obj);
                    if (!StringUtil.isEmpty(value)) {
                        map.put(property.getName(), value);
                    }
                }
            }
        }catch(Exception e){
            throw new CommunityException(e);
        }

        return map;
    }


    private static String retrieveSignedSource(Map<String, String> sourceMap) {
        TreeMap<String, String> sortedMap = new TreeMap<String, String>();
        sortedMap.putAll(sourceMap);
        return retrieveSignedSource(sortedMap);
    }

    private static String retrieveSignedSource(TreeMap<String, String> sourceMap) {
        StringBuilder rc = new StringBuilder(1024);
        for (Entry<String, String> entry : sourceMap.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            if (!StringUtil.isEmpty(val)) {
                rc.append(key).append(Constants.CHAR_EQ).append(val).append(Constants.CHAR_AND);
            }
        }
        int len = rc.length();
        if (len > 0) {
            rc = rc.deleteCharAt(len - 1);
        }

        logger.debug("Sined source: {}", new Object[] { EncryptionUtil.encryptWithBase64(rc.toString()) });
        return rc.toString();
    }
}
