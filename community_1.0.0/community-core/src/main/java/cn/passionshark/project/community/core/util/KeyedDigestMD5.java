package cn.passionshark.project.community.core.util;

import java.security.MessageDigest;

import cn.passionshark.project.community.api.exception.CommunityException;

public class KeyedDigestMD5 {
//    private static final Logger logger = LoggerFactory.getLogger(KeyedDigestMD5.class);

    public static String getKeyedDigestUTF8(String strSrc, String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(strSrc.getBytes("UTF8"));

            String result = "";
            byte[] temp;
            temp = md5.digest(key.getBytes("UTF8"));
            for (int i = 0; i < temp.length; i++) {
                result += Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
            }
            return result;
        }
        catch (Exception e) {
//            logger.error("Failed to do md5 digest", e);
            throw new CommunityException(e);
        }
    }
}
