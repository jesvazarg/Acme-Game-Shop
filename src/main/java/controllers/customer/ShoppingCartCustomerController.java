
package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.CustomerService;
import services.GameService;
import services.ShoppingCartService;
import controllers.AbstractController;
import domain.Customer;
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

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------------------
	public ShoppingCartCustomerController() {
		super();
	}

	// Display ---------------------------------------------------------------	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		ShoppingCart shoppingCart;

		shoppingCart = this.customerService.findByPrincipal().getShoppingCart();

		result = this.createEditModelAndView(shoppingCart);

		return result;
	}

	// Buy games ---------------------------------------------------------------

	@RequestMapping(value = "/buy", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ShoppingCart shoppingCart, final BindingResult binding) {
		ModelAndView result;
		final Customer customer = this.customerService.findByPrincipal();

		if (binding.hasErrors())
			result = this.createEditModelAndView(shoppingCart);
		else
			try {
				this.shoppingCartService.buyGamesInShoppingCart(shoppingCart);
				result = new ModelAndView("redirect:/shoppingCart/customer/display.do");
			} catch (final Throwable oops) {
				/* Comprobacion si tiene tarjeta de credito valida */
				try {
					if (!(this.creditCardService.checkCreditCardBoolean(customer.getCreditCard())))
						result = this.createEditModelAndView(shoppingCart, "shoppingCart.commit.error.creditCard");
					else
						result = this.createEditModelAndView(shoppingCart, "shoppingCart.commit.error");
				} catch (final Throwable juu) {
					result = this.createEditModelAndView(shoppingCart, "shoppingCart.commit.error.creditCard");
				}
			}

		return result;
	}

	// Add games -------------------------------------------------------------------
	@RequestMapping(value = "/addGame", method = RequestMethod.GET)
	public ModelAndView addGame(@RequestParam final int gameId) {
		ModelAndView result;
		Game game;

		game = this.gameService.findOne(gameId);
		this.shoppingCartService.addGameToShoppingCart(game);

		result = new ModelAndView("redirect:/game/display.do?gameId=" + gameId);

		return result;
	}

	// Remove games -------------------------------------------------------------------
	@RequestMapping(value = "/removeGame", method = RequestMethod.GET)
	public ModelAndView removeGame(@RequestParam final int gameId) {
		ModelAndView result;
		Game game;

		game = this.gameService.findOne(gameId);
		this.shoppingCartService.removeGameToShoppingCart(game);

		result = new ModelAndView("redirect:/shoppingCart/customer/display.do");

		return result;
	}

	// Ancillary methods Category ----------------------------------------------

	protected ModelAndView createEditModelAndView(final ShoppingCart shoppingCart) {
		ModelAndView result;

		result = this.createEditModelAndView(shoppingCart, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ShoppingCart shoppingCart, final String message) {
		ModelAndView result;
		Collection<Game> games;
		games = shoppingCart.getGames();

		result = new ModelAndView("shoppingCart/display");
		result.addObject("shoppingCart", shoppingCart);
		result.addObject("games", games);
		result.addObject("requestURI", "shoppingCart/customer/display.do");
		result.addObject("message", message);

		return result;
	}

}
