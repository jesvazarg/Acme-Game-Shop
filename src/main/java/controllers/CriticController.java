
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CriticService;
import domain.Critic;
import forms.CriticForm;

@Controller
@RequestMapping("/critic")
public class CriticController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private CriticService	criticService;


	// Constructors -----------------------------------------------------------

	public CriticController() {
		super();
	}

	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		CriticForm criticForm;
		Critic critic;

		critic = this.criticService.findByPrincipal();
		criticForm = this.criticService.constructProfile(critic);
		result = this.editModelAndView(criticForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final CriticForm criticForm, final BindingResult binding) {

		ModelAndView result;
		Critic critic;

		if (binding.hasErrors())
			result = this.editModelAndView(criticForm);
		else
			try {
				critic = this.criticService.reconstructProfile(criticForm, "edit");
				this.criticService.save(critic);
				result = new ModelAndView("redirect:/profile/myProfile.do");

			} catch (final Throwable oops) {
				if (oops.getCause().getCause().getMessage().contains("Duplicate"))
					result = this.editModelAndView(criticForm, "critic.commit.error.duplicate");
				else if (!criticForm.getPassword().equals(criticForm.getConfirmPassword()))
					result = this.editModelAndView(criticForm, "critic.commit.error.password");
				else
					result = this.editModelAndView(criticForm, "critic.commit.error");

			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView editModelAndView(final CriticForm criticForm) {
		ModelAndView result;

		result = this.editModelAndView(criticForm, null);

		return result;
	}

	protected ModelAndView editModelAndView(final CriticForm criticForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("critic/edit");
		result.addObject("criticForm", criticForm);
		result.addObject("requestURI", "critic/edit.do");
		result.addObject("message", message);

		return result;
	}

}
