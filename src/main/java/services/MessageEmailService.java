
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageEmailRepository;
import domain.Actor;
import domain.MessageEmail;

@Service
@Transactional
public class MessageEmailService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MessageEmailRepository	messageEmailRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private DeveloperService		developerService;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public MessageEmailService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public MessageEmail findOne(final int messageEmailId) {
		Assert.isTrue(messageEmailId != 0);
		MessageEmail messageEmail;

		messageEmail = this.messageEmailRepository.findOne(messageEmailId);
		Assert.notNull(messageEmail);
		return messageEmail;
	}

	public Collection<MessageEmail> findAll() {
		Collection<MessageEmail> result;

		result = this.messageEmailRepository.findAll();

		return result;
	}

	public MessageEmail create() {
		MessageEmail result;

		Calendar calendar;

		result = new MessageEmail();
		final Actor actor = this.actorService.findByPrincipal();

		Assert.notNull(actor);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result.setAttachments(new ArrayList<String>());
		result.setMoment(calendar.getTime());
		result.setSender(actor);
		result.setDeletedForRecipient(false);
		result.setDeletedForSender(false);

		return result;
	}

	public MessageEmail create(final Actor recipient) {
		MessageEmail result;

		Calendar calendar;

		result = new MessageEmail();

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result.setAttachments(new ArrayList<String>());
		result.setMoment(calendar.getTime());
		result.setSender(actor);
		result.setDeletedForRecipient(false);
		result.setDeletedForSender(false);
		result.setRecipient(recipient);
		return result;
	}

	public MessageEmail forward(final MessageEmail messageEmail) {
		Assert.notNull(messageEmail);
		MessageEmail result;
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(messageEmail.getSender().equals(actor) && messageEmail.getDeletedForSender() == false);
		result = new MessageEmail();
		result.setSubject(messageEmail.getSubject());
		result.setText(messageEmail.getText());
		result.setMoment(messageEmail.getMoment());
		result.setAttachments(messageEmail.getAttachments());
		result.setSender(actor);
		result.setDeletedForRecipient(false);
		result.setDeletedForSender(false);
		return result;
	}

	public MessageEmail reply(final MessageEmail messageEmail) {
		Assert.notNull(messageEmail);
		Assert.isTrue(messageEmail.getRecipient().equals(this.actorService.findByPrincipal()) && messageEmail.getDeletedForRecipient() == false);
		final MessageEmail result = this.create();

		result.setRecipient(messageEmail.getSender());
		result.setSubject("Re: " + messageEmail.getSubject());

		return result;
	}

	public void delete(final MessageEmail messageEmail) {
		Assert.notNull(messageEmail);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(messageEmail.getSender().equals(actor) || messageEmail.getRecipient().equals(actor));

		if ((messageEmail.getSender().equals(actor) || messageEmail.getRecipient().equals(actor)) && messageEmail.getDeletedForRecipient() == true && messageEmail.getDeletedForSender() == true)
			this.messageEmailRepository.delete(messageEmail);
		if (messageEmail.getRecipient().equals(actor) && messageEmail.getDeletedForRecipient() == false) {
			if (messageEmail.getDeletedForSender() == false) {
				messageEmail.setDeletedForRecipient(true);
				this.save(messageEmail);
			} else
				this.messageEmailRepository.delete(messageEmail);

		} else if (messageEmail.getSender().equals(actor) && messageEmail.getDeletedForSender() == false)
			if (messageEmail.getDeletedForRecipient() == false) {
				messageEmail.setDeletedForSender(true);
				this.save(messageEmail);
			} else
				this.messageEmailRepository.delete(messageEmail);

	}

	public MessageEmail save(MessageEmail messageEmail) {
		Assert.notNull(messageEmail);
		Assert.isTrue(this.validatorURL(messageEmail.getAttachments()));
		Assert.notNull(messageEmail.getId() != 0);

		messageEmail = this.messageEmailRepository.save(messageEmail);

		return messageEmail;
	}

	// Other business methods -------------------------------------------------

	public Collection<MessageEmail> findMessageEmailsSentByActorId(final int actorId) {

		Collection<MessageEmail> result;

		result = this.messageEmailRepository.findMessageEmailsSentByActorId(actorId);

		return result;

	}

	public Collection<MessageEmail> findMessageEmailsReceivedByActorId(final int actorId) {

		Collection<MessageEmail> result;

		result = this.messageEmailRepository.findMessageEmailsReceivedByActorId(actorId);

		return result;

	}

	//Devuelve true si la collection esta vacia o si las URLs contenidas en ellas son URLs validas
	public Boolean validatorURL(final Collection<String> lista) {
		Boolean res = false;
		if (!lista.isEmpty()) {
			for (final String aux : lista)
				if (aux.length() > 11) {
					if ((aux.subSequence(0, 11).equals("http://www.") || (aux.subSequence(0, 12).equals("https://www."))))
						res = true;
					else {
						res = false;
						break;
					}
				} else {
					res = false;
					break;
				}
		} else
			res = true;

		return res;
	}

}
