
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReviewRepository;
import domain.Critic;
import domain.Game;
import domain.Review;

@Service
@Transactional
public class ReviewService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ReviewRepository	reviewRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private CriticService		criticService;


	// Constructors------------------------------------------------------------
	public ReviewService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Review findOne(final int reviewId) {
		Review result;

		result = this.reviewRepository.findOne(reviewId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Review> findAll() {
		Collection<Review> result;

		result = this.reviewRepository.findAll();

		return result;
	}

	public Review create(final Game game) {
		Review result;
		Calendar today;
		Critic principal;

		today = Calendar.getInstance();
		today.set(Calendar.MILLISECOND, -10);
		principal = this.criticService.findByPrincipal();
		Assert.notNull(principal);

		result = new Review();
		result.setMoment(today.getTime());
		result.setDraft(true);
		result.setCritic(principal);
		result.setGame(game);

		return result;
	}

	public Review save(final Review review) {
		Assert.notNull(review);
		Review result;
		Critic principal;
		Review publishReview;
		Boolean canBePublished = true;

		principal = this.criticService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(review.getCritic().getId() == principal.getId());

		publishReview = this.reviewRepository.findPublishReview(review.getGame().getId(), review.getCritic().getId());
		if (publishReview != null)
			if ((publishReview.getId() != review.getId()) && (review.getDraft() == false))
				canBePublished = false;
		Assert.isTrue(canBePublished);

		result = this.reviewRepository.save(review);

		return result;
	}

	public void delete(final Review review) {
		Assert.notNull(review);
		Assert.isTrue(review.getId() != 0);
		Critic principal;

		principal = this.criticService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(review.getCritic().getId() == principal.getId());

		this.reviewRepository.delete(review);
	}

	// Other business methods -------------------------------------------------
	public void publishReview(final Review review) {
		Assert.notNull(review);
		Assert.isTrue(review.getDraft() == true);

		review.setDraft(false);
		this.save(review);
	}

	public Collection<Review> findAllPublishedReview(final int gameId) {
		return this.reviewRepository.findAllPublishedReview(gameId);
	}

	public Review findPublishedReview(final int gameId, final int criticId) {
		return this.reviewRepository.findPublishReview(gameId, criticId);
	}

	public Double[] MaxAvgMinReviewsPerCritic() {
		return this.reviewRepository.MaxAvgMinReviewsPerCritic();
	}

}
