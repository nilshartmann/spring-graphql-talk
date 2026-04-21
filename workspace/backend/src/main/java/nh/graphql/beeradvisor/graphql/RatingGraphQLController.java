package nh.graphql.beeradvisor.graphql;

import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserService;
import nh.graphql.beeradvisor.domain.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RatingGraphQLController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    public RatingGraphQLController(UserService userService) {
        this.userService = userService;
    }

    // { beers { ratings{ author { id name }}}}
    // -> Log File "Loading User with id"
    @SchemaMapping(typeName = "Rating", field = "author")
    public User author(Rating rating) {
        logger.info("Loading User with id {} for rating {}", rating.getUserId(), rating.getId());
        return userService.getUser(rating.getUserId());
    }
}
