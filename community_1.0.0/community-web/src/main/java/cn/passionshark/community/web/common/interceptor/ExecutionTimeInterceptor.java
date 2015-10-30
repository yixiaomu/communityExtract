package cn.passionshark.community.web.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * Controller Execution time.
 *
 * @author pierre
 * @version $ v1.0 June 25, 2015 $
 */
@Component("executionTimeInterceptor")
public class ExecutionTimeInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeInterceptor.class);

    private static final String START_TIME_NAME = "_startTime_";

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(START_TIME_NAME, System.currentTimeMillis());
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        try {
            long startTime = (long) request.getAttribute(START_TIME_NAME);
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("{} execution time: {}", new Object[] { request.getRequestURI(), elapsedTime });
        }
        catch (Exception ignore) {
            logger.error("Failed to calc controller elapsed time", ignore);
        }
    }
}
