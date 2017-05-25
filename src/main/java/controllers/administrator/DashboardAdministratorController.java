
package controllers.administrator;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CategoryService;
import services.CustomerService;
import services.DeveloperService;
import services.DiscountService;
import services.GameService;
import services.ReviewService;
import controllers.AbstractController;
import domain.Category;
import domain.Customer;
import domain.Developer;
import domain.Game;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services -----------------------------------------------------------
	@Autowired
	private GameService			gameService;

	@Autowired
	private DeveloperService	developerService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private ReviewService		reviewService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private DiscountService		discountService;


	// Constructor --------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Dashboard ----------------------------------------------------------
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		//Level C
		Collection<Game> c1_1;
		Collection<Game> c1_2;
		Collection<Customer> c2;
		Collection<Developer> c3_1;
		Collection<Developer> c3_2;
		Collection<Game> c4;
		Collection<Game> c5_1;
		Collection<Game> c5_2;
		List<Game> c6;
		Collection<Developer> c7;
		Double c8;
		List<Category> c9;
		Object[] c10_1;

		//Level B
		List<Game> b1;
		Collection<Developer> b2_1;
		Collection<Developer> b2_2;
		Object[] b3;

		//Level A
		Double[] a1;
		Object[] a2;
		Object[] a3;

		//Level C
		c1_1 = this.gameService.gameMoreLikes();
		c1_2 = this.gameService.gameLessLikes();
		c2 = this.customerService.findCustomersWithMoreComments();
		c3_1 = this.developerService.developerMoreSells();
		c3_2 = this.developerService.developerLessSells();
		c4 = this.gameService.gamesMoreThatAVG();
		c5_1 = this.gameService.findBestSellerGames();
		c5_2 = this.gameService.findWorstSellerGames();
		c6 = this.gameService.findGameBestAndWorstScoreComments();
		c7 = this.developerService.developersWithBestSellersQuantity();
		c8 = this.developerService.avgDeveloperPerSellGames();
		c9 = this.categoryService.findCategoryOrderBySellsNumber();
		c10_1 = this.gameService.ratioLikeDislikePerGame();

		//Level B
		b1 = this.gameService.findGameBestAndWorstScoreReviews();
		b2_1 = this.developerService.developerWithGameBetterReview();
		b2_2 = this.developerService.developerWithGameWorstReview();
		b3 = this.reviewService.MaxAvgMinReviewsPerCritic();

		//Level A
		a1 = this.actorService.minAvgMaxMessagesSent();
		a2 = this.actorService.minAvgMaxMessagesReceived();
		a3 = this.discountService.MaxAvgMinPercentagePerDiscount();

		result = new ModelAndView("administrator/dashboard");
		//Level C
		result.addObject("c1_1", c1_1);
		result.addObject("c1_2", c1_2);
		result.addObject("c2", c2);
		result.addObject("c3_1", c3_1);
		result.addObject("c3_2", c3_2);
		result.addObject("c4", c4);
		result.addObject("c5_1", c5_1);
		result.addObject("c5_2", c5_2);
		result.addObject("c6", c6);
		result.addObject("c7", c7);
		result.addObject("c8", c8);
		result.addObject("c9", c9);
		result.addObject("c10_1", c10_1);

		//Level B
		result.addObject("b1", b1);
		result.addObject("b2_1", b2_1);
		result.addObject("b2_2", b2_2);
		result.addObject("b3", b3);

		//Level A
		result.addObject("a1", a1);
		result.addObject("a2", a2);
		result.addObject("a3", a3);

		return result;
	}

}
