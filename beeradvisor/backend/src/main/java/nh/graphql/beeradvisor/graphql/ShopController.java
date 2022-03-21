package nh.graphql.beeradvisor.graphql;

import nh.graphql.beeradvisor.domain.Beer;
import nh.graphql.beeradvisor.domain.BeerRepository;
import nh.graphql.beeradvisor.domain.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class ShopController {
  private static Logger logger = LoggerFactory.getLogger(ShopController.class);

  private final BeerRepository beerRepository;

  public ShopController(BeerRepository beerRepository) {
    this.beerRepository = beerRepository;
  }

  @SchemaMapping
  public List<Beer> beers(Shop shop) {
    final List<String> beerIds = shop.getBeers();
    return beerRepository.findByIdIn(beerIds);
  }

  @SchemaMapping
  public AddressField address(Shop shop) {
    return new AddressField(shop);
  }
}
