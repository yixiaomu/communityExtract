package cn.passionshark.project.community.common.log;

import org.slf4j.Marker;

import cn.passionshark.project.community.common.util.StringUtil;


/**
 * Logger Wrapper, supply simple marker prefix.
 */
public class Logger implements org.slf4j.Logger {
    private static final String LEFT_SQUARE_BRACKET = "[";
    private static final String RIGHT_SQUARE_BRACKET = "] ";
    private static final String LINE = "-";

    private final org.slf4j.Logger raw;
    private String prefix;

    public Logger(String prefix, org.slf4j.Logger rawLogger) {
        this.raw = rawLogger;
        this.prefix = prefix;
    }

    /**
     * Add prefix to original message format.
     *
     * @param format original message format
     * @return combined format
     */
    private String combineFormat(String format) {
        if (StringUtil.isEmpty(prefix)) {
            return format;
        }

        if (null == format) {
            format = "";
        }
        return new StringBuilder(LEFT_SQUARE_BRACKET).append(prefix).append(RIGHT_SQUARE_BRACKET).append(format).toString();
    }

    private String combineFormat(String func, String format) {
        if (StringUtil.isEmpty(prefix)) {
            return new StringBuilder(LEFT_SQUARE_BRACKET).append(func).append(RIGHT_SQUARE_BRACKET).append(format).toString();
        }

        if (null == format) {
            format = "";
        }
        return new StringBuilder(LEFT_SQUARE_BRACKET).append(prefix).append(LINE).append(func)
                .append(RIGHT_SQUARE_BRACKET).append(format).toString();
    }

    // ------------------------------------------------------------------------
    // Self properties
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public org.slf4j.Logger getRawLogger() {
        return raw;
    }

    // -------------------------------------------------------------------------
    // Override
    public String getName() {
        return raw.getName();
    }

    public boolean isTraceEnabled() {
        return raw.isTraceEnabled();
    }

    public void trace(String msg) {
        raw.trace(combineFormat(msg));
    }

    public void trace(String format, Object arg) {
        raw.trace(combineFormat(format), arg);
    }

    public void trace(String format, Object arg1, Object arg2) {
        raw.trace(combineFormat(format), arg1, arg2);
    }

    public void trace(String format, Object[] argArray) {
        raw.trace(combineFormat(format), argArray);
    }

    public void trace(String msg, Throwable t) {
        raw.trace(combineFormat(msg), t);
    }

    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    public void trace(Marker marker, String msg) {
        raw.trace(marker, combineFormat(msg));
    }

    public void trace(Marker marker, String format, Object arg) {
        raw.trace(marker, combineFormat(format), arg);
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        raw.trace(marker, combineFormat(format), arg1, arg2);
    }

    public void trace(Marker marker, String format, Object[] argArray) {
        raw.trace(marker, combineFormat(format), argArray);
    }

    public void trace(Marker marker, String msg, Throwable t) {
        raw.trace(marker, combineFormat(msg), t);
    }

    public boolean isDebugEnabled() {
        return raw.isDebugEnabled();
    }

    // -----------------------------------------------------------------------
    // Overrides methods
    public void debug(String func, String msg) {
        if(isDebugEnabled()) {
            raw.debug(combineFormat(func, msg));
        }
    }

    public void debug(String func, String format, Object arg) {
        if(isDebugEnabled()) {
            raw.debug(combineFormat(func, format), arg);
        }
    }

    public void debug(String func, String format, Object arg1, Object arg2) {
        if(isDebugEnabled()) {
            raw.debug(combineFormat(func, format), arg1, arg2);
        }
    }

    public void debug(String func, String format, Object[] argArray) {
        if(isDebugEnabled()) {
            raw.debug(combineFormat(func, format), argArray);
        }
    }

    public void debug(String func, String msg, Throwable t) {
        if(isDebugEnabled()) {
            raw.debug(combineFormat(func, msg), t);
        }
    }

    // -----------------------------------------------------------------------
    // Raw methods
    public void debug(String msg) {
        if(isDebugEnabled()) {
            raw.debug(combineFormat(msg));
        }
    }

    public void debug(String format, Object arg) {
        if(isDebugEnabled()) {
            raw.debug(combineFormat(format), arg);
        }
    }

    public void debug(String format, Object arg1, Object arg2) {
        if(isDebugEnabled()) {
            raw.debug(combineFormat(format), arg1, arg2);
        }
    }

    public void debug(String format, Object[] argArray) {
        if(isDebugEnabled()) {
            raw.debug(combineFormat(format), argArray);
        }
    }

    public void debug(String msg, Throwable t) {
        if(isDebugEnabled()) {
            raw.debug(combineFormat(msg), t);
        }
    }

    public boolean isDebugEnabled(Marker marker) {
        return raw.isDebugEnabled(marker);
    }

    public void debug(Marker marker, String msg) {
        if(isDebugEnabled()) {
            raw.debug(marker, combineFormat(msg));
        }
    }

    public void debug(Marker marker, String format, Object arg) {
        if(isDebugEnabled()) {
            raw.debug(marker, combineFormat(format), arg);
        }
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        if(isDebugEnabled()) {
            raw.debug(marker, combineFormat(format), arg1, arg2);
        }
    }

    public void debug(Marker marker, String format, Object[] argArray) {
        if(isDebugEnabled()) {
            raw.debug(marker, combineFormat(format), argArray);
        }
    }

    public void debug(Marker marker, String msg, Throwable t) {
        if(isDebugEnabled()) {
            raw.debug(marker, combineFormat(msg), t);
        }
    }

    public boolean isInfoEnabled() {
        return raw.isInfoEnabled();
    }

