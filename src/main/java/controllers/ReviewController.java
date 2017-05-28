
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CustomerService;
import services.ReviewService;
import domain.Actor;
import domain.Critic;
import domain.Customer;
import domain.Game;
import domain.Review;

@Controller
@RequestMapping("/review")
public class ReviewController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private ReviewService	reviewService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------

	public ReviewController() {
		super();
	}

	// Display ---------------------------------------------------------------
	@RequestMapping(value = "/displayNotAuth", method = RequestMethod.GET)
	public ModelAndView displayNotAuth(final int reviewId) {
		ModelAndView result;
		Review review;
		Game game;
		final Boolean isMine = false;

		review = this.reviewService.findOne(reviewId);
		game = review.getGame();
		Assert.isTrue(game.getAge() <= 13);

		result = new ModelAndView("review/display");
		result.addObject("review", review);
		result.addObject("isMine", isMine);
		result.addObject("requestURI", "review/displayNotAuth.do");

		return result;
	}

	// Display ---------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int reviewId) {
		ModelAndView result;
		Review review;
		Actor principal;
		Game game;
		Critic critic;
		Boolean isMine = false;

		review = this.reviewService.findOne(reviewId);
		game = review.getGame();
		principal = this.actorService.findByPrincipal();
		if (this.actorService.checkAuthority(principal, "CRITIC")) {
			critic = (Critic) principal;
			if (critic.getId() == review.getCritic().getId())
				isMine = true;
		}

		if (this.actorService.checkAuthority(principal, "CUSTOMER"))
			Assert.isTrue(this.customerService.edadCustomer((Customer) principal) >= game.getAge());

		result = new ModelAndView("review/display");
		result.addObject("review", review);
		result.addObject("isMine", isMine);
		result.addObject("requestURI", "review/display.do");

		return result;
	}

}
