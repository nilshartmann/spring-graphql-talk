package nh.graphql.beeradvisor.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Service
public class BeerAdvisorService {

	private final BeerRepository beerRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  public BeerAdvisorService(BeerRepository beerRepository, ApplicationEventPublisher applicationEventPublisher) {
		this.beerRepository = beerRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Transactional
  public Rating addRating(String userId,
                          String beerId,
                          String comment,
                          int stars) {
    Beer beer = beerRepository.findById(beerId).orElseThrow();

    Rating rating = beer.addRating(userId, comment, stars);
    beerRepository.save(beer);

    applicationEventPublisher.publishEvent(new RatingCreatedEvent(rating.getId()));
    return rating;
  }


  public int calculateAverageStars(Beer beer) {
    slowdown(800);
    var result = (int) Math.round(beer.getRatings().stream().mapToInt(Rating::getStars).average().orElse(0));

    return result;
  }

  @Async
  public CompletableFuture<Integer> calculateAverageStars_async(Beer beer) {
    slowdown(800);
    var result = (int) Math.round(beer.getRatings().stream().mapToInt(Rating::getStars).average().orElse(0));

    return CompletableFuture.completedFuture(result);
  }


  public static void slowdown(long l) {
    try {
      Thread.sleep(l);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }


}
