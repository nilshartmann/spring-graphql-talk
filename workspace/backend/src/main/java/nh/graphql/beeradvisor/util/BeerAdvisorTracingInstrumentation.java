package nh.graphql.beeradvisor.util;

import graphql.GraphQLContext;
import graphql.com.google.common.collect.ImmutableList;
import graphql.execution.ExecutionStepInfo;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import graphql.execution.instrumentation.tracing.TracingSupport;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TracingInstrumentation, very similiar to original {@link TracingInstrumentation}
 * but writes some other informations that I need for for demonstration purposes.
 */
@Component
public class BeerAdvisorTracingInstrumentation extends TracingInstrumentation {
    @Override
    public InstrumentationState createState() {
        return new TracingSupport(false) {
            private final ConcurrentLinkedQueue<Map<String, Object>> fieldData = new ConcurrentLinkedQueue<>();
            private final Map<String, Object> parseMap = new LinkedHashMap<>();
            private final Map<String, Object> validationMap = new LinkedHashMap<>();
            private final long startRequestMillis = System.currentTimeMillis();
            private boolean trace = false;
            private boolean traceAll = false;
            private final AtomicInteger counter = new AtomicInteger();

            public TracingContext beginField(DataFetchingEnvironment dataFetchingEnvironment, boolean trivialDataFetcher) {
                if (dataFetchingEnvironment.getField().getDirectives("trace").size() > 0) {
                    trace = true;
                }

                if (dataFetchingEnvironment.getField().getDirectives("traceAll").size() > 0) {
                    trace = true;
                    traceAll = true;
                }

                if (!trace) {
                    return () -> {
                    };
                }


                long startFieldFetch = System.currentTimeMillis();
                final int fetcherNo = trivialDataFetcher ? -1 : counter.incrementAndGet();
                return () -> {
                    long now = System.currentTimeMillis();
                    long duration = now - startFieldFetch;
                    long startOffset = startFieldFetch - startRequestMillis;
                    ExecutionStepInfo executionStepInfo = dataFetchingEnvironment.getExecutionStepInfo();

                    Map<String, Object> fetchMap = new LinkedHashMap<>();
                    String fieldName = executionStepInfo.getPath().toString();

                    String source = GraphQLUtils.getFieldContext(dataFetchingEnvironment);

                    if (!traceAll) {
                        if (!trivialDataFetcher) {
                            // "structured" format: only trace non-trivial datafetchers
                            fetchMap.put("fetcher_start_no", fetcherNo);
                            fetchMap.put("field", fieldName);
                            if (source != null) {
                                fetchMap.put("description", source);
                            }
                            fetchMap.put("startedAt_ms", startOffset);
                            fetchMap.put("took_ms", duration);
                            fetchMap.put("thread", Thread.currentThread().getName());
                        }
                    } else {
                        // "Text"-Format: Trace in more human readable form,
                        // also include trivial fetcher,
                        // so that we can have a form of "logging" in the extensions field,
                        // to see what's going on at-all

                        String msg = String.format("fetched by %s datafetcher in %sms at %sms on thread %s",
                            trivialDataFetcher ? "trivial" : "CUSTOM",
                            duration,
                            startOffset,
                            Thread.currentThread().getName());
                        fetchMap.put(fieldName, msg);
                        if (source != null) {
                            fetchMap.put("source", source);
                        }
                    }

                    if (!fetchMap.isEmpty()) {
                        fieldData.add(fetchMap);
                    }
                };
            }

            public TracingContext beginParse() {
                return traceToMap(parseMap);
            }

            /**
             * This should be called to start the trace of query validation, with {@link TracingContext#onEnd()} being called to
             * end the call.
             *
             * @return a context to call end on
             */
            public TracingContext beginValidation() {
                return traceToMap(validationMap);
            }

            private TracingContext traceToMap(Map<String, Object> map) {
                long start = System.currentTimeMillis();
                return () -> {
                    long now = System.currentTimeMillis();
                    long duration = now - start;
                    long startOffset = now - startRequestMillis;

                    map.put("startOffset_ms", startOffset);
                    map.put("duration_ms", duration);
                };
            }

            /**
             * This will snapshot this tracing and return a map of the results
             *
             * @return a snapshot of the tracing data
             */
            public Map<String, Object> snapshotTracingData() {
                Map<String, Object> traceMap = new LinkedHashMap<>();
//        traceMap.put("version", 1L);
                traceMap.put("overall_query_execution_time", System.currentTimeMillis() - startRequestMillis);
//        traceMap.put("parsing", copyMap(parseMap));
//        traceMap.put("validation", copyMap(validationMap));
                traceMap.put("execution", executionData());

                return traceMap;
            }

            private Object copyMap(Map<String, Object> map) {
                return new LinkedHashMap<>(map);
            }

            private Map<String, Object> executionData() {
                Map<String, Object> map = new LinkedHashMap<>();
                List<Map<String, Object>> list = ImmutableList.copyOf(fieldData);
                map.put("datafetchers", list);
                return map;
            }
        };
    }
}
