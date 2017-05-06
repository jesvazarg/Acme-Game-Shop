
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Actor() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	name;
	private String	surname;
	private String	email;
	private String	phone;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "(\\+\\d{1,3} )?(\\(\\d{1,3}\\) )?(\\w{4,})?")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}


	// Relationships ----------------------------------------------------------
	private UserAccount			userAccount;
	private Collection<MessageEmail>	sentMessages;
	private Collection<MessageEmail>	receivedMessages;
	private CreditCard			creditCard;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "sender")
	public Collection<MessageEmail> getSentMessages() {
		return this.sentMessages;
	}
	public void setSentMessages(final Collection<MessageEmail> sentMessages) {
		this.sentMessages = sentMessages;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "recipient")
	public Collection<MessageEmail> getReceivedMessages() {
		return this.receivedMessages;
	}
	public void setReceivedMessages(final Collection<MessageEmail> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String maskEmailAndPhone(final String string) {

		final String masked = string.replaceAll("[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]", "***");
		final String masked2 = masked.replaceAll("(\\+\\d{1,3})?(\\(\\d{1,3}\\) )?(\\d{3,})", "***");

		return masked2;
	}

	// Relationships ----------------------------------------------------------

	@Valid
	@OneToOne(optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
