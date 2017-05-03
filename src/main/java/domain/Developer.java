
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Developer extends Actor {

	// Constructors ----------------------------------------------------------

	public Developer() {
		super();
	}


	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Collection<Game>	games;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "developer")
	public Collection<Game> getGames() {
		return this.games;
	}

	public void setGames(final Collection<Game> games) {
		this.games = games;
	}

}
