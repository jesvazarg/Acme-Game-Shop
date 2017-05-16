
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.GameService;
import services.ShoppingCartService;
import controllers.AbstractController;
import domain.Game;
import domain.ShoppingCart;

@Controller
@RequestMapping("/shoppingCart/customer")
public class ShoppingCartCustomerController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ShoppingCartService	shoppingCartService;

	@Autowired
	private GameService			gameService;


	// Constructors -----------------------------------------------------------
	public ShoppingCartCustomerController() {
		super();
	}

	// Display ---------------------------------------------------------------	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		ShoppingCart shoppingCart;
		Collection<Game> games;

		shoppingCart = this.customerService.findByPrincipal().getShoppingCart();
		games = shoppingCart.getGames();

		result = new ModelAndView("shoppingCart/display");
		result.addObject("games", games);
		result.addObject("requestURI", "shoppingCart/customer/display.do");

		return result;
	}

	// Buy games ---------------------------------------------------------------
	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public ModelAndView buy() {
		ModelAndView result;
		ShoppingCart shoppingCart;
		Collection<Game> games;

		shoppingCart = this.customerService.findByPrincipal().getShoppingCart();
		this.shoppingCartService.buyGamesInShoppingCart(shoppingCart);
		games = shoppingCart.getGames();

		result = new ModelAndView("shoppingCart/display");
		result.addObject("games", games);
		result.addObject("requestURI", "shoppingCart/customer/display.do");

		return result;
	}

	// Add games -------------------------------------------------------------------
	@RequestMapping(value = "/addGame", method = RequestMethod.GET)
	public ModelAndView addGame(@RequestParam final int gameId) {
		ModelAndView result;
		Game game;

		game = this.gameService.findOne(gameId);
		this.shoppingCartService.addGameToShoppingCart(game);

		result = new ModelAndView("game/display");
		result.addObject("game", game);

		return result;
	}

	// Add games -------------------------------------------------------------------
	@RequestMapping(value = "/removeGame", method = RequestMethod.GET)
	public ModelAndView removeGame(@RequestParam final int gameId) {
		ModelAndView result;
		Game game;

		game = this.gameService.findOne(gameId);
		this.shoppingCartService.removeGameToShoppingCart(game);

		result = new ModelAndView("game/display");
		result.addObject("game", game);

		return result;
	}

}
