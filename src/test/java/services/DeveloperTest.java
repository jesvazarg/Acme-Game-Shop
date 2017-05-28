
package services;

import java.util.Calendar;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Developer;
import forms.CreateDeveloperForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DeveloperTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private DeveloperService	developerService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	//Registrarse en el sistema como developer.
	//Loguearse en el sistema usando sus credenciales.

	//En este primer test vamos a registrarnos como developer
	//El primer test negativo se produce porque el atributo Contraseña y Repetir contraseña no son iguales,
	//el siguiente error se produce porque el atributo IsAgree se encuetra a false (el usuario no ha aceptado los terminos)
	//por ultimo el correo electronico no sigue el formato adecuado
	@Test
	public void driverRegistrarDeveloper() {
		final Object testingData[][] = {
			{
				"developerSantiago", "password", "password", "santiago", "fraga", "sant@gmail.com", "346578921", "Mercadona", true, null
			}, {
				"developerJesus", "password", "password", "jesus", "vazquez", "jes@gmail.com", "346578921", "Activision", true, null
			}, {
				"developerPablo", "password", "password", "pablo", "buenaventura", "pab@gmail.com", "346578921", "Microsoft", true, null
			}, {
				"developerNew", "password", "passwordError", "new", "cortes", "new@gmail.com", "346578921", "Bungie", true, IllegalArgumentException.class
			}, {
				"developerNew", "password", "password", "new", "alba", "new2@gmail.com", "346578921", "Sony", false, IllegalArgumentException.class
			}, {
				"developerNew", "password", "password", "new", "surname", "noTengoCorreo", "346578921", "EA", true, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registrarDeveloper((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Boolean) testingData[i][8], (Class<?>) testingData[i][9]);
	}

	protected void registrarDeveloper(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final String phone, final String company, final Boolean isAgree,
		final Class<?> expected) {
		Class<?> caught;
		Md5PasswordEncoder encoder;
		String passwordEncoded;

		caught = null;
		try {
			final Developer developer = this.developerService.create();

			Assert.isTrue(password.equals(confirmPassword));
			developer.getUserAccount().setUsername(username);
			encoder = new Md5PasswordEncoder();
			passwordEncoded = encoder.encodePassword(password, null);
			developer.getUserAccount().setPassword(passwordEncoded);

			developer.setName(name);
			developer.setSurname(surname);
			developer.setEmail(email);
			developer.setPhone(phone);
			developer.setCompany(company);

			Assert.isTrue(isAgree);

			this.developerService.save(developer);

			this.developerService.findAll();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//Con este test comprobamos que nos logueamos correctamente
	@Test
	public void driverLogInDeveloper() {
		final Object testingData[][] = {
			{
				"developer1", null
			}, {
				"developer2", null
			}, {
				"admin", IllegalArgumentException.class
			}, {
				"", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.loguearteComoCustomer((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void loguearteComoCustomer(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Developer developer = this.developerService.findByPrincipal();
			Assert.notNull(developer);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//Con este test lo que hacemos es modificar el perfil del developer que esta logueado
	//Las pruebas que dan errores son porque la contraseña no es igual que la confirmacion de la contraseña,
	//porque intentamos loguearnos con un cliente que no existe y porque ponemos un correo electronico no valido

	@Test
	public void driverEditDeveloper() {
		final Object testingData[][] = {
			{
				"developer1", "password", "password", "pepe", "fernandez", "pepe@gmail.com", "1254", "Nintendo", null
			}, {
				"developer2", "password", "password", "pepe", "fernandez", "pepe@gmail.com", "1254", "Ubisoft", null
			}, {
				"developer3", "password", "password", "pepe", "fernandez", "pepe@gmail.com", "1254", "Blizzard", null
			}, {
				"developer1", "password", "passwordError", "pepe", "fernandez", "pepe@gmail.com", "1254", "EA", IllegalArgumentException.class
			}, {
				"papepipopu", "password", "password", "pepe", "fernandez", "pepe@gmail.com", "1254", "Riot", IllegalArgumentException.class
			}, {
				"developer1", "password", "password", "pepe", "fernandez", "noTengoCorreo", "1254", "Sony", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editarUnCliente((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void editarUnCliente(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final String phone, final String company, final Class<?> expected) {
		Class<?> caught;
		final String[] fecha;
		final Calendar calendar = Calendar.getInstance();

		caught = null;
		try {
			this.authenticate(username);
			Developer developer = this.developerService.findByPrincipal();
			final CreateDeveloperForm createDeveloperForm = this.developerService.constructProfile(developer);

			createDeveloperForm.setUsername(username);
			createDeveloperForm.setPassword(password);
			createDeveloperForm.setConfirmPassword(confirmPassword);

			createDeveloperForm.setName(name);
			createDeveloperForm.setSurname(surname);
			createDeveloperForm.setEmail(email);
			createDeveloperForm.setPhone(phone);

			createDeveloperForm.setCompany(company);

			developer = this.developerService.reconstructProfile(createDeveloperForm, "edit");

			this.developerService.save(developer);

			this.developerService.findAll();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
