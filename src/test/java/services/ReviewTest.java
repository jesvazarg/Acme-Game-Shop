
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

}
