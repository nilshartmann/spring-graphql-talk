package nh.graphql.beeradvisor.graphql;

import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserService;
import nh.graphql.beeradvisor.domain.Rating;
import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.stereotype.Controller;

import java.util.concurrent.CompletableFuture;

import static nh.graphql.beeradvisor.domain.BeerAdvisorService.slowdown;

@Controller
public class RatingGraphQlController {
    private static final Logger logger = LoggerFactory.getLogger(RatingGraphQlController.class);

    private final UserService userService;

    public RatingGraphQlController(UserService userService, BatchLoaderRegistry batchLoaderRegistry) {
        this.userService = userService;
        batchLoaderRegistry.forTypePair(String.class, User.class).registerBatchLoader(
            (userIds, env) -> {
                logger.info("Loading Users for Ratings with userIds'{}'", userIds);

                return userService.findUsersWithIds(userIds);
            }
        );
    }

//    //  Naive Implementierung:
//  @SchemaMapping(typeName = "Rating", field = "author")
//  public User author(Rating rating) {
//    logger.info("Loading User with id {} for rating {}",rating.getUserId(), rating.getId());
//    return userService.getUser(rating.getUserId());
//  }

    @SchemaMapping(typeName = "Rating", field = "author")
    public CompletableFuture<User> author(Rating rating, DataLoader<String, User> userDataLoader) {
        return userDataLoader.load(rating.getUserId());
    }

}
