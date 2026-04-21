package nh.graphql.beeradvisor.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * RatingRepository
 */
public interface RatingRepository extends CrudRepository<Rating, String> {

}