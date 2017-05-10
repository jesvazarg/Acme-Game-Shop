
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// Constructors ----------------------------------------------------------

	public Category() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	name;


	@NotBlank
	@Column(unique = true)
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}


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
