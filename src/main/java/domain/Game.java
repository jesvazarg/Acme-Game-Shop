
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Game extends DomainEntity {

	// Constructors -----------------------------------------------------------
	public Game() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	title;
	private String	description;
	private String	picture;
	private Integer	sellsNumber;
	private Integer	age;
	private Double	price;


	@NotNull
	@Min(0)
	public Integer getAge() {
		return this.age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	@NotBlank
	@Column(unique = true)
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

	@NotBlank
	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotNull
	@Min(0)
	public Integer getSellsNumber() {
		return this.sellsNumber;
	}
	public void setSellsNumber(final Integer sellsNumber) {
		this.sellsNumber = sellsNumber;
	}

	public Double getPrice() {
		return this.price;
	}
	public void setPrice(final Double price) {
		this.price = price;
	}


	// Relationships ----------------------------------------------------------
	private Developer					developer;
	private Collection<Review>			reviews;
	private Collection<Category>		categories;
	private Collection<Comment>			comments;
	private Collection<Sense>			senses;
	private Collection<ShoppingCart>	shoppingCarts;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Developer getDeveloper() {
		return this.developer;
	}

	public void setDeveloper(final Developer developer) {
		this.developer = developer;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "game")
	public Collection<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(final Collection<Review> reviews) {
		this.reviews = reviews;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "games")
	public Collection<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "game")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "game")
	public Collection<Sense> getSenses() {
		return this.senses;
	}

	public void setSenses(final Collection<Sense> senses) {
		this.senses = senses;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "games")
	public Collection<ShoppingCart> getShoppingCarts() {
		return this.shoppingCarts;
	}

	public void setShoppingCarts(final Collection<ShoppingCart> shoppingCarts) {
		this.shoppingCarts = shoppingCarts;
	}

}
