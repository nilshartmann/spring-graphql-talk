package nh.graphql.beeradvisor.graphql;

import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserService;
import nh.graphql.beeradvisor.domain.BeerAdvisorService;
import nh.graphql.beeradvisor.domain.BeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class BeerAdvisorGraphQLController {
    private final Logger logger = LoggerFactory.getLogger(BeerAdvisorGraphQLController.class);

    private final BeerRepository beerRepository;
    private final BeerAdvisorService beerAdvisorService;
    private final UserService userService;

    public BeerAdvisorGraphQLController(BeerRepository beerRepository, BeerAdvisorService beerAdvisorService, UserService userService) {
        this.beerRepository = beerRepository;
        this.beerAdvisorService = beerAdvisorService;
        this.userService = userService;
    }

}
