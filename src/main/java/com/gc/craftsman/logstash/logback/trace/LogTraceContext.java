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
        if (StringUtils.isBlank(traceKey) || StringUtils.isBlank(traceValue)) {
            return false;
        }
        
        context.put(traceKey, traceValue);
        return true;
    }

    public static boolean markTraceData(Map<String, String> traceMap) {
        if (traceMap == null || traceMap.isEmpty()) {
            return false;
        }

        context.putAll(traceMap);
        return true;
    }
}
