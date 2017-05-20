
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

import services.DiscountService;
import controllers.AbstractController;
import domain.Discount;

@Controller
@RequestMapping("/discount/administrator")
public class DiscountAdministratorController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private DiscountService	discountService;


	// Constructors -----------------------------------------------------------

	public DiscountAdministratorController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Discount> discounts;

		discounts = this.discountService.findAll();

		result = new ModelAndView("discount/list");
		result.addObject("discounts", discounts);
		result.addObject("requestURI", "discount/administrator/list.do");

		return result;
	}

	// Create, edit and delete ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Discount discount;

		discount = this.discountService.create();
		result = this.createModelAndView(discount);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int discountId) {
		ModelAndView result;
		Discount discount;

		discount = this.discountService.findOne(discountId);
		result = this.editModelAndView(discount);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@Valid final Discount discount, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(discount);
		else
			try {
				this.discountService.save(discount);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createModelAndView(discount, "discount.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Discount discount, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(discount);
		else
			try {
				this.discountService.save(discount);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.editModelAndView(discount, "discount.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int discountId) {
		ModelAndView result;
		Discount discount;

		discount = this.discountService.findOne(discountId);
		this.discountService.delete(discount);
		result = new ModelAndView("redirect:list.do");

		return result;
	}
	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createModelAndView(final Discount discount) {
		ModelAndView result;

		result = this.createModelAndView(discount, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Discount discount, final String message) {
		ModelAndView result;

		result = new ModelAndView("discount/create");
		result.addObject("discount", discount);
		result.addObject("requestURI", "discount/administrator/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editModelAndView(final Discount discount) {
		ModelAndView result;

		result = this.editModelAndView(discount, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Discount discount, final String message) {
		ModelAndView result;

		result = new ModelAndView("discount/edit");
		result.addObject("discount", discount);
		result.addObject("requestURI", "discount/administrator/edit.do");
		result.addObject("message", message);

		return result;
	}

}
