
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.CustomerService;
import domain.Actor;
import domain.Comment;
import domain.Customer;
import domain.Game;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private CommentService	commentService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------
	public CommentController() {
		super();
	}

	// Display ---------------------------------------------------------------
	@RequestMapping(value = "/displayAuth", method = RequestMethod.GET)
	public ModelAndView displayAuth(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;
		Game game;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		comment = this.commentService.findOne(commentId);
		game = comment.getGame();

		if (this.actorService.checkAuthority(actor, "CUSTOMER"))
			Assert.isTrue(this.customerService.edadCustomer((Customer) actor) >= game.getAge());

		result = new ModelAndView("comment/display");
		result.addObject("comment", comment);
		result.addObject("game", game);
		return result;
	}

	@RequestMapping(value = "/displayNoAuth", method = RequestMethod.GET)
	public ModelAndView displayNoAuth(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;
		Game game;

		comment = this.commentService.findOne(commentId);
		game = comment.getGame();
		Assert.isTrue(game.getAge() <= 13);

		result = new ModelAndView("comment/display");
		result.addObject("comment", comment);
		result.addObject("game", game);
		return result;
	}

}
