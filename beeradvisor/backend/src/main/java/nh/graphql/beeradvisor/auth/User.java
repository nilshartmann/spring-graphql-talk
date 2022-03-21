package nh.graphql.beeradvisor.auth;

import org.springframework.security.core.AuthenticatedPrincipal;

import java.util.List;

public class User implements AuthenticatedPrincipal {

  private String id;
  private String login;
  private String name;
  private List<String> roles;

  User() {
  }

  public User(String id, String login, String name, List<String> roles) {
    this.id = id;
    this.login = login;
    this.name = name;
    this.roles = roles;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * @return the login
   */
  public String getLogin() {
    return login;
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

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}
