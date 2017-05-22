
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
	@Query("select max(c.reviews.size), avg(c.reviews.size), min(c.reviews.size) from Critic c")
	Double[] MaxAvgMinReviewsPerCritic();

}
