
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Developer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreditCardTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private DeveloperService	developerService;


	// Tests ------------------------------------------------------------------

	//Crear una nueva tarjeta de crédito de un actor ya existente
	/*
	 * El primer error se produce al introducir un campo en blanco, el segundo al poner
	 * el año de expiracion en pasado (2015) y el tercero se produce por meter un número
	 * incorrecto
	 */

	@Test
	public void driverCreateCreditCard() {
		final Object testingData[][] = {
			{
				"Pablo Escobar", "VISA", "4928756507439105", 3, 2020, 745, 85, null
			}, {
				"Rick Grimes", "DISCOVER", "6420559532032202", 8, 2021, 156, 85, null
			}, {
				"Ragnar Lothbrok", "", "5019767397639669", 1, 2019, 688, 85, IllegalArgumentException.class
			}, {
				"Phil Dunphy", "MASTERCARD", "5293764977707328", 1, 2015, 688, 85, IllegalArgumentException.class
			}, {
				"Sheldon Cooper", "VISA", "44952880865", 11, 2018, 688, 85, ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createCreditCard((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Integer) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6],
				(Class<?>) testingData[i][7]);

	}
	protected void createCreditCard(final String holderName, final String brandName, final String number, final Integer expirationMonth, final Integer expirationYear, final Integer cvv, final Integer id, final Class<?> expected) {

		Class<?> caught = null;

		try {
			this.authenticate("developer3");
			CreditCard creditCard = null;

			creditCard = this.creditCardService.create();

			creditCard.setHolderName(holderName);
			creditCard.setBrandName(brandName);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(expirationMonth);
			creditCard.setExpirationYear(expirationYear);
			creditCard.setCvv(cvv);

			creditCard = this.creditCardService.saveRegister(creditCard);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	};

	//Editar una nueva tarjeta de crédito de un actor ya existente
	/*
	 * El primer error se produce al introducir un campo en blanco, el segundo al poner
	 * el año de expiracion en pasado (2015) y el tercero se produce por meter un número
	 * incorrecto
	 */
	@Test
	public void driverEditCreditCard() {
		final Object testingData[][] = {
			{
				"Pablo Escobar", "VISA", "4928756507439105", 3, 2020, 745, null
			}, {
				"Rick Grimes", "DISCOVER", "6420559532032202", 8, 2021, 156, null
			}, {
				"Ragnar Lothbrok", "", "5019767397639669", 1, 2019, 688, IllegalArgumentException.class
			}, {
				"Phil Dunphy", "MASTERCARD", "5293764977707328", 1, 2015, 688, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.editCreditCard((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Integer) testingData[i][4], (Integer) testingData[i][5], (Class<?>) testingData[i][6]);

	}
	protected void editCreditCard(final String holderName, final String brandName, final String number, final Integer expirationMonth, final Integer expirationYear, final Integer cvv, final Class<?> expected) {

		Class<?> caught = null;
		final Developer developer;

		try {
			this.authenticate("developer1");
			CreditCard creditCard = null;

			developer = this.developerService.findByPrincipal();

			creditCard = developer.getCreditCard();

			creditCard.setHolderName(holderName);
			creditCard.setBrandName(brandName);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(expirationMonth);
			creditCard.setExpirationYear(expirationYear);
			creditCard.setCvv(cvv);

			creditCard = this.creditCardService.save(creditCard);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//El primer test negativo es causado porque la id de la creditcard es de otra que pertence a otro customer 
	//y el segundo es porque no estamos logueado como customer.

	@Test
	public void driverDeleteCreditCard() {
		final Object testingData[][] = {
			{
				"customer1", 88, null
			}, {
				"customer1", 87, IllegalArgumentException.class
			}, {
				"admin", 88, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteCreditCard((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteCreditCard(final String user, final int creditCardId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);
			final CreditCard creditCard = this.creditCardService.findOne(creditCardId);

			this.creditCardService.delete(creditCard);

			this.creditCardService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
