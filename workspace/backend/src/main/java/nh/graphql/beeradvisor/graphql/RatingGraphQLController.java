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

@Controller
public class RatingGraphQLController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    public RatingGraphQLController(UserService userService, BatchLoaderRegistry batchLoaderRegistry) {
        this.userService = userService;
        batchLoaderRegistry.forTypePair(String.class,User.class).registerBatchLoader(
            (userIds,env)-> {
                logger.info("Loading Users for Ratings with userIds'{}'",userIds);
                return userService.findUsersWithIds(userIds);
            }
        );
    }

    // { beers { ratings{ author { id name }}}}
    // -> Log File "Loading User with id"
    // -> Log File "Loading Users for Ratings "
    @SchemaMapping(typeName = "Rating", field = "author")
    public CompletableFuture<User> author(Rating rating, DataLoader<String, User> userDataLoader) {
        logger.info("Loading User with id {} for rating {}", rating.getUserId(), rating.getId());
        return userDataLoader.load(rating.getUserId());
    }
}
