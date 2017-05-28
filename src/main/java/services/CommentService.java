
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;
import domain.Customer;
import domain.Game;

@Service
@Transactional
public class CommentService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CommentRepository	commentRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private GameService			gameService;


	// Constructors -----------------------------------------------------------
	public CommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Comment findOne(final int commentId) {
		Comment result;

		result = this.commentRepository.findOne(commentId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> result;

		result = this.commentRepository.findAll();

		return result;
	}

	public Comment create(final Game game) {
		Assert.notNull(game);
		Customer customer;
		Comment result;
		Calendar calendar;

		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result = new Comment();
		result.setMoment(calendar.getTime());
		result.setCustomer(customer);
		result.setGame(game);

		return result;
	}

	public Comment save(final Comment comment) {
		Assert.isTrue(this.customerService.findByPrincipal().equals(comment.getCustomer()));
		Assert.notNull(comment);

		Comment result;

		result = this.commentRepository.save(comment);

		return result;
	}

	public void delete(final Comment comment) {
		Assert.notNull(comment);
		Assert.isTrue(this.customerService.findByPrincipal().equals(comment.getCustomer()));

		this.commentRepository.delete(comment);
	}

	public void deleteWithGame(final Comment comment) {
		Assert.notNull(comment);

		this.commentRepository.delete(comment);
	}

	// Other business methods -------------------------------------------------

}
