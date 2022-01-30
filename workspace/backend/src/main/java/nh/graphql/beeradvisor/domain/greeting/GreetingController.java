package nh.graphql.beeradvisor.domain.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

  @Autowired
  private GreetingService greetingService;

  @QueryMapping
  public Greeting hello(@Argument String name, @Argument String msg) {
    Greeting greeting = greetingService.getGreeting(name, msg);

    return greeting;
  }


  @PreAuthorize("hasRole('ROLE_GREETER')")
  @SchemaMapping
  public String greet(Greeting greeting) {
    return greeting.getMsg() + " " + greeting.getName();
  }
}
