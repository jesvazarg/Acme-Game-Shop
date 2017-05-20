/*
 * ProfileController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import domain.Actor;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private ActorService	actorService;


	// MyProfile ---------------------------------------------------------------		

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ModelAndView displayMyProfile() {
		ModelAndView result;
		Actor actor;
		Boolean isCustomer = false;
		Boolean isDeveloper = false;
		Boolean isCritic = false;
		String account = "";

		actor = this.actorService.findByPrincipal();

		result = new ModelAndView("profile/display");

		isCustomer = this.actorService.checkAuthority(actor, Authority.CUSTOMER);
		if (isCustomer)
			account = "customer";
		else {
			isDeveloper = this.actorService.checkAuthority(actor, Authority.DEVELOPER);
			if (isDeveloper)
				account = "developer";
			else {
				isCritic = this.actorService.checkAuthority(actor, Authority.CRITIC);
				if (isCritic)
					account = "critic";
			}
		}

		result.addObject("profile", actor);
		result.addObject("account", account);
		result.addObject("requestURI", "profile/display");

		return result;
	}

	// Display ---------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;
		Boolean isCustomer;
		Boolean isDeveloper;
		Boolean isCritic;
		String account = "";

		actor = this.actorService.findOne(actorId);

		result = new ModelAndView("profile/display");

		isCustomer = this.actorService.checkAuthority(actor, Authority.CUSTOMER);
		if (isCustomer)
			account = "customer";
		else {
			isDeveloper = this.actorService.checkAuthority(actor, Authority.DEVELOPER);
			if (isDeveloper)
				account = "developer";
			else {
				isCritic = this.actorService.checkAuthority(actor, Authority.CRITIC);
				if (isCritic)
					account = "critic";
			}
		}

		result.addObject("profile", actor);
		result.addObject("account", account);
		result.addObject("requestURI", "profile/display");

		return result;
	}

}
