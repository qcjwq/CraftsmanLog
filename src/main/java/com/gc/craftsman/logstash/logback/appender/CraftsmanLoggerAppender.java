package com.gc.craftsman.logstash.logback.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.gc.craftsman.logstash.logback.thread.RequestScopeContext;
import net.logstash.logback.appender.LogstashTcpSocketAppender;

import java.util.Map;

/**
 * Created by cjw on 23/09/2017.
 */
public class CraftsmanLoggerAppender extends LogstashTcpSocketAppender {
    private static final RequestScopeContext<String, String> rsc = RequestScopeContext.getInstance();

    @Override
    public synchronized void start() {
        rsc.init();
        super.start();
    }

    @Override
    public synchronized void stop() {
        rsc.clear();
        super.stop();
    }

    @Override
    protected void prepareForDeferredProcessing(ILoggingEvent event) {
        Map<String, String> localMap = rsc.getLocalMap();
        if (localMap != null && !localMap.isEmpty()) {
            try {
                for (Map.Entry<String, String> entry : localMap.entrySet()) {
                    event.getMDCPropertyMap().put(entry.getKey(), entry.getValue());
                }
            } catch (Exception e) {
                addError(e.getMessage(), e);
            }
        }

        super.prepareForDeferredProcessing(event);
    }

}
