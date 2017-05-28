
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CreditCardService;
import services.CustomerService;
import domain.CreditCard;
import domain.Customer;
import forms.CreateCreditCardForm;

@Controller
@RequestMapping("/creditCard")
public class CreditCardActorController extends AbstractController {

	// Service ---------------------------------------------------------------		
	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------
	public CreditCardActorController() {
		super();
	}

	// Display ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {

		ModelAndView result;
		CreditCard creditCard;

		creditCard = this.actorService.findByPrincipal().getCreditCard();

		result = new ModelAndView("creditCard/display");
		result.addObject("creditCard", creditCard);
		result.addObject("requestURI", "creditCard/display");

		return result;

	}
	// Creation ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreateCreditCardForm createCreditCardForm;

		createCreditCardForm = new CreateCreditCardForm();
		result = this.createEditModelAndView(createCreditCardForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final CreateCreditCardForm createCreditCardForm, final BindingResult binding) {

		ModelAndView result;
		CreditCard creditCard;

		if (binding.hasErrors())
			result = this.createEditModelAndView(createCreditCardForm);
		else
			try {
				creditCard = this.creditCardService.reconstructCreditCard(createCreditCardForm, "create");
				this.creditCardService.saveRegister(creditCard);
				result = new ModelAndView("redirect:/creditCard/display.do");

			} catch (final Throwable oops) {
				final String brandname = createCreditCardForm.getBrandName().toUpperCase();
				if (!brandname.equals("VISA") && !brandname.equals("MASTERCARD") && !brandname.equals("DISCOVER") && !brandname.equals("DINNERS") && !brandname.equals("AMEX"))
					result = this.createEditModelAndView(createCreditCardForm, "creditCard.commit.brandname");
				else if (!this.creditCardService.checkCreditCardBooleanForm(createCreditCardForm))
					result = this.createEditModelAndView(createCreditCardForm, "creditCard.commit.expirationDate");
				else
					result = this.createEditModelAndView(createCreditCardForm, "creditCard.commit.error");

			}
		return result;
	}

	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		CreateCreditCardForm createCreditCardForm;
		CreditCard creditCard;

		creditCard = this.actorService.findByPrincipal().getCreditCard();
		Assert.notNull(creditCard);
		createCreditCardForm = this.creditCardService.constructCreditCard(creditCard);
		result = this.editionEditModelAndView(createCreditCardForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final CreateCreditCardForm createCreditCardForm, final BindingResult binding) {

		ModelAndView result;
		CreditCard creditCard;

		if (binding.hasErrors())
			result = this.editionEditModelAndView(createCreditCardForm);
		else
			try {
				creditCard = this.creditCardService.reconstructCreditCard(createCreditCardForm, "edit");
				this.creditCardService.save(creditCard);
				result = new ModelAndView("redirect:/creditCard/display.do");

			} catch (final Throwable oops) {
				final String brandname = createCreditCardForm.getBrandName().toUpperCase();
				if (!brandname.equals("VISA") && !brandname.equals("MASTERCARD") && !brandname.equals("DISCOVER") && !brandname.equals("DINNERS") && !brandname.equals("AMEX"))
					result = this.editionEditModelAndView(createCreditCardForm, "creditCard.commit.brandname");
				else if (!this.creditCardService.checkCreditCardBooleanForm(createCreditCardForm))
					result = this.editionEditModelAndView(createCreditCardForm, "creditCard.commit.expirationDate");
				else
					result = this.editionEditModelAndView(createCreditCardForm, "creditCard.commit.error");

			}
		return result;
	}
	// Delete ------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {

		ModelAndView result;
		Customer principal;
		CreditCard creditCard;

		principal = this.customerService.findByPrincipal();
		creditCard = principal.getCreditCard();
		this.creditCardService.delete(creditCard);
		result = new ModelAndView("redirect:/profile/myProfile.do");

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final CreateCreditCardForm createCreditCardForm) {
		ModelAndView result;

		result = this.createEditModelAndView(createCreditCardForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateCreditCardForm createCreditCardForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("creditCard/create");
		result.addObject("createCreditCardForm", createCreditCardForm);
		result.addObject("requestURI", "creditCard/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateCreditCardForm createCreditCardForm) {
		ModelAndView result;

		result = this.editionEditModelAndView(createCreditCardForm, null);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateCreditCardForm createCreditCardForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("creditCard/edit");
		result.addObject("createCreditCardForm", createCreditCardForm);
		result.addObject("requestURI", "creditCard/edit.do");
		result.addObject("message", message);

		return result;
	}
}
