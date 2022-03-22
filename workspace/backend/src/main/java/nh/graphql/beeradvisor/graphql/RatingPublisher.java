package nh.graphql.beeradvisor.graphql;

import nh.graphql.beeradvisor.domain.Rating;
import nh.graphql.beeradvisor.domain.RatingCreatedEvent;
import nh.graphql.beeradvisor.domain.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

/**
 * RatingPublisher
 */
@Component
public class RatingPublisher {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final RatingRepository ratingRepository;
  private final Sinks.Many<Rating> sink;

  public RatingPublisher(RatingRepository ratingRepository) {
    this.ratingRepository = ratingRepository;
    this.sink = Sinks.many()
      .multicast()
      .onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
  }

  @TransactionalEventListener
  public void onNewRating(RatingCreatedEvent ratingCreatedEvent) {
    logger.info("onNewRating {}", ratingCreatedEvent);

    ratingRepository.findById(ratingCreatedEvent.ratingId())
      .ifPresent(theRating -> this.sink.emitNext(theRating, Sinks.EmitFailureHandler.FAIL_FAST));
  }

  public Flux<Rating> getPublisher() {
    return this.sink.
      asFlux();
  }

  public Flux<Rating> getPublisher(String beerId) {
    return this.getPublisher().filter(rating -> beerId.equals(rating.getBeer().getId()));
  }
}