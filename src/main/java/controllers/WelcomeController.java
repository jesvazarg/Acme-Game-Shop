/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BannerService;
import domain.Banner;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private BannerService	bannerService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/indexNotAuth")
	public ModelAndView indexNotAuth(@RequestParam(required = false, defaultValue = "") final String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		Banner banner;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		banner = this.bannerService.getRandomBanner();

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		result.addObject("banner", banner);

		return result;
	}

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "") String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		Banner banner;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		banner = this.bannerService.getRandomBanner();
		name = this.actorService.findByPrincipal().getName();

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		result.addObject("banner", banner);

		return result;
	}
}
