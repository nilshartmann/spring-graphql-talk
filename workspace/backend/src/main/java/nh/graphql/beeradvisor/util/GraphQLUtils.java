package nh.graphql.beeradvisor.util;

import graphql.execution.ExecutionStepInfo;
import graphql.schema.DataFetchingEnvironment;


/**
 *
 * This is for demonstration/workshops only. You would not use this in real apps.
 */
public class GraphQLUtils {

    /**
     * This is for demonstration/workshops only. You would not use this in real apps.
     */
    public static void addFieldContext(DataFetchingEnvironment env, String value) {
        ExecutionStepInfo executionStepInfo = env.getExecutionStepInfo();
        String fieldName = executionStepInfo.getPath().toString();

        env.getGraphQlContext().put(fieldName + ".description", value);
    }

    /**
     * This is for demonstration/workshops only. You would not use this in real apps.
     */
    static String getFieldContext(DataFetchingEnvironment env) {
        ExecutionStepInfo executionStepInfo = env.getExecutionStepInfo();
        String fieldName = executionStepInfo.getPath().toString();

        String value = env.getGraphQlContext().get(fieldName + ".description");
        if (value != null) {
            env.getGraphQlContext().delete(fieldName+".description");
        }
        return value;
    }
}
