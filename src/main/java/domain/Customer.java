
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	// Constructors ----------------------------------------------------------
	public Customer() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private Date	birthday;


	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(final Date birthday) {
		this.birthday = birthday;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Sense>	senses;
	private Collection<Comment>	comments;
	private Collection<Receipt>	receipts;
	private ShoppingCart		shoppingCart;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "customer")
	public Collection<Sense> getSenses() {
		return this.senses;
	}

	public void setSenses(final Collection<Sense> senses) {
		this.senses = senses;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "customer")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "customer")
	public Collection<Receipt> getReceipts() {
		return this.receipts;
	}

	public void setReceipts(final Collection<Receipt> receipts) {
		this.receipts = receipts;
	}

	@Valid
	@OneToOne(optional = true)
	public ShoppingCart getShoppingCart() {
		return this.shoppingCart;
	}

	public void setShoppingCart(final ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

}
