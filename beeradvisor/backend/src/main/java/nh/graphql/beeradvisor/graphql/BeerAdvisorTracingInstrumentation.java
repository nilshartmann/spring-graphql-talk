package nh.graphql.beeradvisor.graphql;

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

import static graphql.schema.GraphQLTypeUtil.simplePrint;

/**
 * TracingInstrumentation, very similiar to original {@link TracingInstrumentation}
 * but writes durations in ms and adds some other informations
 */
//@Component
public class BeerAdvisorTracingInstrumentation extends TracingInstrumentation {
  @Override
  public InstrumentationState createState() {
    return new TracingSupport(false) {
      private final ConcurrentLinkedQueue<Map<String, Object>> fieldData = new ConcurrentLinkedQueue<>();
      private final Map<String, Object> parseMap = new LinkedHashMap<>();
      private final Map<String, Object> validationMap = new LinkedHashMap<>();
      private final long startRequestMillis = System.currentTimeMillis();

      public TracingContext beginField(DataFetchingEnvironment dataFetchingEnvironment, boolean trivialDataFetcher) {
        if (trivialDataFetcher) {
          return () -> {
          };
        }

        long startFieldFetch = System.currentTimeMillis();
        return () -> {
          long now = System.currentTimeMillis();
          long duration = now - startFieldFetch;
          long startOffset = startFieldFetch - startRequestMillis;
          ExecutionStepInfo executionStepInfo = dataFetchingEnvironment.getExecutionStepInfo();

          Map<String, Object> fetchMap = new LinkedHashMap<>();
          fetchMap.put("path", executionStepInfo.getPath().toString());
          fetchMap.put("fieldName", simplePrint(executionStepInfo.getParent().getUnwrappedNonNullType()) + "." + executionStepInfo.getFieldDefinition().getName());
          fetchMap.put("startOffset_ms", startOffset);
          fetchMap.put("duration_ms", duration);
          fetchMap.put("thread", Thread.currentThread().getName());

          fieldData.add(fetchMap);
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
        traceMap.put("version", 1L);
        traceMap.put("OVERALL_DURATION_MS", System.currentTimeMillis() - startRequestMillis);
        traceMap.put("parsing", copyMap(parseMap));
        traceMap.put("validation", copyMap(validationMap));
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