
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ShoppingCartRepository;
import domain.Game;
import domain.OrderedGames;
import domain.Receipt;
import domain.ShoppingCart;

@Service
@Transactional
public class ShoppingCartService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ShoppingCartRepository	shoppingCartRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ReceiptService			receiptService;

	@Autowired
	private OrderedGamesService		orderedGamesService;


	// Constructors -----------------------------------------------------------
	public ShoppingCartService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public ShoppingCart findOne(final int shoppingCartId) {
		ShoppingCart result;

		result = this.shoppingCartRepository.findOne(shoppingCartId);
		Assert.notNull(result);

		return result;
	}

	public Collection<ShoppingCart> findAll() {
		Collection<ShoppingCart> result;

		result = this.shoppingCartRepository.findAll();

		return result;
	}

	public ShoppingCart create() {
		final ShoppingCart result = new ShoppingCart();

		/* Games */
		final Collection<Game> games = new ArrayList<Game>();
		result.setGames(games);

		return result;

	}

	public ShoppingCart saveRegister(final ShoppingCart shoppingCart) {
		Assert.notNull(shoppingCart);
		return this.shoppingCartRepository.save(shoppingCart);
	}

	public ShoppingCart save(final ShoppingCart shoppingCart) {
		Assert.notNull(shoppingCart);
		Assert.isTrue(this.customerService.findByPrincipal().getShoppingCart().getId() == shoppingCart.getId());
		return this.shoppingCartRepository.save(shoppingCart);
	}

	// Other business methods -------------------------------------------------
	public ShoppingCart addGameToShoppingCart(final Game game) {
		Assert.notNull(game);
		ShoppingCart shoppingCart;
		Collection<Game> gamesInCart;
		shoppingCart = this.customerService.findByPrincipal().getShoppingCart();

		gamesInCart = shoppingCart.getGames();

		if (!gamesInCart.contains(game))
			gamesInCart.add(game);
		shoppingCart.setGames(gamesInCart);
		shoppingCart = this.save(shoppingCart);

		return shoppingCart;

	}

	public ShoppingCart removeGameToShoppingCart(final Game game) {
		Assert.notNull(game);
		ShoppingCart shoppingCart;
		Collection<Game> gamesInCart;
		shoppingCart = this.customerService.findByPrincipal().getShoppingCart();

		gamesInCart = shoppingCart.getGames();

		if (gamesInCart.contains(game))
			gamesInCart.remove(game);
		shoppingCart.setGames(gamesInCart);
		shoppingCart = this.save(shoppingCart);

		return shoppingCart;

	}

	public void buyGamesInShoppingCart(final ShoppingCart shoppingCart) {
		Assert.notNull(shoppingCart);

		final Collection<Game> games = shoppingCart.getGames();

		/* Receipt */
		Receipt receipt = this.receiptService.create();
		receipt = this.receiptService.save(receipt);

		/* Ordered Games */
		for (final Game aux : games) {
			final OrderedGames orderedGames = this.orderedGamesService.create();
			orderedGames.setTitle(aux.getTitle());
			orderedGames.setPrice(aux.getPrice());
			orderedGames.setReceipt(receipt);
			this.orderedGamesService.save(orderedGames);
		}
		final Collection<Game> gamesEmpty = new ArrayList<Game>();
		shoppingCart.setGames(gamesEmpty);
		this.save(shoppingCart);
	}
}