    // -----------------------------------------------------------------
    // Override methods
    /**
     *  Log a message at the INFO level.
     * 
     * @param func function name, deal as a message prefix
     * @param msg message to print
     * @see #info(String)
     */
    public void info(String func, String msg) {
        raw.info(combineFormat(func, msg));
    }
    
    public void info(String func, String format, Object arg) {
        raw.info(combineFormat(func, format), arg);
    }
    
    public void info(String func, String format, Object arg1, Object arg2) {
        raw.info(combineFormat(func, format), arg1, arg2);
    }
    
    public void info(String func, String format, Object[] argArray) {
        raw.info(combineFormat(func, format), argArray);
    }

    public void info(String msg) {
        raw.info(combineFormat(msg));
    }

    // -----------------------------------------------------------------
    // Original methods
    public void info(String format, Object arg) {
        raw.info(combineFormat(format), arg);
    }

    public void info(String format, Object arg1, Object arg2) {
        raw.info(combineFormat(format), arg1, arg2);
    }

    public void info(String format, Object[] argArray) {
        raw.info(combineFormat(format), argArray);
    }

    public void info(String msg, Throwable t) {
        raw.info(combineFormat(msg), t);
    }

    public boolean isInfoEnabled(Marker marker) {
        return raw.isInfoEnabled(marker);
    }

    public void info(Marker marker, String msg) {
        raw.info(marker, combineFormat(msg));
    }

    public void info(Marker marker, String format, Object arg) {
        raw.info(marker, combineFormat(format), arg);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        raw.info(marker, combineFormat(format), arg1, arg2);
    }

    public void info(Marker marker, String format, Object[] argArray) {
        raw.info(marker, combineFormat(format), argArray);
    }

    public void info(Marker marker, String msg, Throwable t) {
        raw.info(marker, combineFormat(msg), t);
    }

    // -------------------------------------------------------------------
    // Warn func overrides
    public void warn(String func, String msg) {
        raw.warn(combineFormat(func, msg));
    }

    public void warn(String func, String format, Object arg) {
        raw.warn(combineFormat(func, format), arg);
    }

    public void warn(String func, String format, Object arg1, Object arg2) {
        raw.warn(combineFormat(func, format), arg1, arg2);
    }

    public void warn(String func, String format, Object[] argArray) {
        raw.warn(combineFormat(func, format), argArray);
    }

    public void warn(String func, String msg, Throwable t) {
        raw.warn(combineFormat(func, msg), t);
    }

    // -------------------------------------------------------------------
    // Warn raw methods

    public boolean isWarnEnabled() {
        return raw.isWarnEnabled();
    }

    public void warn(String msg) {
        raw.warn(combineFormat(msg));
    }

    public void warn(String format, Object arg) {
        raw.warn(combineFormat(format), arg);
    }

    public void warn(String format, Object arg1, Object arg2) {
        raw.warn(combineFormat(format), arg1, arg2);
    }

    public void warn(String format, Object[] argArray) {
        raw.warn(combineFormat(format), argArray);
    }

    public void warn(String msg, Throwable t) {
        raw.warn(combineFormat(msg), t);
    }

    public boolean isWarnEnabled(Marker marker) {
        return raw.isWarnEnabled(marker);
    }

    public void warn(Marker marker, String msg) {
        raw.warn(marker, combineFormat(msg));
    }

    public void warn(Marker marker, String format, Object arg) {
        raw.warn(marker, combineFormat(format), arg);
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        raw.warn(marker, combineFormat(format), arg1, arg2);
    }

    public void warn(Marker marker, String format, Object[] argArray) {
        raw.warn(marker, combineFormat(format), argArray);
    }

    public void warn(Marker marker, String msg, Throwable t) {
        raw.warn(marker, combineFormat(msg), t);
    }

    public boolean isErrorEnabled() {
        return raw.isErrorEnabled();
    }

    // -------------------------------------------------------------------
    // Error func methods
    public void error(String func, String msg) {
        raw.error(combineFormat(func, msg));
    }

    public void error(String func, String format, Object arg) {
        raw.error(combineFormat(func, format), arg);
    }

    public void error(String func, String format, Object arg1, Object arg2) {
        raw.error(combineFormat(func, format), arg1, arg2);
    }

    public void error(String func, String format, Object[] argArray) {
        raw.error(combineFormat(func, format), argArray);
    }

    public void error(String func, String msg, Throwable t) {
        raw.error(combineFormat(func, msg), t);
    }

    // -------------------------------------------------------------------
    // Error func methods
    public void error(String msg) {
        raw.error(combineFormat(msg));
    }

    public void error(String format, Object arg) {
        raw.error(combineFormat(format), arg);
    }

    public void error(String format, Object arg1, Object arg2) {
        raw.error(combineFormat(format), arg1, arg2);
    }

    public void error(String format, Object[] argArray) {
        raw.error(combineFormat(format), argArray);
    }

    public void error(String msg, Throwable t) {
        raw.error(combineFormat(msg), t);
    }

    public boolean isErrorEnabled(Marker marker) {
        return raw.isErrorEnabled(marker);
    }

    public void error(Marker marker, String msg) {
        raw.error(marker, combineFormat(msg));
    }

    public void error(Marker marker, String format, Object arg) {
        raw.error(marker, combineFormat(format), arg);
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        raw.error(marker, combineFormat(format), arg1, arg2);
    }

    public void error(Marker marker, String format, Object[] argArray) {
        raw.error(marker, combineFormat(format), argArray);
    }

    public void error(Marker marker, String msg, Throwable t) {
        raw.error(marker, combineFormat(msg), t);
    }
}
