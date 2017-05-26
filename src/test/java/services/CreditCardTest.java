
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
	@Test
	public void driverCreateAndEditCreditCard() {
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
				"Sheldon Cooper", "VISA", "44952880865", 16, 2018, 688, 85, ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createCreditCard((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Integer) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6],
				(Class<?>) testingData[i][7]);

	}
	protected void createCreditCard(final String holderName, final String brandName, final String number, final Integer expirationMonth, final Integer expirationYear, final Integer cvv, final Integer id, final Class<?> expected) {

		Class<?> caught = null;
		Developer developer = null;

		try {
			this.authenticate("developer3");
			CreditCard creditCard = null;

			developer = this.developerService.findOne(id);
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
	}

}
