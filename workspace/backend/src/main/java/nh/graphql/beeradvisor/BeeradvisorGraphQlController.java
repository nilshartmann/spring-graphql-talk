package nh.graphql.beeradvisor;

import nh.graphql.beeradvisor.domain.BeerRepository;
import org.springframework.stereotype.Controller;

@Controller
public class BeeradvisorGraphQlController {

  private final BeerRepository beerRepository;

  public BeeradvisorGraphQlController(BeerRepository beerRepository) {
    this.beerRepository = beerRepository;
  }
}
