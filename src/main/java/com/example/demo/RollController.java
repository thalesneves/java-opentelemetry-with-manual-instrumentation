package com.example.demo;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RollController {

    @Autowired
    private Tracer tracer;

    @Autowired
    private RedisService redisService;

    @GetMapping("/example")
    public String exampleEndpoint() {
        Span span = tracer.spanBuilder("/create").startSpan();
        span.setAttribute("HTTP_METHOD", "POST");
        span.setAttribute("HTTP_SCHEME", "http");
        span.setAttribute("HTTP_URL", "http://example/resource");
        span.setAttribute("HTTP_HOST", "localhost:8080");
        span.setAttribute("HTTP_TARGET", "/resource");
        span.setAttribute("HTTP_STATUS_CODE", 200);

        try {
            span.addEvent("chamando o Redis...");
            redisService.chamadaAoRedis(span);
            span.setStatus(StatusCode.OK);
        } catch (Exception e) {
            span.setStatus(StatusCode.ERROR, "Something bad happened!");
        } finally {
            span.end();
        }

        return "Hello, OpenTelemetry!";
    }
}
