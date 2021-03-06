package nh.graphql.beeradvisor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nh.graphql.beeradvisor.auth.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Represents a Rating for a single beer given by a single Author
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
@Table(name = "rating_")
public class Rating {
  private static Logger logger = LoggerFactory.getLogger(Rating.class);

  @Id
  private String id;

  @NotNull
  private String comment;

  @NotNull
  private int stars;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private Beer beer;

  @NotNull
  private String userId;

  protected Rating() {

  }

  public Rating(Beer beer, String userId, String id, String comment, int stars) {
    this.beer = beer;
    this.userId = userId;
    this.id = id;
    this.comment = comment;
    this.stars = stars;
  }

  public String getId() {
    return id;
  }

  public String getComment() {
    return comment;
  }

  public Beer getBeer() {
    return beer;
  }

  public String getUserId() {
    return this.userId;
  }

  public int getStars() {
    return stars;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Rating rating = (Rating) o;
    return id.equals(rating.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public String toString() {
    return "[Rating id=" + this.id + ", userId=" + this.userId + "]";
  }
}
