
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Discount extends DomainEntity {

	// Constructors ----------------------------------------------------------

	public Discount() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	code;
	private Integer	percentage;
	private Boolean	used;


	@NotBlank
	public String getCode() {
		return this.code;
	}
	public void setCode(final String code) {
		this.code = code;
	}

	@NotNull
	@Range(min = 0, max = 100)
	public Integer getPercentage() {
		return this.percentage;
	}
	public void setPercentage(final Integer percentage) {
		this.percentage = percentage;
	}

	@NotNull
	public Boolean getUsed() {
		return this.used;
	}
	public void setUsed(final Boolean used) {
		this.used = used;
	}

	// Relationships ----------------------------------------------------------

}
