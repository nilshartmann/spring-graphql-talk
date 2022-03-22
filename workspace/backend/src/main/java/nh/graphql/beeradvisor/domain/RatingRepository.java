package nh.graphql.beeradvisor.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * RatingRepository
 */
public interface RatingRepository extends CrudRepository<Rating, String> {

}