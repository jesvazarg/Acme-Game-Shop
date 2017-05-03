
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Receipt extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Receipt() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private Date	moment;


	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	// Relationships ----------------------------------------------------------
	private Customer					customer;
	private Collection<OrderedGames>	orderedGames;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "receipt")
	public Collection<OrderedGames> getOrderedGames() {
		return this.orderedGames;
	}

	public void setOrderedGames(final Collection<OrderedGames> orderedGames) {
		this.orderedGames = orderedGames;
	}

}
