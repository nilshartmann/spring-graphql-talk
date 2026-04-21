package nh.graphql.beeradvisor.graphql;

import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserService;
import nh.graphql.beeradvisor.domain.Beer;
import nh.graphql.beeradvisor.domain.BeerAdvisorService;
import nh.graphql.beeradvisor.domain.BeerRepository;
import nh.graphql.beeradvisor.domain.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

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

    @QueryMapping
    public Iterable<Beer> beers() {
        return beerRepository.findAll();
    }

    @QueryMapping
    public Optional<Beer> beer(@Argument String beerId) {
        return beerRepository.findById(beerId);
    }

    record AddRatingInput(String beerId, String userId, String comment, int stars) {
    }

    @MutationMapping
    Rating addRating(@Argument AddRatingInput ratingInput) {
        logger.debug("Rating Input {}", ratingInput);
        var newRating = beerAdvisorService.addRating(ratingInput.userId(),
            ratingInput.beerId(),
            ratingInput.comment(),
            ratingInput.stars()
        );

        return newRating;
    }

}
