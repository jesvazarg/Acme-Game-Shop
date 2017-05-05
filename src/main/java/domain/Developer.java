
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Developer extends Actor {

	// Constructors ----------------------------------------------------------

	public Developer() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	company;


	@NotBlank
	public String getCompany() {
		return this.company;
	}

	public void setCompany(final String company) {
		this.company = company;
	}


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
