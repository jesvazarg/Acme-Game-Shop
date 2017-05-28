
package controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.GameService;
import services.SenseService;
import controllers.AbstractController;
import domain.Customer;
import domain.Game;
import domain.Sense;

@Controller
@RequestMapping("/sense/customer")
public class SenseCustomerController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private CustomerService	customerService;

	@Autowired
	private SenseService	senseService;

	@Autowired
	private GameService		gameService;


	// Constructors -----------------------------------------------------------
	public SenseCustomerController() {
		super();
	}

	// Like ----------------------------------------------------------------
	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public ModelAndView like(@RequestParam final int gameId) {
		ModelAndView result;
		final Sense sense;
		boolean exist = false;
		final Game game = this.gameService.findOne(gameId);
		final Customer customer = this.customerService.findByPrincipal();
		Assert.isTrue(this.customerService.edadCustomer(customer) >= game.getAge());

		try {
			for (final Sense t : this.senseService.findAll())
				if (t.getCustomer().equals(this.customerService.findByPrincipal()) && t.getGame().equals(game)) {
					this.senseService.change(t, true);
					exist = true;
					break;
				}
			if (exist == false) {
				sense = this.senseService.createLike(game);
				this.senseService.save(sense);
			}
			result = new ModelAndView("redirect:/game/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/game/list.do");
		}

		return result;
	}
	// Dislike ----------------------------------------------------------------
	@RequestMapping(value = "/dislike", method = RequestMethod.GET)
	public ModelAndView dislike(@RequestParam final int gameId) {
		ModelAndView result;
		final Sense sense;
		boolean exist = false;
		final Game game = this.gameService.findOne(gameId);
		final Customer customer = this.customerService.findByPrincipal();
		Assert.isTrue(this.customerService.edadCustomer(customer) >= game.getAge());

		try {
			for (final Sense t : this.senseService.findAll())
				if (t.getCustomer().equals(this.customerService.findByPrincipal()) && t.getGame().equals(game)) {
					this.senseService.change(t, false);
					exist = true;
					break;
				}
			if (exist == false) {
				sense = this.senseService.createDislike(game);
				this.senseService.save(sense);
			}
			result = new ModelAndView("redirect:/game/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/game/list.do");
		}

		return result;
	}
}
