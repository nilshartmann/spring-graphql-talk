package nh.graphql.beeradvisor.userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping(value = "/user/{userId}")
  public User user(@PathVariable String userId, @RequestParam(name = "slowDown", defaultValue = "false") boolean enableSlowdown) {
    logger.info("Finding User with id {}", userId);
    slowDown(enableSlowdown);
    return userRepository.findUserWithId(userId);
  }

  record LoginRequest(String username, String password) {
  }

  @PostMapping(value = "/login" )
  public User login(@RequestBody LoginRequest loginRequest) {
    logger.info("Finding users with login '{}'", loginRequest);

    // 😱 😱 😱
    if (loginRequest.password.length()<5) {
      logger.info("Invalid password supplied '{}'. For testing, please specify any password with at least five chars", loginRequest.password);
      return null;
    }

    return userRepository.findUserWithLogin(loginRequest.username());
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
