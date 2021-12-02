package bg.wallet.www.project.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getUserPrincipal() != null) {
            log.info("Request intercepted: " +
                    String.format("User: %s. Path: %s. Method %s",
                            request.getUserPrincipal().getName(), request.getRequestURL(), request.getMethod()));
        } else {
            log.info("Request intercepted: " +
                    String.format("User: Anonymous. Path: %s. Method %s", request.getRequestURL(), request.getMethod()));
        }
        return true;
    }

}