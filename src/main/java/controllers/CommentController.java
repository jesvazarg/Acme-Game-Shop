
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import domain.Comment;
import domain.Game;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private CommentService	commentService;


	// Constructors -----------------------------------------------------------
	public CommentController() {
		super();
	}

	// Display ---------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;
		Game game;

		comment = this.commentService.findOne(commentId);
		game = comment.getGame();

		result = new ModelAndView("comment/display");
		result.addObject("comment", comment);
		result.addObject("game", game);
		return result;
	}

}
