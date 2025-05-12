package com.ntw.oms.gateway.filter;

import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class RequestFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        logger.debug("Request pre filter called");

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        if (request.getRequestURI().startsWith("/status") ||
                request.getRequestURI().startsWith("/actuator/prometheus")) {
            return null;
        }

        String body = null;
        try (final InputStream requestDataStream = context.getRequest().getInputStream()) {
            body = StreamUtils.copyToString(requestDataStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // context.set("any param for other filters", param value);

        Map<String, String> requestMap = new HashMap();
        requestMap.put("Uri", request.getRequestURI());
        requestMap.put("Url", request.getRequestURL().toString());
        requestMap.put("Protocol", request.getProtocol());
        requestMap.put("AuthHeader", request.getHeader("Authorization"));
        requestMap.put("Method", request.getMethod());
        requestMap.put("QueryString", request.getQueryString());
        requestMap.put("Body", body);
        requestMap.put("ContentType", request.getHeader("Content-Type"));

        logger.info("Request pre filter executed; route={}", (new Gson()).toJson(requestMap));

        return null;
    }

}
