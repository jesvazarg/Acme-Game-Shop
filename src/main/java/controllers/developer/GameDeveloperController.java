
package controllers.developer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CategoryService;
import services.GameService;
import domain.Category;
import domain.Game;

@Controller
@RequestMapping("/game/developer")
public class GameDeveloperController {

	// Services ---------------------------------------------------------------

	@Autowired
	private GameService		gameService;

	@Autowired
	private CategoryService	categoryService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public GameDeveloperController() {
		super();
	}

	// Create, Edit and Delete ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Game game;

		game = this.gameService.create();

		result = this.createEditModelAndView(game);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int gameId) {
		ModelAndView result;
		Game game;

		game = this.gameService.findOne(gameId);

		result = this.createEditModelAndView(game);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Game game, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(game);
		else
			try {
				if (game.getId() != 0) {
					this.categoryService.select(game.getCategories(), game);
					game = this.gameService.save(game);
				} else {
					game = this.gameService.save(game);
					this.categoryService.select(game.getCategories(), game);
				}
				result = new ModelAndView("redirect:../../game/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(game, "game.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@Valid final int gameId) {
		ModelAndView result;
		Game game;

		game = this.gameService.findOne(gameId);
		this.gameService.delete(game);
		result = new ModelAndView("redirect:../../game/list.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Game game) {
		ModelAndView result;

		result = this.createEditModelAndView(game, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Game game, final String message) {
		ModelAndView result;

		Collection<Category> categories;
		categories = this.categoryService.findAll();

		result = new ModelAndView("game/edit");
		result.addObject("game", game);
		result.addObject("categories", categories);
		result.addObject("requestURI", "game/developer/edit.do");
		result.addObject("message", message);

		return result;
	}

	// Ancillary methods Game ----------------------------------------------

	protected ModelAndView createEditModelAndViewGame(final Game game) {
		ModelAndView result;

		result = this.createEditModelAndViewGame(game, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewGame(final Game game, final String message) {
		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.findAll();

		result = new ModelAndView("game/edit");
		result.addObject("game", game);
		result.addObject("editProperties", true);
		result.addObject("requestURI", "game/developer/editCategories.do?gameId=" + game.getId());
		result.addObject("categories", categories);
		result.addObject("message", message);

		return result;
	}

}
