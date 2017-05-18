
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.GameService;
import services.ReviewService;
import services.SenseService;
import domain.Actor;
import domain.Game;
import domain.Review;
import domain.Sense;

@Controller
@RequestMapping("/game")
public class GameController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private GameService		gameService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ReviewService	reviewService;

	@Autowired
	private SenseService	senseService;


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

		games = this.gameService.findByAge();

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

		final Actor actor = this.actorService.findByPrincipal();

		game = this.gameService.findOne(gameId);
		reviews = this.reviewService.findAllPublishedReview(gameId);

		result = new ModelAndView("game/display");
		result.addObject("game", game);
		result.addObject("reviews", reviews);
		result.addObject("requestURI", "game/list.do");

		return result;
	}

}
