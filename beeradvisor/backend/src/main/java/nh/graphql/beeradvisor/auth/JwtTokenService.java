package nh.graphql.beeradvisor.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Service
public class JwtTokenService {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Value("${jwt.expirationInMs}")
  private int jwtExpirationInMs;

  private final SecretKey secretKey;

  private final JwtParser jwtParser;

  public JwtTokenService() {

    this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    this.jwtParser = Jwts.parserBuilder()
      .setSigningKey(secretKey)
      .build();

  }

  public String createTokenForUser(User user) {

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

    return createToken(user.getId(), now, expiryDate);
  }

  private String createToken(String userId, Date issuedAt, Date expiryDate) {
    return Jwts.builder()
        .setSubject(userId)
        .setIssuedAt(issuedAt)
        .setExpiration(expiryDate)
        .signWith(secretKey)
        .compact();
  }

  public String getUserIdFromToken(String token) {
    Claims claims = jwtParser
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }

  public boolean validateToken(String authToken) {
    try {
      jwtParser.parseClaimsJws(authToken);
      return true;
    } catch (Exception ex) {
      logger.error("Invalid JWT token: " + ex, ex);
    }

    return false;
  }

  @PostConstruct
  public void generateNeverExpiringToken()
  {
    final String userId = "U5";
    Calendar expiry = Calendar.getInstance();
    expiry.set(2072, Calendar.JUNE, 12, 22, 13, 7);
    Calendar issued = Calendar.getInstance();
    issued.set(2022, Calendar.JANUARY, 5, 9, 4, 7);

    String token = createToken(userId, issued.getTime(), expiry.getTime());

    logger.info("""

        ===============================================================
        NEVER EXPIRING JWT TOKEN for User-Id '{}'
        ===============================================================
        {"Authorization": "Bearer {}"}
        ===============================================================
        """,
      userId,
      token);
  }
}
