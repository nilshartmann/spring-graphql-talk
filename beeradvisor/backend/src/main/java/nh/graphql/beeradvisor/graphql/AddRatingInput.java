package nh.graphql.beeradvisor.graphql;

import javax.validation.constraints.Max;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public record AddRatingInput(String beerId, String userId, String comment, @Max(5) int stars) {
}
