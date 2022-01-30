package nh.graphql.beeradvisor.domain.greeting;

public class Greeting {

  public String name;
  public String msg;

  public Greeting(String name, String msg) {
    this.name = name;
    this.msg = msg;
  }

  public String getName() {
    return name;
  }

  public String getMsg() {
    return msg;
  }
}
