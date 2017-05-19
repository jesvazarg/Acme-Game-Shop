
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.CustomerService;
import services.DiscountService;
import services.GameService;
import services.ShoppingCartService;
import controllers.AbstractController;
import domain.Customer;
import domain.Discount;
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

	@Autowired
	private DiscountService		discountService;


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

	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public ModelAndView searchButton(@RequestParam final String code) {
		ModelAndView result;
		Customer customer;
		ShoppingCart shoppingCart;
		Integer percentage = 0;

		customer = this.customerService.findByPrincipal();
		shoppingCart = customer.getShoppingCart();

		if (customer.getCreditCard() == null)
			result = this.createEditModelAndView(shoppingCart, "shoppingCart.commit.error.notCreditCard");
		else if (!(this.creditCardService.checkCreditCardBoolean(customer.getCreditCard())))
			result = this.createEditModelAndView(shoppingCart, "shoppingCart.commit.error.validCreditCard");
		else if (code != "") {
			Discount discount;
			discount = this.discountService.getDiscountWithCode(code);
			if (discount == null)
				result = this.createEditModelAndView(shoppingCart, "shoppingCart.commit.error.invalidDiscunt");
			else if (discount.getUsed() == true)
				result = this.createEditModelAndView(shoppingCart, "shoppingCart.commit.error.discuntUsed");
			else {
				percentage = discount.getPercentage();
				this.shoppingCartService.buyGamesInShoppingCart(shoppingCart, percentage);
				this.discountService.useDiscount(discount);
				result = new ModelAndView("redirect:/shoppingCart/customer/display.do");
			}
		} else {
			this.shoppingCartService.buyGamesInShoppingCart(shoppingCart, percentage);
			result = new ModelAndView("redirect:/shoppingCart/customer/display.do");
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
		//		String error = null;

		games = shoppingCart.getGames();
		//		if (message.equals("shoppingCart.commit.error.notCreditCard"))
		//			error = "perfil";
		//		else if (message.equals("shoppingCart.commit.error.validCreditCard"))
		//			error = "creditCard";

		result = new ModelAndView("shoppingCart/display");
		result.addObject("shoppingCart", shoppingCart);
		result.addObject("games", games);
		result.addObject("requestURI", "shoppingCart/customer/display.do");
		result.addObject("message", message);

		return result;
	}

}
