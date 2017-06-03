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

import services.DeveloperService;
import domain.Developer;
import forms.CreateDeveloperForm;

@Controller
@RequestMapping("/developer")
public class DeveloperController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private DeveloperService	developerService;


	// Constructors -----------------------------------------------------------

	public DeveloperController() {
		super();
	}

	// Creation ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreateDeveloperForm createDeveloperForm;

		createDeveloperForm = new CreateDeveloperForm();
		result = this.createEditModelAndView(createDeveloperForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final CreateDeveloperForm createDeveloperForm, final BindingResult binding) {

		ModelAndView result;
		Developer developer;

		if (binding.hasErrors())
			result = this.createEditModelAndView(createDeveloperForm);
		else
			try {
				developer = this.developerService.reconstructProfile(createDeveloperForm, "create");
				this.developerService.save(developer);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				if (!createDeveloperForm.getPassword().equals(createDeveloperForm.getConfirmPassword()))
					result = this.createEditModelAndView(createDeveloperForm, "developer.commit.error.password");
				else if (createDeveloperForm.getIsAgree().equals(false))
					result = this.createEditModelAndView(createDeveloperForm, "developer.commit.error.isAgree");
				else if ((oops.getCause().getCause().getMessage() != null) && (oops.getCause().getCause().getMessage().contains("Duplicate")))
					result = this.createEditModelAndView(createDeveloperForm, "developer.commit.error.duplicate");
				else
					result = this.createEditModelAndView(createDeveloperForm, "developer.commit.error");
			}
		return result;
	}
	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final CreateDeveloperForm createDeveloperForm;
		Developer developer;

		developer = this.developerService.findByPrincipal();
		createDeveloperForm = this.developerService.constructProfile(developer);
		result = this.editionEditModelAndView(createDeveloperForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final CreateDeveloperForm createDeveloperForm, final BindingResult binding) {

		ModelAndView result;
		Developer developer;

		if (binding.hasErrors())
			result = this.editionEditModelAndView(createDeveloperForm);
		else
			try {
				developer = this.developerService.reconstructProfile(createDeveloperForm, "edit");
				this.developerService.save(developer);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				if (!createDeveloperForm.getPassword().equals(createDeveloperForm.getConfirmPassword()))
					result = this.editionEditModelAndView(createDeveloperForm, "developer.commit.error.password");
				else if ((oops.getCause().getCause().getMessage() != null) && (oops.getCause().getCause().getMessage().contains("Duplicate")))
					result = this.editionEditModelAndView(createDeveloperForm, "developer.commit.error.duplicate");
				else
					result = this.editionEditModelAndView(createDeveloperForm, "developer.commit.error");

			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final CreateDeveloperForm createDeveloperForm) {
		ModelAndView result;

		result = this.createEditModelAndView(createDeveloperForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateDeveloperForm createDeveloperForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("developer/create");
		result.addObject("createDeveloperForm", createDeveloperForm);
		result.addObject("requestURI", "developer/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateDeveloperForm createDeveloperForm) {
		ModelAndView result;

		result = this.editionEditModelAndView(createDeveloperForm, null);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateDeveloperForm createDeveloperForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("developer/edit");
		result.addObject("createDeveloperForm", createDeveloperForm);
		result.addObject("requestURI", "developer/edit.do");
		result.addObject("message", message);

		return result;
	}

}
