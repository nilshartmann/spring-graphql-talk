package nh.graphql.beeradvisor.userservice;


import java.util.LinkedList;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class User {
  private String id;
  private String login;
  private String name;
  private final List<String> roles;

  public User(String id, String login, String name, boolean isAdmin) {
    this.id = id;
    this.login = login;
    this.name = name;
    roles = isAdmin ? List.of("ROLE_USER", "ROLE_ADMIN") : List.of("ROLE_USER");
  }

  public User(String id, String login, String name) {
    this(id, login, name, false);
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getLogin() {
    return login;
  }

  public String getName() {
    return name;
  }

  public List<String> getRoles() {
    return roles;
  }
}
