package nh.graphql.beeradvisor.domain;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public record AddRatingInput(String beerId, String userId, String comment, int stars) {
}
