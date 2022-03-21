package nh.graphql.beeradvisor.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Beer updateBeer(String beerId, String newName) {
		final Beer beer = beerRepository.findById(beerId).orElseThrow();
		beer.setName(newName);

		beerRepository.save(beer);
		return beer;
	}

  @Transactional
  // EXAMPLE:
  // access fields from graphql fetchers in Spring Security: userId must match logged
  // in user
  // (checks makes no sense, just for demo!)
  @PreAuthorize("isAuthenticated() && #userId == authentication.principal.id")
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

}
