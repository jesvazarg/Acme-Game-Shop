
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "score")
})
public class Review extends DomainEntity {

	// Constructors ----------------------------------------------------------

	public Review() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	title;
	private String	description;
	private Date	moment;
	private Integer	score;
	private Boolean	draft;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotNull
	@Range(min = 0, max = 10)
	public Integer getScore() {
		return this.score;
	}
	public void setScore(final Integer score) {
		this.score = score;
	}

	@NotNull
	public Boolean getDraft() {
		return this.draft;
	}
	public void setDraft(final Boolean draft) {
		this.draft = draft;
	}


	// Relationships ----------------------------------------------------------

	private Critic	critic;
	private Game	game;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Critic getCritic() {
		return this.critic;
	}
	public void setCritic(final Critic critic) {
		this.critic = critic;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Game getGame() {
		return this.game;
	}
	public void setGame(final Game game) {
		this.game = game;
	}

}
