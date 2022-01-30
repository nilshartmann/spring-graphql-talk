package nh.graphql.beeradvisor.graphql;

import graphql.schema.DataFetcher;
import nh.graphql.beeradvisor.domain.Beer;
import nh.graphql.beeradvisor.domain.Rating;
import nh.graphql.beeradvisor.domain.Shop;
import nh.graphql.beeradvisor.domain.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class BeerController {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final ShopRepository shopRepository;

  public BeerController(ShopRepository shopRepository) {
    this.shopRepository = shopRepository;
  }

  @SchemaMapping
  public List<Shop> shops(Beer beer) {
    final String beerId = beer.getId();
    return shopRepository.findShopsWithBeer(beerId);
  }

  @SchemaMapping
  public Integer averageStars(Beer beer) {
    return (int) Math.round(beer.getRatings().stream().mapToDouble(Rating::getStars).average().getAsDouble());
  }

  @SchemaMapping
  public List<Rating> ratingsWithStars(Beer beer, @Argument int stars) {
    return beer.getRatings().stream().filter(r -> r.getStars() == stars).collect(Collectors.toList());
  }
}
