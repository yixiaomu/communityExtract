package cn.passionshark.project.community.common.secure;

/**
 * Cipher error for encryption and decryption.
 */
public class CipherException extends Exception {

    private static final long serialVersionUID = -6245719576383435848L;

    public CipherException(String message, Throwable cause) {
        super(message, cause);
    }

    public CipherException(String message) {
        super(message);
    }

    public CipherException(Throwable cause) {
        super(cause);
    }
}
