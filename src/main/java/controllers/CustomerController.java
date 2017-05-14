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
				System.out.println(oops);
				result = this.createEditModelAndView(createCustomerForm, "customer.commit.error");

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

}
