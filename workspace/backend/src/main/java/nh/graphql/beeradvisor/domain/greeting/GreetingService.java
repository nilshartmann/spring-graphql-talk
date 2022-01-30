package nh.graphql.beeradvisor.domain.greeting;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
  public Greeting getGreeting(String name, String msg) {
    return new Greeting(name, msg);
  }

}
