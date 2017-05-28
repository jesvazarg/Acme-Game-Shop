
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Category;
import domain.Game;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class GameTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private GameService		gameService;

	@Autowired
	private CategoryService	categoryService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	//-	Un actor autenticado como developer debe ser capaz de:
	//	Crear, editar y borrar juegos.

	//El primer test negativo es causado porque no nos hemos logueado developer, el segundo de
	//ellos se produce porque introducimos un campo vacio (descripcion) y el tercero es provocado porque le
	//pasamos un id de categoria que no existe.
	@Test
	public void driverCreateGame() {
		final Object testingData[][] = {
			{
				"developer1", "Titulo1", "Descripcion1", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 13, 20.0, 89, null
			}, {
				"developer2", "Titulo2", "Descripcion2", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 14, 30.0, 90, null
			}, {
				"developer3", "Titulo3", "Descripcion3", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 18, 15.0, 91, null
			}, {
				"admin", "Titulo4", "Descripcion4", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, 12.0, 90, IllegalArgumentException.class
			}, {
				"developer1", "Titulo5", "", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, 12.0, 90, ConstraintViolationException.class
			}, {
				"developer1", "Titulo5", "Descripcion5", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, 12.0, 70, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createGame((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (int) testingData[i][4], (double) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);
	}
	protected void createGame(final String username, final String title, final String description, final String picture, final int age, final double price, final int categoryId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Game game;
			Game aux;

			game = this.gameService.create();
			game.setTitle(title);
			game.setDescription(description);
			game.setPicture(picture);
			game.setAge(age);
			game.setPrice(price);

			final Collection<Category> categories = new ArrayList<Category>();
			final Category category = this.categoryService.findOne(categoryId);
			categories.add(category);
			aux = this.gameService.save(game);
			this.categoryService.select(categories, aux);

			this.gameService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//El primer test negativo es causado porque no nos hemos logueado developer, el segundo de
	//ellos se produce porque introducimos un campo vacio (descripcion) y el tercero es provocado porque le
	//pasamos un id de categoria que no existe.
	@Test
	public void driverEditGame() {
		final Object testingData[][] = {
			{
				"developer1", "Titulo10", "Descripcion1", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 13, 20.0, 89, 94, null
			}, {
				"developer2", "Titulo2", "Descripcion20", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 14, 30.0, 90, 95, null
			}, {
				"developer2", "Titulo3", "Descripcion3", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 18, 15.0, 91, 96, null
			}, {
				"admin", "Titulo4", "Descripcion4", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, 12.0, 90, 94, IllegalArgumentException.class
			}, {
				"developer1", "Titulo5", "", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, 12.0, 90, 94, ConstraintViolationException.class
			}, {
				"developer1", "Titulo5", "Descripcion5", "http://mrbean-website-cache.s3.amazonaws.com/wp-content/uploads/2013/11/smiling-mr-bean-grey.jpg", 11, 12.0, 90, 70, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editGame((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (int) testingData[i][4], (double) testingData[i][5], (int) testingData[i][6], (int) testingData[i][7],
				(Class<?>) testingData[i][8]);
	}
	protected void editGame(final String username, final String title, final String description, final String picture, final int age, final double price, final int categoryId, final int gameId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Game game;

			game = this.gameService.findOne(gameId);
			game.setTitle(title);
			game.setDescription(description);
			game.setPicture(picture);
			game.setAge(age);
			game.setPrice(price);

			final Collection<Category> categories = new ArrayList<Category>();
			final Category category = this.categoryService.findOne(categoryId);
			categories.add(category);

			this.categoryService.select(categories, game);
			this.gameService.save(game);

			this.gameService.findAll();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//El primer test negativo es causado porque la id del juego es de un juego que pertence a otro developer 
	//y el segundo es porque no estamos logueado como developer.

	@Test
	public void driverDeleteGame() {
		final Object testingData[][] = {
			{
				"developer1", 94, null
			}, {
				"developer1", 95, IllegalArgumentException.class
			}, {
				"admin", 94, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteGame((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteGame(final String user, final int gameId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);
			final Game game = this.gameService.findOne(gameId);

			this.gameService.delete(game);

			this.gameService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//Probamos la búsqueda de juegos usando una palabra clave contenia en su nombre o en
	// su descripcion.El primer test negativo es causado porque no estamos logueados.

	@Test
	public void driverFindGameByNameOrDescription() {
		final Object testingData[][] = {
			{
				"developer1", "diablo", null
			}, {
				"developer1", "esclavo", null
			}, {
				"", "diablo", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.findGameByNameOrDescription((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void findGameByNameOrDescription(final String user, final String game, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);
			final Collection<Game> games = this.gameService.findByKey(game);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//Probamos el filtrado de juegos mediante un rango de precios o una categoria
	//El primer test negativo es causado porque no estamos logueados.

	@Test
	public void driverFilterGameByCategoryOrPrice() {
		final Object testingData[][] = {
			{
				"developer1", "sport", 0.0, 0.0, null
			}, {
				"customer1", "sport", 0.0, 60.0, null
			}, {
				"customer1", "sport", 15.0, 0.0, null
			}, {
				"customer1", "sport", 25.0, 60.0, null
			}, {
				"customer1", "sport", 0.0, 60.0, null
			}, {
				"", "sport", 0.0, 60.0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.filterGameByCategoryOrPrice((String) testingData[i][0], (String) testingData[i][1], (Double) testingData[i][2], (Double) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void filterGameByCategoryOrPrice(final String user, final String category, final Double minPrice, final Double maxPrice, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);
			final Collection<Game> games = this.gameService.findByCategoryOrPrice(category, minPrice, maxPrice);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
