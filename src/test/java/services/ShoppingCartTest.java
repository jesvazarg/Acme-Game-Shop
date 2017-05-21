
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Game;
import domain.ShoppingCart;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ShoppingCartTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ShoppingCartService	shoppingCartService;

	@Autowired
	private GameService			gameService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	//Añadir juegos en su carro de la compra y realizar la compra. 
	//La edad del comprador de un videojuego debe ser mayor o igual a la que el juego requiere para ser jugado. 
	//Un cliente no puede comprar un juego si no tiene una tarjeta de crédito válida.

	//El primer test negativo es causado porque el customer3 no tiene la edad permitida para comprarlo,
	//el segundo es porque un developer no puede añadir juegos al carrito y el ultimo es causado porque
	//se le pasa un id de juego que no existe.
	@Test
	public void driverAñadirYEliminarJuegosAlCarrito() {
		final Object testingData[][] = {
			{
				"customer1", 94, null
			}, {
				"customer1", 95, null
			}, {
				"customer2", 96, null
			}, {
				"customer2", 95, null
			}, {
				"customer3", 96, null
			}, {
				"customer3", 94, IllegalArgumentException.class
			}, {
				"developer1", 94, IllegalArgumentException.class
			}, {
				"customer1", 0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.añadirYEliminarJuegosAlCarrito((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void añadirYEliminarJuegosAlCarrito(final String username, final int idGame, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Game game = this.gameService.findOne(idGame);
			ShoppingCart shoppingCart = this.shoppingCartService.addGameToShoppingCart(game);
			Assert.isTrue(shoppingCart.getGames().contains(game));

			shoppingCart = this.shoppingCartService.removeGameToShoppingCart(game);
			Assert.isTrue(!shoppingCart.getGames().contains(game));

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//El primer test negativo es causado ya que le ponemos un usuario que no es un customer,
	//el segundo de ellos el id del game no existe, y los dos ultimos es causado ya que ni
	//el custmer2 ni el customer3 tienen tarjeta de credito
	@Test
	public void driverComprarJuegosDelCarrito() {
		final Object testingData[][] = {
			{
				"customer1", 94, null
			}, {
				"customer1", 95, null
			}, {
				"NoExist", 94, IllegalArgumentException.class
			}, {
				"customer1", 0, IllegalArgumentException.class
			}, {
				"customer2", 95, NullPointerException.class
			}, {
				"customer3", 96, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.comprarJuegosDelCarrito((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void comprarJuegosDelCarrito(final String username, final int idGame, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);
			ShoppingCart shoppingCart;
			Game game;

			game = this.gameService.findOne(idGame);
			shoppingCart = this.shoppingCartService.addGameToShoppingCart(game);
			Assert.isTrue(shoppingCart.getGames().contains(game));

			this.shoppingCartService.buyGamesInShoppingCart(shoppingCart, 0);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
