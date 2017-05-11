
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OrderedGamesRepository;
import domain.OrderedGames;
import domain.Receipt;

@Service
@Transactional
public class OrderedGamesService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private OrderedGamesRepository	orderedGamesRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ReceiptService			receiptService;

	@Autowired
	private CustomerService			customerService;


	// Constructors -----------------------------------------------------------
	public OrderedGamesService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public OrderedGames findOne(final int orderedGamesId) {
		OrderedGames result;

		result = this.orderedGamesRepository.findOne(orderedGamesId);
		Assert.notNull(result);

		return result;
	}

	public Collection<OrderedGames> findAll() {
		Collection<OrderedGames> result;

		result = this.orderedGamesRepository.findAll();

		return result;
	}

	public OrderedGames create() {
		final OrderedGames result = new OrderedGames();

		/* Receipt */
		final Receipt receipt = this.receiptService.create();
		result.setReceipt(receipt);

		return result;
	}

	public OrderedGames save(final OrderedGames orderedGames) {
		Assert.notNull(orderedGames);
		Assert.isTrue(orderedGames.getReceipt().getCustomer().equals(this.customerService.findByPrincipal()));
		return this.orderedGamesRepository.save(orderedGames);
	}

	// Other business methods -------------------------------------------------

}
