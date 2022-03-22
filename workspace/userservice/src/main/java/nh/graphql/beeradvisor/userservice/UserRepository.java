package nh.graphql.beeradvisor.userservice;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class UserRepository {

  private final List<User> users = new LinkedList<User>();

  public UserRepository() {
    users.add(new User("U1", "waldemar", "Waldemar Vasu"));
    users.add(new User("U2", "karl", "Karl Marx"));
    users.add(new User("U3", "alessa", "Alessa Bradley"));
    users.add(new User("U4", "lauren", "Lauren Jones"));
    users.add(new User("U5", "nils", "Nils", true));
  }

  public User findUserWithId(String id) {
    return users.stream().filter(u -> id.equals(u.getId()))
      .findAny().orElse(null);
  }

  public List<User> findUsersWithId(String[] ids) {
    final List<String> idList = Arrays.asList(ids);

    return idList.stream()
      .map(userId -> this.users.stream()
        .filter(u -> userId.equals(u.getId()))
        .findFirst().orElse(null))
      .toList();
  }

  public User findUserWithLogin(String login) {
    return this.users.stream().filter(
      u -> u.getLogin().equals(login)
    ).findFirst().orElse(null);
  }
}
