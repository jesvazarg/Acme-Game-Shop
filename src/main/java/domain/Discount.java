
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
	@Column(unique = true)
	@Pattern(regexp = "\\w{3}\\-\\w{3}\\-\\w{3}")
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
