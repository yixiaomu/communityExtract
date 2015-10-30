package cn.passionshark.project.community.common.secure;

import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.passionshark.project.community.common.secure.util.HexUtil;
import cn.passionshark.project.community.common.util.StringUtil;


/**
 * Cryptographic Service.
 */
public class CryptService {
    private static final Logger logger = Logger.getLogger(CryptService.class.getName());

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String CIHPER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static final String DEFAULT_IV = "0102030405060708";

    public static String encrypt(String source, String key) throws CipherException {
        try {
            if (StringUtil.isEmpty(source)) {
                return source;
            }

            if (StringUtil.isEmpty(key) || 16 != key.length()) {
                throw new IllegalArgumentException("Invalid key.");
            }

            byte[] raw = key.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIHPER_TRANSFORMATION);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(DEFAULT_IV.getBytes(DEFAULT_CHARSET));
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(source.getBytes(DEFAULT_CHARSET));

            return HexUtil.byte2hex(encrypted);
        }
        catch (Exception e) {
            logger.warning("Fail to encrypt, due to " + e.getMessage());
            throw new CipherException(e);
        }
    }

    public static String decrypt(String source, String key) throws CipherException {
        try {
            if (StringUtil.isEmpty(source)) {
                return source;
            }
            if (StringUtil.isEmpty(key) || 16 != key.length()) {
                throw new IllegalArgumentException("Invalid key.");
            }

            byte[] raw = key.getBytes(DEFAULT_CHARSET);
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIHPER_TRANSFORMATION);
            IvParameterSpec ivParameter = new IvParameterSpec(DEFAULT_IV.getBytes(DEFAULT_CHARSET));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameter);
            byte[] encrypted = HexUtil.hex2byte(source.getBytes(DEFAULT_CHARSET));
            byte[] original = cipher.doFinal(encrypted);
            return new String(original, DEFAULT_CHARSET);
        }
        catch (Exception e) {
            logger.warning("Fail to decrypt, due to " + e.getMessage());
            throw new CipherException(e);
        }
    }
}
