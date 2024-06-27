package com.example.demo;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private Tracer tracer;

    public void chamadaAoRedis(Span parentSpan) {
        Span span = tracer.spanBuilder("/chamada-ao-redis")
                .setParent(Context.current().with(parentSpan))
                .startSpan();

        try (Scope scope = span.makeCurrent()) {
            Thread.sleep(5000);
            span.setStatus(StatusCode.OK);
        } catch (InterruptedException e) {
            span.recordException(e);
            span.setStatus(StatusCode.ERROR);
        } finally {
            span.end();
        }
    }

}
