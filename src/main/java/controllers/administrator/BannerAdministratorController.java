
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

import services.BannerService;
import controllers.AbstractController;
import domain.Banner;

@Controller
@RequestMapping("/banner/administrator")
public class BannerAdministratorController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private BannerService	bannerService;


	// Constructors -----------------------------------------------------------

	public BannerAdministratorController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Banner> banners;

		banners = this.bannerService.findAll();

		result = new ModelAndView("banner/list");
		result.addObject("banners", banners);
		result.addObject("requestURI", "banner/administrator/list.do");

		return result;
	}

	// Create, edit and delete ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Banner banner;

		banner = this.bannerService.create();
		result = this.createModelAndView(banner);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int bannerId) {
		ModelAndView result;
		Banner banner;

		banner = this.bannerService.findOne(bannerId);
		result = this.editModelAndView(banner);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@Valid final Banner banner, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(banner);
		else
			try {
				this.bannerService.save(banner);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createModelAndView(banner, "banner.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Banner banner, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(banner);
		else
			try {
				this.bannerService.save(banner);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.editModelAndView(banner, "banner.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int bannerId) {
		ModelAndView result;
		Banner banner;

		banner = this.bannerService.findOne(bannerId);
		this.bannerService.delete(banner);
		result = new ModelAndView("redirect:list.do");

		return result;
	}
	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createModelAndView(final Banner banner) {
		ModelAndView result;

		result = this.createModelAndView(banner, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Banner banner, final String message) {
		ModelAndView result;

		result = new ModelAndView("banner/create");
		result.addObject("banner", banner);
		result.addObject("requestURI", "banner/administrator/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editModelAndView(final Banner banner) {
		ModelAndView result;

		result = this.editModelAndView(banner, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Banner banner, final String message) {
		ModelAndView result;

		result = new ModelAndView("banner/edit");
		result.addObject("banner", banner);
		result.addObject("requestURI", "banner/administrator/edit.do");
		result.addObject("message", message);

		return result;
	}

}
