package nh.graphql.beeradvisor;

import nh.graphql.beeradvisor.domain.BeerAdvisorService;
import nh.graphql.beeradvisor.domain.BeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class BeeradvisorGraphQlController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BeerRepository beerRepository;
    private final BeerAdvisorService beerAdvisorService;

    public BeeradvisorGraphQlController(BeerRepository beerRepository, BeerAdvisorService beerAdvisorService) {
        this.beerRepository = beerRepository;
        this.beerAdvisorService = beerAdvisorService;
    }

    // ... todo ... implementieren!
}
