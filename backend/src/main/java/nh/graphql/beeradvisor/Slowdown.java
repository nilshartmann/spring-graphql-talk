package nh.graphql.beeradvisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Random;

/**
 * Utility to simulate slow interactions
 */
public class Slowdown {

  private static final Logger log = LoggerFactory.getLogger( Slowdown.class );

  private static final Random random = new Random();

  public final static Optional<Boolean> enableUserServiceSlowDown = Optional.of(false);

  private static void slowDown(boolean enableSlowdown) {
    if (enableSlowdown) {
      try {
        int x = random.nextInt(1300) + 700;
        log.info("Slowdown for {}ms", x);
        Thread.sleep(x);
      } catch (InterruptedException e) {
        log.info("Interrupted");
      }
    }
  }
}
