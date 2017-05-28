
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CategoryService;
import services.CustomerService;
import services.DeveloperService;
import services.GameService;
import services.ReviewService;
import services.SenseService;
import services.ShoppingCartService;
import domain.Actor;
import domain.Category;
import domain.Customer;
import domain.Developer;
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
	private CategoryService		categoryService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private DeveloperService	developerService;

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
		Collection<Object[]> auxGames;
		Collection<Game> games;

		games = this.gameService.findAllUnderThirteen();
		auxGames = this.gameService.avgGreaterthanEight(games);

		result = new ModelAndView("game/list");
		result.addObject("games", auxGames);

		return result;
	}

	// List ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Game> games;
		Collection<Sense> senses;
		Collection<Category> categories;
		Collection<Object[]> auxGames;

		final Actor actor = this.actorService.findByPrincipal();
		senses = this.senseService.findAll();

		categories = this.categoryService.findAll();

		final Customer customer = this.customerService.findByUserAccount(actor.getUserAccount());
		if (customer != null)
			games = this.gameService.findByAge();

		else
			games = this.gameService.findAll();

		auxGames = this.gameService.avgGreaterthanEight(games);

		result = new ModelAndView("game/list");
		result.addObject("games", auxGames);
		result.addObject("principal", actor);
		result.addObject("senseList", senses);
		result.addObject("categories", categories);

		return result;
	}

	// ListMyGames ----------------------------------------------------------------
	@RequestMapping(value = "/listMyGames", method = RequestMethod.GET)
	public ModelAndView listMyGames() {
		ModelAndView result;
		Collection<Game> games;
		Collection<Sense> senses;
		Collection<Category> categories;
		Collection<Object[]> auxGames;

		final Actor actor = this.actorService.findByPrincipal();
		Developer developer;
		developer = this.developerService.findByUserAccountId(actor.getUserAccount().getId());
		senses = this.senseService.findAll();

		categories = this.categoryService.findAll();

		final Customer customer = this.customerService.findByUserAccount(actor.getUserAccount());
		if (customer != null)
			games = this.gameService.findByAge();
		else
			games = this.gameService.findByDeveloperId(developer.getId());

		auxGames = this.gameService.avgGreaterthanEight(games);

		result = new ModelAndView("game/list");
		result.addObject("games", auxGames);
		result.addObject("principal", actor);
		result.addObject("senseList", senses);
		result.addObject("categories", categories);

		return result;
	}

	// DisplayNotAuth ----------------------------------------------------------------
	@RequestMapping(value = "/displayNotAuth", method = RequestMethod.GET)
	public ModelAndView displayNotAuth(@RequestParam final int gameId) {
		ModelAndView result;
		Game game;
		Collection<Review> reviews;
		final Boolean canAddToShoppingcart = false;
		final Boolean isOwner = false;

		game = this.gameService.findOne(gameId);

		reviews = this.reviewService.findAllPublishedReview(gameId);
		if (game.getAge() > 13)
			result = new ModelAndView("redirect:/game/listNotAuth.do");
		else {
			result = new ModelAndView("game/display");
			result.addObject("game", game);
			result.addObject("canAddToShoppingcart", canAddToShoppingcart);
			result.addObject("reviews", reviews);
			result.addObject("isOwner", isOwner);
			result.addObject("requestURI", "game/displayNotAuth.do");
		}

		return result;
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int gameId) {
		ModelAndView result;
		Game game;
		Collection<Review> reviews;
		Boolean canAddToShoppingcart;
		Boolean isOwner = false;

		final Actor actor = this.actorService.findByPrincipal();
		final Customer customer = this.customerService.findByUserAccount(actor.getUserAccount());
		final Developer developer = this.developerService.findByUserAccountId(actor.getUserAccount().getId());
		game = this.gameService.findOne(gameId);

		if (developer != null && game.getDeveloper().equals(developer))
			isOwner = true;

		reviews = this.reviewService.findAllPublishedReview(gameId);

		if (customer != null)
			canAddToShoppingcart = this.shoppingCartService.canAddToShoppingCart(game);
		else
			canAddToShoppingcart = false;

		if (this.actorService.checkAuthority(actor, "CUSTOMER") && (this.customerService.edadCustomer((Customer) actor) < game.getAge()))
			result = new ModelAndView("redirect:/game/list.do");
		else {
			result = new ModelAndView("game/display");
			result.addObject("game", game);
			result.addObject("canAddToShoppingcart", canAddToShoppingcart);
			result.addObject("reviews", reviews);
			result.addObject("isOwner", isOwner);
			result.addObject("actor", actor);
			result.addObject("requestURI", "game/display.do");
		}
		return result;
	}

	// Search ----------------------------------------------------------------
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchButton(@RequestParam final String key) {

		ModelAndView result;
		Collection<Game> games;
		Collection<Sense> senses;
		Collection<Category> categories;
		Collection<Object[]> auxGames;

		final Actor actor = this.actorService.findByPrincipal();
		senses = this.senseService.findAll();

		categories = this.categoryService.findAll();

		final Customer customer = this.customerService.findByUserAccount(actor.getUserAccount());
		if (customer != null)
			games = this.gameService.findByKeyWithAge(key);
		else
			games = this.gameService.findByKey(key);

		auxGames = this.gameService.avgGreaterthanEight(games);

		result = new ModelAndView("game/list");
		result.addObject("games", auxGames);
		result.addObject("principal", actor);
		result.addObject("senseList", senses);
		result.addObject("categories", categories);

		return result;
	}

	// Search ----------------------------------------------------------------
	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public ModelAndView filterButton(@RequestParam(required = false) final String key, @RequestParam(required = false) final Integer minPrice, @RequestParam(required = false) final Integer maxPrice) {

		ModelAndView result;
		Collection<Game> games;
		Collection<Sense> senses;
		Collection<Category> categories;
		Collection<Object[]> auxGames;

		Double minPrice2 = 0.0;
		Double maxPrice2 = 0.0;
		final Actor actor = this.actorService.findByPrincipal();
		senses = this.senseService.findAll();

		categories = this.categoryService.findAll();

		if (minPrice != null)
			minPrice2 = (double) minPrice;

		if (maxPrice != null)
			maxPrice2 = (double) maxPrice;
		final Customer customer = this.customerService.findByUserAccount(actor.getUserAccount());
		if (customer != null)
			games = this.gameService.findByCategoryOrPriceWithAge(key, minPrice2, maxPrice2);
		else
			games = this.gameService.findByCategoryOrPrice(key, minPrice2, maxPrice2);

		auxGames = this.gameService.avgGreaterthanEight(games);

		result = new ModelAndView("game/list");
		result.addObject("games", auxGames);
		result.addObject("principal", actor);
		result.addObject("senseList", senses);
		result.addObject("categories", categories);

		return result;
	}

}
