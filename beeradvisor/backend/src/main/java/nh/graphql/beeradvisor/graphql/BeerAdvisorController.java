package nh.graphql.beeradvisor.graphql;

import nh.graphql.beeradvisor.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class BeerAdvisorController {
  private final Logger logger = LoggerFactory.getLogger(BeerAdvisorController.class);

  private final BeerRepository beerRepository;
  private final BeerAdvisorService beerAdvisorService;
  private final ShopRepository shopRepository;
  private final RatingPublisher ratingPublisher;

  public BeerAdvisorController(BeerRepository beerRepository, BeerAdvisorService beerAdvisorService, ShopRepository shopRepository, RatingPublisher ratingPublisher) {
    this.beerRepository = beerRepository;
    this.beerAdvisorService = beerAdvisorService;
    this.shopRepository = shopRepository;
    this.ratingPublisher = ratingPublisher;
  }

  @QueryMapping
  public Optional<Beer> beer(@Argument String beerId) {
    return beerRepository.findById(beerId);
  }

  @QueryMapping
  public Iterable<Beer> beers() {
    return beerRepository.findAll();
  }

  @MutationMapping
  public Beer updateBeerName(@Argument String beerId, @Argument String newName) {
    return beerAdvisorService.updateBeer(beerId, newName);
  }

  @QueryMapping
  public Shop shop(@Argument String shopId) {
    return shopRepository.findShop(shopId);
  }

  @QueryMapping
  public List<Shop> shops() {
    return shopRepository.findAll();
  }

  @MutationMapping
  public Rating addRating(@Argument AddRatingInput ratingInput) {
    logger.debug("Rating Input {}", ratingInput);
    return beerAdvisorService.addRating(ratingInput.userId(),
      ratingInput.beerId(),
      ratingInput.comment(),
      ratingInput.stars()
    );
  }

  @SubscriptionMapping
  public Flux<Rating> onNewRating() {
    return this.ratingPublisher.getPublisher();
  }

  @SubscriptionMapping
  public Flux<Rating> newRatings(@Argument String beerId) {
    logger.info("Subscription for 'newRatings' (" + beerId + ") received");
    return this.ratingPublisher.getPublisher(beerId);
  }

  @QueryMapping
  public String ping(@Argument String msg) {
    if (msg == null) {
      return "HELLO";
    }

    return "HELLO, " + msg;
  }
}
