package com.ntw.oms.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.ntw.common.entity.UserAuth;
import com.ntw.common.security.JJwtUtility;
import com.ntw.common.security.JwtUtility;
import com.ntw.common.security.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;


@Secured
public class AuthenticationFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    public UserAuth getUserCred(String authHeader) {
        if(authHeader==null || !authHeader.startsWith("Bearer")) {
            logger.error("Authorization not provided; authHeader={}", authHeader);
            return null;
        }

        String authToken = authHeader.substring("Bearer".length()).trim();

        JwtUtility jwtUtility = new JJwtUtility();
        UserAuth userAuth = jwtUtility.parseToken(authToken);

        return userAuth;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        if (request.getRequestURI().startsWith("/auth/token") ||
                request.getRequestURI().startsWith("/status") ||
                request.getRequestURI().startsWith("/admin/status") ||
                request.getRequestURI().startsWith("/actuator/prometheus") ||
                        HttpMethod.OPTIONS.matches(request.getMethod()) ) {
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            logger.info("No Authorization header present in the request; requestContext={}", request.toString());
            context.setResponseStatusCode(HttpServletResponse.SC_FORBIDDEN);
            context.setResponseBody("No Authorization header present in the request");
            context.setSendZuulResponse(false);
            return null;
        }

        logger.debug("Auth header : "+authHeader);

        UserAuth userAuth = getUserCred(authHeader);
        if (userAuth == null) {
            logger.info("Unable to authenticate user token");
            context.setResponseStatusCode(HttpServletResponse.SC_FORBIDDEN);
            context.setResponseBody("Unable to authenticate user token");
            return null;
        }

        RequestContext.getCurrentContext().set("SecurityContext", userAuth);
        logger.debug("User authenticated; context={}", userAuth);

        return null;
    }
}

