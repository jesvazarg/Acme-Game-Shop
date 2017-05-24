
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Discount;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DiscountTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private DiscountService	discountService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS

	//Registrar un nuevo descuento
	@Test
	public void driverRegisterDiscount() {
		final Object testingData[][] = {
			{
				"admin", 25, null
			}, {
				"admin", 75, null
			}, {
				"critic1", 50, IllegalArgumentException.class
			}, {
				"admin", 200, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registerDiscount((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void registerDiscount(final String loged, final Integer percentage, final Class<?> expected) {

		Discount discount = null;
		Class<?> caught = null;

		try {
			this.authenticate(loged);

			discount = this.discountService.create();

			discount.setPercentage(percentage);

			discount = this.discountService.save(discount);
			this.discountService.findAll();
			this.discountService.findOne(discount.getId());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Editar un descuento
	@Test
	public void driverEditDiscount() {
		final Object testingData[][] = {
			{
				"admin", 117, 0, null
			}, {
				"admin", 118, 100, null
			}, {
				"critic1", 119, 50, IllegalArgumentException.class
			}, {
				"admin", 120, 400, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editDiscount((String) testingData[i][0], (int) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void editDiscount(final String loged, final int discountId, final Integer percentage, final Class<?> expected) {

		Discount discount = null;
		Class<?> caught = null;

		try {
			this.authenticate(loged);

			discount = this.discountService.findOne(discountId);

			discount.setPercentage(percentage);

			discount = this.discountService.save(discount);
			this.discountService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Borrar un descuento
	@Test
	public void driverBorrarDiscount() {
		final Object testingData[][] = {
			{
				"admin", 117, null
			}, {
				"admin", 118, null
			}, {
				"critic1", 119, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.borrarDiscount((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void borrarDiscount(final String loged, final int discountId, final Class<?> expected) {

		Discount discount = null;
		Class<?> caught = null;

		try {
			this.authenticate(loged);

			discount = this.discountService.findOne(discountId);

			this.discountService.delete(discount);
			this.discountService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
