package nh.graphql.beeradvisor.userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@RestController
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UserRepository userRepository;
  private final static Random random = new Random();

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping(value = "/users/{userIds}")
  public List<User> users(@PathVariable String[] userIds, @RequestParam(name = "slowDown", defaultValue = "false") boolean enableSlowdown) {

    logger.info("Finding users with ids '{}'", Arrays.asList(userIds));

    List<User> result = userRepository.findUsersWithId(userIds);
    slowDown(enableSlowdown);
    return result;
  }

  @GetMapping(value = "/login/{login}")
  public User login(@PathVariable String login) {

    logger.info("Finding users with login '{}'", login);

    return userRepository.findUserWithLogin(login);
  }

  private static void slowDown(boolean enableSlowdown) {
    if (enableSlowdown) {
      try {
        int x = random.nextInt(1500) + 500;
        Thread.sleep(x);
      } catch (InterruptedException e) {
        logger.info("Interrupted");
      }
    }
  }

}
