package nh.graphql.beeradvisor.graphql;

import graphql.schema.DataFetcher;
import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserService;
import nh.graphql.beeradvisor.domain.Beer;
import nh.graphql.beeradvisor.domain.Rating;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Book;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class RatingController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    public RatingController(UserService userService) {
      this.userService = userService;
    }

    @SchemaMapping
    public User author(Rating rating) {
      return userService.getUser(rating.getUserId());
    }

//    @BatchMapping
//    public BatchLoader<String, User> userBatchLoader = new BatchLoader<String, User>() {
//        @Override
//        public CompletableFuture<List<User>> load(List<String> ids) {
//
//        }
//    };
//
//
//    public DataFetcher authorFetcher() {
//        return environment -> {
//            Rating rating = environment.getSource();
//            boolean useDataLoader = environment.getField().getDirective("useDataLoader") != null;
//            final String userId = rating.getUserId();
//
//            if (!useDataLoader) {
//                logger.info("Reading user (author) with id '{}' WITHOUT DataLoader", userId);
//                return userService.getUser(userId);
//            }
//
//            logger.info("Reading user (author) with id '{}' WITH DataLoader", userId);
//            DataLoader<String, User> dataLoader = environment.getDataLoader("user");
//            return dataLoader.load(userId);
//        };
//    }
}
