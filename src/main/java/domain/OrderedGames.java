
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class OrderedGames extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public OrderedGames() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	title;
	private Double	price;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@Min(0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}


	// Relationships ----------------------------------------------------------
	private Receipt	receipt;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Receipt getReceipt() {
		return this.receipt;
	}

	public void setReceipt(final Receipt receipt) {
		this.receipt = receipt;
	}

}
