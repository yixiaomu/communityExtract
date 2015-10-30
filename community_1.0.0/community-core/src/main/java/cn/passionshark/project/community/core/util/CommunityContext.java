package cn.passionshark.project.community.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cn.passionshark.project.community.common.log.Logger;
import cn.passionshark.project.community.common.log.LoggerFactory;
import cn.passionshark.project.community.common.validate.EncryptionUtil;
import cn.passionshark.project.community.common.validate.IdempotentSupport;


/**
 * InsuranceContext represents execution state of a request process.
 * It is a structure that can be used to map an attribute name to an attribute value.
 * Internally Insurance context implementations are usually backed by a {@link ThreadLocal}.
 *  
 */
@SuppressWarnings("unchecked")
public final class CommunityContext {
    private static final Logger logger = LoggerFactory.getLogger(CommunityContext.class);

    private static final class CommunityThreadContext {
        public TreeMap<String, String> validatedSource = new TreeMap<String, String>();
        public TreeMap<String, Object> signedSource = new TreeMap<String, Object>();
        public TreeMap<String, Object> idempotentSource = new TreeMap<String, Object>();
    }

    private static final ThreadLocal<CommunityThreadContext> serializedRequest = new ThreadLocal<CommunityThreadContext>();

    private CommunityContext() {
    }

    private static CommunityThreadContext getRequest() {
        CommunityThreadContext context = serializedRequest.get();
        if (null == context) {
            context = new CommunityThreadContext();
            setRequest(context);
        }
        return context;
    }

    private static void setRequest(CommunityThreadContext serializedContent) {
        serializedRequest.set(serializedContent);
    }

    public static void clear() {
        serializedRequest.remove();
    }

    public static String getValidatedSource() {
        logger.debug("validated source: {}", new Object[] {getRequest().validatedSource});
        return IdempotentSupport.serializeSignedMap(getRequest().validatedSource);
    }

    public static String getSignedSource() {
        logger.debug("sign souce: {}", new Object[] {getRequest().signedSource.toString()});
        return IdempotentSupport.serializeComplexSource(getRequest().signedSource);
    }

    public static String getIdempotentSource() {
        logger.info("idempotent source: {}", new Object[] {getRequest().idempotentSource.toString()});
        return EncryptionUtil.MD5Encode(IdempotentSupport.serializeComplexSource(getRequest().idempotentSource), "UTF-8");
    }

    public static boolean setValidatedSource(String key, String val) {
        TreeMap<String, String> validatedMap = getRequest().validatedSource;
        return setIfAbsent(validatedMap, key, val);
    }

    public static boolean setSignedSource(String key, String val) {
        TreeMap<String, Object> signedMap = getRequest().signedSource;
        return setIfAbsent0(signedMap, key, val);
    }

    public static boolean setIdempotentSource(String key, String val) {
        TreeMap<String, Object> idempotentMap = getRequest().idempotentSource;
        return setIfAbsent0(idempotentMap, key, val);
    }

    private static boolean setIfAbsent0(TreeMap<String, Object> container, String key, String val) {
        if (!container.containsKey(key)) {
            container.put(key, val);
        }
        else {
            Object value = container.get(key);
            List<String> arr = null;
            if (value instanceof List) {
                arr = (List<String>) value;
                arr.add(val);
            }
            else {
                arr = new ArrayList<String>();
                arr.add(String.valueOf(value));
                arr.add(val);
            }
            container.put(key, arr);
            logger.debug("Dup validated field: {}", new Object[] { key });
        }
        return true;
    }

    private static boolean setIfAbsent(TreeMap<String, String> validatableMap, String key, String val) {
        if (!validatableMap.containsKey(key)) {
            validatableMap.put(key, val);
            return true;
        }
        else {
            logger.debug("Omit override validated field: {}", new Object[] { key });
            return false;
        }
    }
}
