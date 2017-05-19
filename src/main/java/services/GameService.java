
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.GameRepository;
import domain.Category;
import domain.Comment;
import domain.Customer;
import domain.Developer;
import domain.Game;
import domain.Review;
import domain.Sense;
import domain.ShoppingCart;

@Service
@Transactional
public class GameService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private GameRepository		gameRepository;

	@Autowired
	private DeveloperService	developerService;

	@Autowired
	private CustomerService		customerService;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public GameService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Game findOne(final int gameId) {
		Game game;

		game = this.gameRepository.findOne(gameId);
		Assert.notNull(game);

		return game;
	}

	public Collection<Game> findAll() {
		Collection<Game> result;

		result = this.gameRepository.findAll();

		return result;
	}

	public Game create() {
		Game result;

		final Developer developer = this.developerService.findByPrincipal();

		Collection<Sense> senses;
		Collection<Review> reviews;
		Collection<Category> categories;
		Collection<Comment> comments;
		Collection<ShoppingCart> shoppingCarts;

		senses = new ArrayList<Sense>();
		reviews = new ArrayList<Review>();
		categories = new ArrayList<Category>();
		comments = new ArrayList<Comment>();
		shoppingCarts = new ArrayList<ShoppingCart>();

		result = new Game();
		result.setCategories(categories);
		result.setComments(comments);
		result.setReviews(reviews);
		result.setSenses(senses);
		result.setDeveloper(developer);
		result.setSellsNumber(0);

		return result;
	}

	public Game save(final Game game) {
		Assert.notNull(game);
		Developer developer;
		developer = this.developerService.findByPrincipal();
		Assert.isTrue(game.getDeveloper().equals(developer));
		Game result;

		result = this.gameRepository.save(game);
		return result;
	}

	public void delete(final Game game) {
		Assert.notNull(game);
		Developer developer;
		developer = this.developerService.findByPrincipal();
		Assert.isTrue(game.getDeveloper().equals(developer));

		this.gameRepository.delete(game);
	}

	// Other business methods -------------------------------------------------

	public Collection<Game> findByDeveloperId(final int developerId) {
		Collection<Game> result;
		Developer developer;
		developer = this.developerService.findOne(developerId);
		result = this.gameRepository.findByCustomerId(developer.getId());

		return result;
	}

	public Collection<Game> findAllUnderThirteen() {
		Collection<Game> result;
		result = this.gameRepository.findAllUnderThirteen();
		return result;
	}

	public Collection<Game> findByAge() {
		Collection<Game> result;
		final Customer customer = this.customerService.findByPrincipal();
		//		if(customerService.edadCustomer(customer)>=18){
		//			result = gameRepository.findAll();
		//		}else{
		result = this.gameRepository.findAllByAge(this.customerService.edadCustomer(customer));
		//}
		return result;
	}

	public Collection<Game> gameMoreLikes() {
		return this.gameRepository.gameMoreLikes();
	}

	public Collection<Game> gameLessLikes() {
		return this.gameRepository.gameLessLikes();
	}

	public Collection<Game> gamesMoreThatAVG() {
		return this.gameRepository.gamesMoreThatAVG();
	}

}
