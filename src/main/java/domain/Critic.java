
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
public class Critic extends Actor {

	// Constructors ----------------------------------------------------------

	public Critic() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	magazine;


	@NotBlank
	public String getMagazine() {
		return this.magazine;
	}
	public void setMagazine(final String magazine) {
		this.magazine = magazine;
	}


	// Relationships ----------------------------------------------------------

	private Collection<Review>	reviews;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "critic")
	public Collection<Review> getReviews() {
		return this.reviews;
	}
	public void setReviews(final Collection<Review> reviews) {
		this.reviews = reviews;
	}

}
