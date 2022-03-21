package nh.graphql.beeradvisor.auth;

import nh.graphql.beeradvisor.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static nh.graphql.beeradvisor.Slowdown.enableUserServiceSlowDown;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final String userServiceUrl;
  private final RestTemplate restTemplate;
  private final WebClient webClient;

  public UserService(@Value("${beeradvisor.userservice.url}") String url) {
    this.userServiceUrl = url;
    this.restTemplate = new RestTemplate();
    this.webClient = WebClient.builder().baseUrl(userServiceUrl).build();
  }

  public User getUser(String userId) {
    List<String> userIds = Utils.listOf(userId);

    User user = findUsersWithIds(userIds).blockFirst();

    return user;
  }
  public Flux<User> findUsersWithIds(List<String> userIds) {
    var result = webClient.get()
      .uri(uriBuilder -> uriBuilder
        .path("/users/{userIds}")
        .queryParamIfPresent("slowDown", enableUserServiceSlowDown)
        .build(String.join(",", userIds)))
      .retrieve().bodyToFlux(User.class)
      .doFirst(() -> logger.trace("Requesting users with ids '{}'!", userIds))
      .doOnNext(x -> logger.trace("Retrieved Result for user ids '{}: {}'!", userIds, x));

    return result;
  }

  record LoginRequest(String username, String password) {
  }

  public User getUserByLogin(String userName, String password) {
    logger.info("login with userName '{}' from '{}'", userName, userServiceUrl);
    User user = restTemplate.
      postForObject(this.userServiceUrl + "/login",
        new LoginRequest(userName, password),
        User.class, userName);
    return user;
  }
}
