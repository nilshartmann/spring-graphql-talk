package nh.graphql.beeradvisor.graphql;

import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserService;
import nh.graphql.beeradvisor.domain.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;

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

  @BatchMapping
  public Flux<User> author(List<Rating> ratings) {
    logger.info("Collecting Users for {}", ratings);
    var distinctUserIds = ratings.stream()
      .map(Rating::getUserId)
      .distinct()
      .toList();
    logger.info("Collecting Users with distinct user ids {}", distinctUserIds);

    // https://github.com/spring-projects/spring-graphql/issues/260#issuecomment-1013767722
    var result = userService.findUsersWithIds(distinctUserIds)
      .collectList()
      .flatMapIterable(users -> ratings.stream()
        .map(r -> users.stream()
          .filter(user -> user.getId().equals(r.getUserId()))
          .findFirst()
          .orElse(null)
        ).toList()
      );
    return result;
  }
}
