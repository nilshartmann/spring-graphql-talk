package nh.graphql.beeradvisor.auth.graphql;

import nh.graphql.beeradvisor.auth.JwtTokenService;
import nh.graphql.beeradvisor.auth.User;
import nh.graphql.beeradvisor.auth.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    public LoginController(UserService userService, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    @MutationMapping
    public LoginResponse login(@Argument String username, @Argument String password) {
      User user = userService.getUserByLogin(username, password);
      if (user == null) {
        return LoginResponse.failed("Unknown username/password");
      }

      final String token = jwtTokenService.createTokenForUser(user);

      return LoginResponse.succeeded(new Authentication(user.getId(), user.getName(), token));
    }
}
