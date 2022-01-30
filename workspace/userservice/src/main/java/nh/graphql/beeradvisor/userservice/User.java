package nh.graphql.beeradvisor.userservice;


/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class User {
  private String id;
  private String login;
  private String name;

  public User(String id, String login, String name) {
    this.id = id;
    this.login = login;
    this.name = name;
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
}
