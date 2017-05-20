
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private CategoryService	categoryService;


	// Constructors -----------------------------------------------------------

	public CategoryAdministratorController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/list.do");

		return result;
	}

	// Create, edit and delete ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Category category;

		category = this.categoryService.create();
		result = this.createModelAndView(category);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);
		result = this.editModelAndView(category);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@Valid final Category category, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(category);
		else
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				if (oops.getCause().getCause().getMessage().contains("Duplicate"))
					result = this.createModelAndView(category, "category.commit.error.duplicate");
				else
					result = this.createModelAndView(category, "category.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(category);
		else
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				if (oops.getCause().getCause().getMessage().contains("Duplicate"))
					result = this.editModelAndView(category, "category.commit.error.duplicate");
				else
					result = this.editModelAndView(category, "category.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);
		this.categoryService.delete(category);
		result = new ModelAndView("redirect:list.do");

		return result;
	}
	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createModelAndView(final Category category) {
		ModelAndView result;

		result = this.createModelAndView(category, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Category category, final String message) {
		ModelAndView result;

		result = new ModelAndView("category/create");
		result.addObject("category", category);
		result.addObject("requestURI", "category/administrator/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editModelAndView(final Category category) {
		ModelAndView result;

		result = this.editModelAndView(category, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Category category, final String message) {
		ModelAndView result;

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("requestURI", "category/administrator/edit.do");
		result.addObject("message", message);

		return result;
	}

}
