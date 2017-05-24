
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Actor;
import domain.MessageEmail;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageEmailTest extends AbstractTest {

	@Autowired
	private MessageEmailService	messageEmailService;

	@Autowired
	private ActorService		actorService;


	// Tests ------------------------------------------------------------------

	// REQUISITOS FUNCIONALES
	//Chirp to another actor.
	//Browse the list of messages that he or she's got, and reply to any of them.
	//Browse the list of messages that he or she's sent, and re-send any of them.
	//Erase any of the messages that he or she's got or sent.

	//En este primer driver se comprueba que un actor pueda enviar un mensaje a otro actor

	@Test
	public void driverEnvioDeMensaje() {
		final Object testingData[][] = {
			{
				"customer1", "Envio1", "text1", this.actorService.findOne(81), null
			}, {
				"customer1", "Envio2", "text2", this.actorService.findOne(82), null
			}, {
				"developer2", "Envio3", "text3", this.actorService.findOne(80), null
			}, {
				"", "Envio4", "text4", this.actorService.findOne(81), IllegalArgumentException.class
			}, {
				"customer1", "Envio1", "", this.actorService.findOne(82), ConstraintViolationException.class
			}, {
				"customer1", "", "text1", this.actorService.findOne(83), ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.envioDeMensaje((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Actor) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void envioDeMensaje(final String sender, final String subject, final String text, final Actor recipient, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);
			final MessageEmail messageEmail = this.messageEmailService.create(recipient);

			messageEmail.setSubject(subject);
			messageEmail.setText(text);

			this.messageEmailService.save(messageEmail);

			this.messageEmailService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En este driver se comprueba que un actor puede responder a un determinado mensaje.

	@Test
	public void driverRespuestaDeMensaje() {
		final Object testingData[][] = {
			{
				"customer1", "Envio1", "text1", 131, null
			}, {
				"developer1", "Envio4", "text4", 131, IllegalArgumentException.class
			}, {
				"customer1", "Envio1", "", 131, ConstraintViolationException.class
			}, {
				"customer1", "Envio2", "text1", 130, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.respuestaDeMensaje((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void respuestaDeMensaje(final String sender, final String subject, final String text, final int chirp, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);
			final MessageEmail aux1 = this.messageEmailService.findOne(chirp);
			final MessageEmail aux2 = this.messageEmailService.reply(aux1);

			aux2.setSubject(subject);
			aux2.setText(text);

			this.messageEmailService.save(aux2);

			this.messageEmailService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En este driver se comprueba que un actor puede reenviar un mensaje.

	@Test
	public void driverReenvioDeMensaje() {
		final Object testingData[][] = {
			{
				"customer1", "Envio1", "text1", this.actorService.findOne(85), 130, null
			}, {
				"customer2", "Envio2", "text2", this.actorService.findOne(85), 133, null
			}, {
				"customer1", "Envio4", "text4", this.actorService.findOne(85), 131, IllegalArgumentException.class
			}, {
				"customer1", "", "text1", this.actorService.findOne(84), 130, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.reenvioDeMensaje((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Actor) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void reenvioDeMensaje(final String sender, final String subject, final String text, final Actor recipient, final int chirp, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);
			final MessageEmail aux1 = this.messageEmailService.findOne(chirp);
			final MessageEmail aux2 = this.messageEmailService.forward(aux1);

			aux2.setSubject(subject);
			aux2.setText(text);
			aux2.setRecipient(recipient);

			this.messageEmailService.save(aux2);

			this.messageEmailService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En este driver se comprueba que un actor puede borrar un mensaje suyo.

	@Test
	public void driverBorrarMensaje() {
		final Object testingData[][] = {
			{
				"customer1", 130, null
			}, {
				"", 130, IllegalArgumentException.class
			}, {
				"admin", 130, IllegalArgumentException.class
			}, {
				"developer1", 134, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.borrarMensaje((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void borrarMensaje(final String user, final int chirp, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);
			final MessageEmail aux = this.messageEmailService.findOne(chirp);
			this.messageEmailService.delete(aux);

			//this.chirpService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
