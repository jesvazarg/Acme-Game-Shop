
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Game;
import domain.Review;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReviewTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ReviewService	reviewService;

	@Autowired
	private GameService		gameService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	// Un actor autenticado como crítico debe ser capaz de:
	// Administrar sus críticas, lo que incluye crear, editar, listar y borrar.

	//Registrar una nueva crítica
	@Test
	public void driverRegisterReview() {
		final Object testingData[][] = {
			{
				"critic1", 96, "Titulo", "descripción", 0, true, null
			}, {
				"critic1", 96, "Titulo2", "descripción2", 10, false, null
			}, {
				"critic1", 94, "Titulo3", "descripción3", 5, false, IllegalArgumentException.class
			}, {
				"admin", 96, "Titulo4", "descripción4", 3, false, IllegalArgumentException.class
			}, {
				"critic2", 96, "Titulo5", "descripción5", 15, true, ConstraintViolationException.class
			}, {
				"critic3", 95, "", "descripción6", 7, true, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registerReview((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (Boolean) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void registerReview(final String loged, final int gameId, final String title, final String description, final Integer score, final Boolean draft, final Class<?> expected) {

		Game game = null;
		Review review = null;
		Class<?> caught = null;

		try {
			this.authenticate(loged);

			game = this.gameService.findOne(gameId);

			review = this.reviewService.create(game);

			review.setTitle(title);
			review.setDescription(description);
			review.setScore(score);
			review.setDraft(draft);

			review = this.reviewService.save(review);
			this.reviewService.findAll();
			this.reviewService.findOne(review.getId());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Editar una crítica
	@Test
	public void driverEditReview() {
		final Object testingData[][] = {
			{
				"critic1", 127, "Titulo", "descripción", 0, true, null
			}, {
				"critic2", 129, "Titulo2", "descripción2", 10, false, null
			}, {
				"critic1", 125, "Titulo3", "descripción3", 5, false, IncorrectResultSizeDataAccessException.class
			}, {
				"critic3", 129, "Titulo4", "descripción4", 3, true, IllegalArgumentException.class
			}, {
				"critic2", 128, "Titulo5", "descripción5", 15, false, ConstraintViolationException.class
			}, {
				"critic1", 126, "", "descripción6", 7, false, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editReview((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (Boolean) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void editReview(final String loged, final int reviewId, final String title, final String description, final Integer score, final Boolean draft, final Class<?> expected) {

		Review review = null;
		Class<?> caught = null;

		try {
			this.authenticate(loged);

			review = this.reviewService.findOne(reviewId);

			review.setTitle(title);
			review.setDescription(description);
			review.setScore(score);
			review.setDraft(draft);

			review = this.reviewService.save(review);
			this.reviewService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Borrar una crítica
	@Test
	public void driverDeleteReview() {
		final Object testingData[][] = {
			{
				"critic1", 127, null
			}, {
				"critic2", 129, null
			}, {
				"critic3", 129, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteReview((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void deleteReview(final String loged, final int reviewId, final Class<?> expected) {

		Review review = null;
		Class<?> caught = null;

		try {
			this.authenticate(loged);

			review = this.reviewService.findOne(reviewId);

			this.reviewService.delete(review);
			this.reviewService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
