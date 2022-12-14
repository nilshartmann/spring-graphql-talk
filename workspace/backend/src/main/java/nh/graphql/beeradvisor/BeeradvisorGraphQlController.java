package nh.graphql.beeradvisor;

import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserService;
import nh.graphql.beeradvisor.domain.Beer;
import nh.graphql.beeradvisor.domain.BeerAdvisorService;
import nh.graphql.beeradvisor.domain.BeerRepository;
import nh.graphql.beeradvisor.domain.Rating;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Min;
import java.util.Optional;

@Controller
public class BeeradvisorGraphQlController {

  private final UserService userService;
  private final BeerRepository beerRepository;
  private final BeerAdvisorService beerAdvisorService;

  public BeeradvisorGraphQlController(UserService userService, BeerRepository beerRepository, BeerAdvisorService beerAdvisorService) {
    this.userService = userService;
    this.beerRepository = beerRepository;
    this.beerAdvisorService = beerAdvisorService;
  }

  @QueryMapping
  public Iterable<Beer> beers() {
    return beerRepository.findAll();
  }

  @QueryMapping
  public Optional<Beer> beer(@Argument String beerId) {
    return beerRepository.findById(beerId);
  }

  @SchemaMapping
  public Mono<User> givenBy(Rating rating) {
    var userId = rating.getUserId();
    return userService.findUser(userId);
  }

  record AddRatingInput(String beerId, String userId,
                        @Min(1)
                        int stars, String comment) {}

  @MutationMapping
  public Rating addRating(@Argument AddRatingInput ratingInput) {
    return this.beerAdvisorService.addRating(
      ratingInput.userId(),
      ratingInput.beerId(),
      ratingInput.comment(),
      ratingInput.stars()
    );
  }
}
