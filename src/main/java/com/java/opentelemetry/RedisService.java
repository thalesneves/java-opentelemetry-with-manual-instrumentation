package com.java.opentelemetry;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final Tracer tracer;

    @Autowired
    public RedisService(Tracer tracer) {
        this.tracer = tracer;
    }

    public void callToRedis() {
        Span span = tracer.spanBuilder("/call-redis")
                .setParent(Context.current())
                .startSpan();

         try (Scope ignored = span.makeCurrent()) {
            Thread.sleep(50);
            span.setStatus(StatusCode.OK);
        } catch (InterruptedException e) {
            span.recordException(e);
            span.setStatus(StatusCode.ERROR);
        } finally {
            span.end();
        }
    }

}
