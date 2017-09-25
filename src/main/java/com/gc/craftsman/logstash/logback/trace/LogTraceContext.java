package com.gc.craftsman.logstash.logback.trace;

import com.gc.craftsman.logstash.logback.thread.RequestScopeContext;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by cjw on 23/09/2017.
 */
public class LogTraceContext {

    private static RequestScopeContext<String, String> context = RequestScopeContext.getInstance();

    public static boolean markTraceData(String traceKey, String traceValue) {
        if (StringUtils.isNotBlank(traceKey) && StringUtils.isNotBlank(traceValue)) {
            context.put(traceKey, traceValue);
            return true;
        }

        return false;
    }
}
