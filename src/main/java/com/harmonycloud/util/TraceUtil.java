package com.harmonycloud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.HttpServletBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.List;

public class TraceUtil {
    private static final Logger LOG = LoggerFactory.getLogger(TraceUtil.class);
    private static final String[] keys = {
            "x-request-id",
            "x-b3-traceid",
            "x-b3-spanid",
            "x-b3-parentspanid",
            "x-b3-sampled",
            "x-b3-flags",
            "x-ot-span-context"
    };

    public static void addTraceForHttp(HttpHeaders forwardHeaders, HttpHeaders headers) throws Exception {
        if (forwardHeaders == null || headers == null) {
            LOG.warn("forwardHeaders or headers should not be null while adding trace id.");
            return;
        }
        for (String key : keys) {
            List<String> value = forwardHeaders.get(key);
            if (value != null)
                headers.put(key, value);
        }
    }

    public static void addTraceForHttp(HttpServletRequest request, HttpHeaders headers) throws Exception {
        if (request == null || headers == null) {
            LOG.warn("request or headers should not be null while adding trace id.");
            return;
        }
        for (String key : keys) {
            String value = request.getHeader(key);
            if (value != null) {
                headers.set(key, value);
            }
        }

    }

    public static RequestEntity.HeadersBuilder<?> addTraceForHttp(HttpHeaders forwardHeaders, RequestEntity.HeadersBuilder<?> headers) {
        if (forwardHeaders == null || headers == null) {
            LOG.warn("forwardHeaders or headers should not be null while adding trace id.");
            return headers;
        }
        for (String key : keys) {
            List<String> value = forwardHeaders.get(key);
            if (value != null) {
                String[] array = value.toArray(new String[value.size()]);
                headers.header(key, array);
            }
        }
        return headers;
    }

    public static RequestEntity.BodyBuilder addTraceForHttp(HttpHeaders forwardHeaders, RequestEntity.BodyBuilder headers) {
        if (forwardHeaders == null || headers == null) {
            LOG.warn("forwardHeaders or headers should not be null while adding trace id.");
            return headers;
        }
        for (String key : keys) {
            List<String> value = forwardHeaders.get(key);
            if (value != null) {
                String[] array = value.toArray(new String[value.size()]);
                headers.header(key, array);
            }
        }
        return headers;
    }

    public static void printTrace(HttpHeaders headers) {
        if (headers == null) {
            LOG.warn("headers should not be null while printing trace.");
            return;
        }
        for (String key : keys) {
            System.out.println("cws: " + key + ": " + headers.get(key));
        }
    }
}
