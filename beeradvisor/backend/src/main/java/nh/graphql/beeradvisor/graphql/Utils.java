package nh.graphql.beeradvisor.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static void slowDown(DataFetchingEnvironment environment) {
        if (environment.getField().hasDirective("slowDown")) {
            log.info("Sleeping for 2000ms on Thread '{}'", Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            log.info("Slept 2000ms on Thread '{}'", Thread.currentThread().getName());
        }
    }

    public static DataFetcher wrap(final DataFetcher df) {
        return environment -> {
            if (!environment.getField().hasDirective("async")) {
                return df.get(environment);
            }

            return CompletableFuture.supplyAsync(() -> {
                try {
                    return df.get(environment);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new IllegalStateException(e);
                }
            });
        };
    }

}