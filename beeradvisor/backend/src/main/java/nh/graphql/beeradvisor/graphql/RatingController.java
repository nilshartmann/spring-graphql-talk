package nh.graphql.beeradvisor.graphql;

import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserService;
import nh.graphql.beeradvisor.domain.Rating;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class RatingController {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final UserService userService;

  public RatingController(UserService userService, BatchLoaderRegistry batchLoaderRegistry) {
    this.userService = userService;
    batchLoaderRegistry.forTypePair(String.class,User.class).registerBatchLoader(
      (userIds,env)-> {
        logger.info("Loading Users for Ratings with userIds'{}'",userIds);
        return userService.findUsersWithIds(userIds);
      }
    );

  }

//  Naive Implementierung:
//  @SchemaMapping(typeName = "Rating", field = "author")
//  public User author(Rating rating) {
//    logger.info("Loading User with id {} for rating {}",rating.getUserId(), rating.getId());
//    return userService.getUser(rating.getUserId());
//  }

  // Besser:
  @SchemaMapping(typeName = "Rating", field = "author")
  public Mono<User> author(Rating rating) {
    logger.info("Loading User with id {} for rating {}",rating.getUserId(), rating.getId());
    return userService.findUser(rating.getUserId());
  }


  // Noch Besser:
//  @SchemaMapping(typeName = "Rating", field = "author")
//  public CompletableFuture<User> author(Rating rating, DataLoader<String, User> userDataLoader) {
//    return userDataLoader.load(rating.getUserId());
//  }

//  @BatchMapping
//  public Flux<User> author(List<Rating> ratings) {
//    logger.info("Collecting Users for {}", ratings);
//    var distinctUserIds = ratings.stream()
//      .map(Rating::getUserId)
//      .distinct()
//      .toList();
//    logger.info("Collecting Users with distinct user ids {}", distinctUserIds);
//
//    // https://github.com/spring-projects/spring-graphql/issues/260#issuecomment-1013767722
//    var result = userService.findUsersWithIds(distinctUserIds)
//      .collectList()
//      .flatMapIterable(users -> ratings.stream()
//        .map(r -> users.stream()
//          .filter(user -> user.getId().equals(r.getUserId()))
//          .findFirst()
//          .orElse(null)
//        ).toList()
//      );
//    return result;
//  }
}
