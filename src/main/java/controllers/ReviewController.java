
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ReviewService;
import domain.Actor;
import domain.Review;

@Controller
@RequestMapping("/review")
public class ReviewController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private ReviewService	reviewService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ReviewController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView list(final int reviewId) {
		ModelAndView result;
		Review review;
		Actor actor;
		Boolean isCritic = false;

		actor = this.actorService.findByPrincipal();
		review = this.reviewService.findOne(reviewId);

		if (review.getCritic().equals(actor))
			isCritic = true;

		result = new ModelAndView("review/display");
		result.addObject("review", review);
		result.addObject("isCritic", isCritic);
		result.addObject("requestURI", "review/display.do");

		return result;
	}

}
