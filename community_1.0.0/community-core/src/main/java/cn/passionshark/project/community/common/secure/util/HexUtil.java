package cn.passionshark.project.community.common.secure.util;

import java.security.MessageDigest;

/**
 * Hex Utility.
 *
 */
public class HexUtil {
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static String bit32(String source) throws Exception {
        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        digest.update(source.getBytes("UTF-8"));
        byte messageDigest[] = digest.digest();
        return toHexString(messageDigest);
    }

    public static String bit16(String source) throws Exception {
        return bit32(source).substring(8, 24);
    }

    // ------------------------------------------------------------------------
    // Secure Utilities
    public static String byte2hex(byte[] bytes) {
        String hex = "";
        String stmp = "";
        for (int n = 0; n < bytes.length; n++) {
            stmp = Integer.toHexString(bytes[n] & 0xFF);
            if (stmp.length() == 1) {
                hex += ("0" + stmp);
            }
            else {
                hex += stmp;
            }
        }
        return hex.toUpperCase();
    }

    public static byte[] hex2byte(byte[] bytes) {
        if ((bytes.length % 2) != 0) {
            throw new IllegalArgumentException("len of bytes is not a even number.");
        }
        byte[] b2 = new byte[bytes.length / 2];

        for (int n = 0; n < bytes.length; n += 2) {
            String item = new String(bytes, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
}
