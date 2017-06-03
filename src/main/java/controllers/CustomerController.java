/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Customer;
import forms.CreateCustomerForm;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Creation ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreateCustomerForm createCustomerForm;

		createCustomerForm = new CreateCustomerForm();
		result = this.createEditModelAndView(createCustomerForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final CreateCustomerForm createCustomerForm, final BindingResult binding) {

		ModelAndView result;
		Customer customer;

		if (binding.hasErrors())
			result = this.createEditModelAndView(createCustomerForm);
		else
			try {
				customer = this.customerService.reconstructProfile(createCustomerForm, "create");
				this.customerService.saveRegister(customer);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				if (!createCustomerForm.getPassword().equals(createCustomerForm.getConfirmPassword()))
					result = this.createEditModelAndView(createCustomerForm, "customer.commit.error.password");
				else if (createCustomerForm.getIsAgree().equals(false))
					result = this.createEditModelAndView(createCustomerForm, "customer.commit.error.isAgree");
				else if ((oops.getCause().getCause().getMessage() != null) && (oops.getCause().getCause().getMessage().contains("Duplicate")))
					result = this.createEditModelAndView(createCustomerForm, "customer.commit.error.duplicate");
				else
					result = this.createEditModelAndView(createCustomerForm, "customer.commit.error");

			}
		return result;
	}
	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		CreateCustomerForm createCustomerForm;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		createCustomerForm = this.customerService.constructProfile(customer);
		result = this.editionEditModelAndView(createCustomerForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final CreateCustomerForm createCustomerForm, final BindingResult binding) {

		ModelAndView result;
		Customer customer;

		if (binding.hasErrors())
			result = this.editionEditModelAndView(createCustomerForm);
		else
			try {
				customer = this.customerService.reconstructProfile(createCustomerForm, "edit");
				this.customerService.save(customer);
				result = new ModelAndView("redirect:/profile/myProfile.do");

			} catch (final Throwable oops) {
				if (!createCustomerForm.getPassword().equals(createCustomerForm.getConfirmPassword()))
					result = this.editionEditModelAndView(createCustomerForm, "customer.commit.error.password");
				else if ((oops.getCause().getCause().getMessage() != null) && (oops.getCause().getCause().getMessage().contains("Duplicate")))
					result = this.editionEditModelAndView(createCustomerForm, "customer.commit.error.duplicate");
				else
					result = this.editionEditModelAndView(createCustomerForm, "customer.commit.error");

			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final CreateCustomerForm createCustomerForm) {
		ModelAndView result;

		result = this.createEditModelAndView(createCustomerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateCustomerForm createCustomerForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("customer/create");
		result.addObject("createCustomerForm", createCustomerForm);
		result.addObject("requestURI", "customer/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateCustomerForm createCustomerForm) {
		ModelAndView result;

		result = this.editionEditModelAndView(createCustomerForm, null);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateCustomerForm createCustomerForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("customer/edit");
		result.addObject("createCustomerForm", createCustomerForm);
		result.addObject("requestURI", "customer/edit.do");
		result.addObject("message", message);

		return result;
	}

}
