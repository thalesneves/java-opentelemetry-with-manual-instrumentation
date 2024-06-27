# java-opentelemetry-with-manual-instrumentation
This is a sample that uses Spring Boot Framework, Opentelemetry with manual instrumentation

## environments variables
```
OTEL_LOGS_EXPORTER=otlp
OTEL_METRIC_EXPORT_INTERVAL=15000
OTEL_METRICS_EXPORTER=otlp
OTEL_SERVICE_NAME=dice-server
OTEL_TRACES_EXPORTER=otlp
```

## can be use Jaeger with Docker
```
winpty docker run --rm -it --name jaeger \
  -e COLLECTOR_OTLP_ENABLED=true \
  -p 4317:4317 \
  -p 16686:16686 \
  jaegertracing/all-in-one
```
