
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@AttributeOverrides({
	@AttributeOverride(name = "like", column = @Column(name = "likeGame"))
})
public class Sense extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Sense() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private Boolean	like;


	@NotNull
	public Boolean getLike() {
		return this.like;
	}

	public void setLike(final Boolean like) {
		this.like = like;
	}


	// Relationships ----------------------------------------------------------
	private Customer	customer;
	private Game		game;


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
	@ManyToOne(optional = false)
	public Game getGame() {
		return this.game;
	}

	public void setGame(final Game game) {
		this.game = game;
	}

}
