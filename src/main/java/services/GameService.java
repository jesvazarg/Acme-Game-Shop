
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	private CategoryService		categoryService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private CommentService		commentService;

	@Autowired
	private SenseService		senseService;

	@Autowired
	private ReviewService		reviewsService;

	@Autowired
	private ShoppingCartService	shoppingCartService;


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

		senses = new ArrayList<Sense>();
		reviews = new ArrayList<Review>();
		categories = new ArrayList<Category>();
		comments = new ArrayList<Comment>();

		result = new Game();
		result.setCategories(categories);
		result.setComments(comments);
		result.setReviews(reviews);
		result.setSenses(senses);
		result.setDeveloper(developer);
		result.setSellsNumber(0);

		return result;
	}

	public void sell(final Game game) {
		Assert.notNull(game);
		Customer customer;

		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		game.setSellsNumber(game.getSellsNumber() + 1);

		this.gameRepository.save(game);
		//return result;
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

		Collection<Category> categories;
		Collection<Comment> comments;
		Collection<Sense> senses;
		Collection<Review> reviews;
		Collection<ShoppingCart> shoppingCarts;

		/* Borramos las categorias */
		categories = game.getCategories();
		for (final Category category : categories)
			this.categoryService.deleteGame(category, game);

		/* Borramos los comentarios */
		comments = game.getComments();
		for (final Comment comment : comments)
			this.commentService.deleteWithGame(comment);

		/* Borramos los likes */
		senses = game.getSenses();
		for (final Sense sense : senses)
			this.senseService.deleteWithGame(sense);

		/* Borramos las criticas */
		reviews = game.getReviews();
		for (final Review review : reviews)
			this.reviewsService.deleteWithGame(review);

		/* Lo borramos de las shoppingCart que lo contengan */
		shoppingCarts = this.shoppingCartService.findAll();
		for (final ShoppingCart shoppingCart : shoppingCarts)
			if (shoppingCart.getGames().contains(game))
				this.shoppingCartService.removeGameToShoppingCartWithGame(game, shoppingCart);

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

	public List<Game> findGameBestAndWorstScoreReviews() {
		List<Game> lista;
		final List<Game> res = new ArrayList<Game>();

		lista = this.gameRepository.findGamesOrderByScoreReviews();
		if (!lista.isEmpty()) {
			res.add(lista.get(0));
			res.add(lista.get(lista.size() - 1));
		}
		return res;
	}

	public List<Game> findGameBestAndWorstScoreComments() {
		List<Game> lista;
		final List<Game> res = new ArrayList<Game>();

		lista = this.gameRepository.findGamesOrderByScoreComments();
		if (!lista.isEmpty()) {
			res.add(lista.get(0));
			res.add(lista.get(lista.size() - 1));
		}
		return res;
	}
}
