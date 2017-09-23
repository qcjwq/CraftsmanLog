package com.gc.craftsman.logstash.logback.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.gc.craftsman.logstash.logback.thread.RequestScopeContext;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;

/**
 * Created by cjw on 23/09/2017.
 */
public class CraftsmanLoggerAppender extends LogstashTcpSocketAppender {

    private static final Logger logger = LoggerFactory.getLogger(CraftsmanLoggerAppender.class);

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
            MDC.setContextMap(localMap);
        }

        super.prepareForDeferredProcessing(event);
    }
}
