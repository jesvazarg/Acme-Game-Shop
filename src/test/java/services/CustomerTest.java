
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
import domain.Customer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private CustomerService	customerService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	//Registrarse en el sistema como cliente.
	//Loguearse en el sistema usando sus credenciales.

	//En este primer test vamos a registrarnos como cliente
	//El primer test negativo se produce porque el atributo Contraseña y Repetir contraseña no son iguales,
	//el siguiente error se produce porque el atributo IsAgree se encuetra a false (el usuario no ha aceptado los terminos)
	//por ultimo el correo electronico no sigue el formato adecuado
	@Test
	public void driverRegistrarUnCliente() {
		final Object testingData[][] = {
			{
				"customerJuan", "password", "password", "pepe", "fernandez", "pepe@gmail.com", "1254", "06/10/1980", true, null
			}, {
				"customerPepe", "password", "password", "pepe", "fernandez", "pepe@gmail.com", "1254", "06/10/1980", true, null
			}, {
				"customerGonzalo", "password", "password", "pepe", "fernandez", "pepe@gmail.com", "1254", "06/10/1980", true, null
			}, {
				"customerNew", "password", "passwordError", "pepe", "fernandez", "pepe@gmail.com", "1254", "06/10/1980", true, IllegalArgumentException.class
			}, {
				"customerNew", "password", "password", "pepe", "fernandez", "pepe@gmail.com", "1254", "06/10/1980", false, IllegalArgumentException.class
			}, {
				"customerNew", "password", "password", "pepe", "fernandez", "noTengoCorreo", "1254", "06/10/1980", true, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registrarUnCliente((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Boolean) testingData[i][8], (Class<?>) testingData[i][9]);
	}

	protected void registrarUnCliente(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final String phone, final String birthdate, final Boolean isAgree,
		final Class<?> expected) {
		Class<?> caught;
		Md5PasswordEncoder encoder;
		String passwordEncoded;
		String[] fecha;
		final Calendar calendar = Calendar.getInstance();

		caught = null;
		try {
			final Customer customer = this.customerService.create();

			Assert.isTrue(password.equals(confirmPassword));
			customer.getUserAccount().setUsername(username);
			encoder = new Md5PasswordEncoder();
			passwordEncoded = encoder.encodePassword(password, null);
			customer.getUserAccount().setPassword(passwordEncoded);

			customer.setName(name);
			customer.setSurname(surname);
			customer.setEmail(email);
			customer.setPhone(phone);
			/* Pasamos el atributo birthdate de String a Date */
			fecha = birthdate.split("/");
			calendar.set(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));

			customer.setBirthdate(calendar.getTime());

			Assert.isTrue(isAgree);

			this.customerService.saveRegister(customer);

			this.customerService.findAll();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//Con este test lo que hacemos es modificar el perfil del cliente que esta logueado
	//Las pruebas que dan errores son porque la contraseña no es igual que la confirmacion de la contraseña,
	//intentamos loguearnos con un cliente que no existe y ponemos un correo electronico no valido
	@Test
	public void driverEditarUnCliente() {
		final Object testingData[][] = {
			{
				"customer1", "password", "password", "pepe", "fernandez", "pepe@gmail.com", "1254", "06/10/1980", null
			}, {
				"customer2", "password", "password", "pepe", "fernandez", "pepe@gmail.com", "1254", "06/10/1980", null
			}, {
				"customer3", "password", "password", "pepe", "fernandez", "pepe@gmail.com", "1254", "06/10/1980", null
			}, {
				"customer1", "password", "passwordError", "pepe", "fernandez", "pepe@gmail.com", "1254", "06/10/1980", IllegalArgumentException.class
			}, {
				"customerInvetado", "password", "password", "pepe", "fernandez", "pepe@gmail.com", "1254", "06/10/1980", IllegalArgumentException.class
			}, {
				"customer2", "password", "password", "pepe", "fernandez", "noTengoCorreo", "1254", "06/10/1980", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editarUnCliente((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void editarUnCliente(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final String phone, final String birthdate, final Class<?> expected) {
		Class<?> caught;
		Md5PasswordEncoder encoder;
		String passwordEncoded;
		String[] fecha;
		final Calendar calendar = Calendar.getInstance();

		caught = null;
		try {
			this.authenticate(username);
			final Customer customer = this.customerService.findByPrincipal();

			Assert.isTrue(password.equals(confirmPassword));
			customer.getUserAccount().setUsername(username);
			encoder = new Md5PasswordEncoder();
			passwordEncoded = encoder.encodePassword(password, null);
			customer.getUserAccount().setPassword(passwordEncoded);

			customer.setName(name);
			customer.setSurname(surname);
			customer.setEmail(email);
			customer.setPhone(phone);
			/* Pasamos el atributo birthdate de String a Date */
			fecha = birthdate.split("/");
			calendar.set(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));

			customer.setBirthdate(calendar.getTime());

			this.customerService.saveRegister(customer);

			this.customerService.findAll();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
