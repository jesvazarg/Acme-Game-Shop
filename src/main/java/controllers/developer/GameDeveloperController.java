
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
import services.DeveloperService;
import services.GameService;
import domain.Actor;
import domain.Category;
import domain.Developer;
import domain.Game;

@Controller
@RequestMapping("/game/developer")
public class GameDeveloperController {

	// Services ---------------------------------------------------------------

	@Autowired
	private GameService			gameService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private DeveloperService	developerService;


	// Constructors -----------------------------------------------------------

	public GameDeveloperController() {
		super();
	}

	// Create, Edit and Delete ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Game game;
		Actor actor;
		Developer developer;

		actor = this.actorService.findByPrincipal();
		developer = this.developerService.findByUserAccountId(actor.getUserAccount().getId());

		if (developer == null)
			return result = new ModelAndView("redirect:../../welcome/index.do");

		game = this.gameService.create();

		result = this.createEditModelAndView(game);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int gameId) {
		ModelAndView result;
		Game game;
		Actor actor;
		Developer developer;
		game = this.gameService.findOne(gameId);

		actor = this.actorService.findByPrincipal();
		developer = this.developerService.findByUserAccountId(actor.getUserAccount().getId());

		if (developer == null || !game.getDeveloper().equals(developer))
			return result = new ModelAndView("redirect:../../welcome/index.do");

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
		Actor actor;
		Developer developer;
		game = this.gameService.findOne(gameId);

		actor = this.actorService.findByPrincipal();
		developer = this.developerService.findByUserAccountId(actor.getUserAccount().getId());

		if (developer == null || !game.getDeveloper().equals(developer))
			return result = new ModelAndView("redirect:../../welcome/index.do");

		this.gameService.delete(game);
		result = new ModelAndView("redirect:../../game/list.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	//Create
	protected ModelAndView createEditModelAndView(final Game game) {
		ModelAndView result;

		result = this.createEditModelAndView(game, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Game game, final String message) {
		ModelAndView result;

		Collection<Category> categories;
		categories = this.categoryService.findAll();
		if (game.getId() != 0)
			result = new ModelAndView("game/edit");
		else
			result = new ModelAndView("game/create");

		result.addObject("game", game);
		result.addObject("categories", categories);
		result.addObject("requestURI", "game/developer/edit.do");
		result.addObject("message", message);

		return result;
	}

}
