package cn.passionshark.project.community.common.validate;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

import cn.passionshark.project.community.common.log.Logger;
import cn.passionshark.project.community.common.log.LoggerFactory;
import cn.passionshark.project.community.common.util.Constants;
import cn.passionshark.project.community.common.util.StringUtil;


public class EncryptionUtil {
    private static final Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);

    public static String encryptWithBase64(String source) {
        if (StringUtil.isEmpty(source)) return source;
        try {
            return Base64.encodeBase64String(source.getBytes(Constants.CHARSET_UTF8));
        }
        catch (Exception e) {
            logger.error("Failed to do base 64", e);
        }
        return null;
    }

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}
