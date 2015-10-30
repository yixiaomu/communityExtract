package cn.passionshark.project.community.common.log;

/**
 * Wrapper of {@link org.slf4j.LoggerFactory}, produce various customized loggers, more to see {@link org.slf4j.LoggerFactory}.
 *
 * @author pierre
 * @version $ v1.0 Jan 9, 2015 $
 */
public final class LoggerFactory {
    private static final String EMPTY_PREFIX = null;

    /**
     * Retrieve a logger according to the name parameter.
     *
     * @param key name of logger
     * @return logger
     */
    public static Logger getLogger(Class<?> key) {
        return new Logger(EMPTY_PREFIX, org.slf4j.LoggerFactory.getLogger(key));
    }

    /**
     * @see #getLogger(Class)
     */
    public static Logger getLogger(String key) {
        return new Logger(EMPTY_PREFIX, org.slf4j.LoggerFactory.getLogger(key));
    }

    /**
     * Retrieve a logger according to the name parameter.
     * 
     * @param key name of logger
     * @return logger
     */
    public static Logger getLogger(String prefix, Class<?> key) {
        return new Logger(prefix, org.slf4j.LoggerFactory.getLogger(key));
    }
    
    /**
     * @see #getLogger(Class)
     */
    public static Logger getLogger(String prefix, String key) {
        return new Logger(prefix, org.slf4j.LoggerFactory.getLogger(key));
    }
}
