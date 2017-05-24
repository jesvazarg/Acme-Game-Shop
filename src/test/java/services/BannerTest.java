
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Banner;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BannerTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private BannerService	bannerService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS

	//Registrar un nuevo banner
	@Test
	public void driverRegisterBanner() {
		final Object testingData[][] = {
			{
				"admin", "http:www.web.com/image.jpg", null
			}, {
				"admin", "https:www.web.com/image2.png", null
			}, {
				"critic1", "https:www.web.com/image3.gif", IllegalArgumentException.class
			}, {
				"admin", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registerBanner((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void registerBanner(final String loged, final String picture, final Class<?> expected) {

		Banner banner = null;
		Class<?> caught = null;

		try {
			this.authenticate(loged);

			banner = this.bannerService.create();

			banner.setPicture(picture);

			banner = this.bannerService.save(banner);
			this.bannerService.findAll();
			this.bannerService.findOne(banner.getId());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Editar un banner
	@Test
	public void driverEditBanner() {
		final Object testingData[][] = {
			{
				"admin", 114, "http:www.web.com/image.jpg", null
			}, {
				"admin", 115, "http:www.web.com/image2.png", null
			}, {
				"critic1", 116, "http:www.web.com/image3.gif", IllegalArgumentException.class
			}, {
				"admin", 114, "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editBanner((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void editBanner(final String loged, final int bannerId, final String picture, final Class<?> expected) {

		Banner banner = null;
		Class<?> caught = null;

		try {
			this.authenticate(loged);

			banner = this.bannerService.findOne(bannerId);

			banner.setPicture(picture);

			banner = this.bannerService.save(banner);
			this.bannerService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Borrar un banner
	@Test
	public void driverBorrarBanner() {
		final Object testingData[][] = {
			{
				"admin", 114, null
			}, {
				"admin", 115, null
			}, {
				"critic1", 116, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.borrarBanner((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void borrarBanner(final String loged, final int bannerId, final Class<?> expected) {

		Banner banner = null;
		Class<?> caught = null;

		try {
			this.authenticate(loged);

			banner = this.bannerService.findOne(bannerId);

			this.bannerService.delete(banner);
			this.bannerService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
