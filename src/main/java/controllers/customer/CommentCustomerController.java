
package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CustomerService;
import services.GameService;
import controllers.AbstractController;
import domain.Comment;
import domain.Game;

@Controller
@RequestMapping("/comment/customer")
public class CommentCustomerController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private CustomerService	customerService;

	@Autowired
	private CommentService	commentService;

	@Autowired
	private GameService		gameService;


	// Constructors -----------------------------------------------------------
	public CommentCustomerController() {
		super();
	}

	// Create -------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int gameId) {
		ModelAndView result;
		Game game;
		Comment comment;

		game = this.gameService.findOne(gameId);
		comment = this.commentService.create(game);

		result = this.createEditModelAndView(comment);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Comment comment, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			if (comment.getGame() == null)
				result = this.createEditModelAndView(comment, "comment.commit.error.not.game");
			else
				result = this.createEditModelAndView(comment);
		else
			try {
				this.commentService.save(comment);
				result = new ModelAndView("redirect:/game/display.do?gameId=" + comment.getGame().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, "comment.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@Valid final int commentId) {
		ModelAndView result;
		final Comment comment;
		final Game game;

		comment = this.commentService.findOne(commentId);
		game = comment.getGame();

		this.commentService.delete(comment);
		result = new ModelAndView("redirect:/game/display.do?gameId=" + game.getId());

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {
		ModelAndView result;

		result = new ModelAndView("comment/create");
		result.addObject("comment", comment);
		result.addObject("message", message);

		return result;
	}

}
