
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReceiptRepository;
import domain.OrderedGames;
import domain.Receipt;

@Service
@Transactional
public class ReceiptService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ReceiptRepository	receiptRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------
	public ReceiptService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Receipt findOne(final int receiptId) {
		Receipt result;

		result = this.receiptRepository.findOne(receiptId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Receipt> findAll() {
		Collection<Receipt> result;

		result = this.receiptRepository.findAll();

		return result;
	}

	public Receipt create() {
		final Receipt result = new Receipt();
		result.setCustomer(this.customerService.findByPrincipal());

		/* OrderedGames */
		final Collection<OrderedGames> orderedGames = new ArrayList<OrderedGames>();
		result.setOrderedGames(orderedGames);

		return result;
	}

	public Receipt save(final Receipt receipt) {
		Assert.notNull(receipt);
		Assert.isTrue(receipt.getCustomer().equals(this.customerService.findByPrincipal()));
		return this.receiptRepository.save(receipt);
	}

	// Other business methods -------------------------------------------------

}
