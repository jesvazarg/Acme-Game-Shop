
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CriticRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Critic;
import domain.MessageEmail;
import domain.Review;

@Service
@Transactional
public class CriticService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CriticRepository		criticRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	// Constructors------------------------------------------------------------
	public CriticService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Critic findOne(final int criticId) {
		Critic result;

		result = this.criticRepository.findOne(criticId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Critic> findAll() {
		Collection<Critic> result;

		result = this.criticRepository.findAll();

		return result;
	}

	public Critic create() {
		Critic result;
		UserAccount userAccount;
		Authority authority;
		Collection<Review> reviews;
		Collection<MessageEmail> sentMessages;
		Collection<MessageEmail> receivedMessages;
		Administrator principal;

		userAccount = new UserAccount();
		authority = new Authority();
		reviews = new ArrayList<Review>();
		sentMessages = new ArrayList<MessageEmail>();
		receivedMessages = new ArrayList<MessageEmail>();

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		authority.setAuthority(Authority.CRITIC);
		userAccount.addAuthority(authority);

		result = new Critic();
		result.setUserAccount(userAccount);
		result.setReviews(reviews);
		result.setSentMessages(sentMessages);
		result.setReceivedMessages(receivedMessages);

		return result;
	}

	public Critic save(final Critic critic) {
		Assert.notNull(critic);
		Critic result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.criticRepository.save(critic);
		return result;
	}

	// Other business methods -------------------------------------------------
	public Critic findByPrincipal() {
		Critic result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}
	public Critic findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Critic result;

		result = this.criticRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

}
