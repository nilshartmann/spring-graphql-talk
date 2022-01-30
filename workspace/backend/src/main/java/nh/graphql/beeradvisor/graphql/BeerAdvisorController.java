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

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class BeerAdvisorController {
  private final Logger logger = LoggerFactory.getLogger(BeerAdvisorController.class);

  private final BeerService beerService;
  private final ShopRepository shopRepository;
  private final RatingService ratingService;
  private final RatingPublisher ratingPublisher;

  public BeerAdvisorController(BeerService beerService, ShopRepository shopRepository, RatingService ratingService, RatingPublisher ratingPublisher) {
    this.beerService = beerService;
    this.shopRepository = shopRepository;
    this.ratingService = ratingService;
    this.ratingPublisher = ratingPublisher;
  }

  @MutationMapping
  public Beer updateBeerName(@Argument String beerId, @Argument String newName) {
    return beerService.updateBeer(beerId, newName);
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
    return ratingService.addRating(ratingInput);
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
