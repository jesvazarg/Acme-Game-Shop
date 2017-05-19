
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CustomerService;
import services.GameService;
import services.ReviewService;
import services.SenseService;
import services.ShoppingCartService;
import domain.Actor;
import domain.Customer;
import domain.Game;
import domain.Review;
import domain.Sense;

@Controller
@RequestMapping("/game")
public class GameController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private GameService			gameService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ReviewService		reviewService;

	@Autowired
	private SenseService		senseService;

	@Autowired
	private ShoppingCartService	shoppingCartService;


	// Constructors -----------------------------------------------------------

	public GameController() {
		super();
	}

	// ListNotAuth ----------------------------------------------------------------
	@RequestMapping(value = "/listNotAuth", method = RequestMethod.GET)
	public ModelAndView listNotAuth() {
		ModelAndView result;
		Collection<Game> games;

		games = this.gameService.findAllUnderThirteen();

		result = new ModelAndView("game/list");
		result.addObject("games", games);

		return result;
	}

	// List ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Game> games;
		Collection<Sense> senses;

		final Actor actor = this.actorService.findByPrincipal();
		senses = this.senseService.findAll();

		final Customer customer = this.customerService.findByUserAccount(actor.getUserAccount());
		if (customer != null)
			games = this.gameService.findByAge();
		else
			games = this.gameService.findAll();

		result = new ModelAndView("game/list");
		result.addObject("games", games);
		result.addObject("principal", actor);
		result.addObject("senseList", senses);

		return result;
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int gameId) {
		ModelAndView result;
		Game game;
		Collection<Review> reviews;
		Boolean canAddToShoppingcart;

		final Actor actor = this.actorService.findByPrincipal();

		game = this.gameService.findOne(gameId);
		reviews = this.reviewService.findAllPublishedReview(gameId);
		canAddToShoppingcart = this.shoppingCartService.canAddToShoppingCart(game);

		result = new ModelAndView("game/display");
		result.addObject("game", game);
		result.addObject("canAddToShoppingcart", canAddToShoppingcart);
		result.addObject("reviews", reviews);
		result.addObject("requestURI", "game/list.do");

		return result;
	}

}
