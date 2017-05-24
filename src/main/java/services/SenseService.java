
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SenseRepository;
import domain.Customer;
import domain.Game;
import domain.Sense;

@Service
@Transactional
public class SenseService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SenseRepository	senseRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------
	public SenseService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Sense findOne(final int senseId) {
		Sense result;

		result = this.senseRepository.findOne(senseId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Sense> findAll() {
		Collection<Sense> result;

		result = this.senseRepository.findAll();

		return result;
	}

	public Sense createLike(final Game game) {
		final Customer customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		Assert.notNull(game);
		final Sense result = new Sense();

		result.setLike(true);
		result.setCustomer(customer);
		result.setGame(game);

		return result;
	}

	public Sense createDislike(final Game game) {
		final Customer customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		Assert.notNull(game);
		final Sense result = new Sense();

		result.setLike(false);
		result.setCustomer(customer);
		result.setGame(game);

		return result;
	}

	public Sense save(final Sense sense) {
		Assert.notNull(sense);
		Assert.isTrue(this.customerService.findByPrincipal().equals(sense.getCustomer()));

		Sense result;
		result = this.senseRepository.save(sense);
		return result;
	}

	public Sense change(final Sense sense, final boolean li) {
		Assert.notNull(sense);
		Assert.isTrue(this.customerService.findByPrincipal().equals(sense.getCustomer()));

		sense.setLike(li);
		Sense result;
		result = this.senseRepository.save(sense);
		return result;
	}

	public void delete(final Sense sense) {
		Assert.notNull(sense);
		Assert.notNull(this.customerService.findByPrincipal());
		Assert.isTrue(sense.getId() != 0);

		this.senseRepository.delete(sense);
	}

	public void deleteWithGame(final Sense sense) {
		Assert.notNull(sense);
		Assert.isTrue(sense.getId() != 0);

		this.senseRepository.delete(sense);
	}

	// Other business methods -------------------------------------------------

}
