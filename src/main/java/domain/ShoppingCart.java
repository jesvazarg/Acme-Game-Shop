
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class ShoppingCart extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public ShoppingCart() {
		super();
	}


	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Collection<Game>	games;


	@NotNull
	@Valid
	@ManyToMany()
	public Collection<Game> getGames() {
		return this.games;
	}

	public void setGames(final Collection<Game> games) {
		this.games = games;
	}

}
