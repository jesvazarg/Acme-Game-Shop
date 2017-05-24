
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Query("select r from Review r where r.game.id=?1 and r.critic.id=?2 and r.draft=false")
	Review findPublishReview(int gameId, int criticId);

	@Query("select r from Review r where r.game.id=?1 and r.draft=false")
	Collection<Review> findAllPublishedReview(int gameId);

	//B3 El máximo, la media y el mínimo número de críticas publicadas por crítico.
	// Media del número de críticas publicadas por crítico
	@Query("select count(r)/(select count(c1) from Critic c1) from Critic c join c.reviews r where r.draft=false")
	Double AvgReviewsPerCritic();
	// El máximo y el mínimo número de críticas publicadas por crítico.
	@Query("select count(r) from Critic c join c.reviews r where r.draft=false group by c order by count(r) DESC")
	Double[] MaxMinReviewsPerCritic();

}
