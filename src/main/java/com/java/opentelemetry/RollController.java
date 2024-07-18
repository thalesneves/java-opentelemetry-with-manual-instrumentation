package com.java.opentelemetry;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RollController {

    private final Tracer tracer;

    private final RedisService redisService;

    @Autowired
    public RollController(Tracer tracer, RedisService redisService) {
        this.tracer = tracer;
        this.redisService = redisService;
    }

    @GetMapping("/example")
    public String example() {
        Span span = tracer.spanBuilder("/create").startSpan();
        span.setAttribute("http.method", "POST");
        span.setAttribute("http.url", "http://example/resource");
        span.setAttribute("http.status_code", 200);

        try (Scope ignored = span.makeCurrent()) {
            redisService.callToRedis();
            span.setStatus(StatusCode.OK);
        } catch (Exception e) {
            span.setStatus(StatusCode.ERROR, "Something bad happened!");
        } finally {
            span.end();
        }

        return "Hello, OpenTelemetry!";
    }
}
