
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Customer;
import domain.MessageEmail;
import domain.Receipt;
import domain.Sense;
import domain.ShoppingCart;
import forms.CreateCustomerForm;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CustomerRepository	customerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ShoppingCartService	shoppingCartService;


	// Constructors -----------------------------------------------------------
	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Customer findOne(final int customerId) {
		Customer result;

		result = this.customerRepository.findOne(customerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> result;

		result = this.customerRepository.findAll();

		return result;
	}

	public Customer create() {
		final UserAccount userAccount = new UserAccount();

		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority(Authority.CUSTOMER);
		authorities.add(a);
		userAccount.setAuthorities(authorities);

		final Customer result = new Customer();

		result.setUserAccount(userAccount);

		/* Senses */
		final Collection<Sense> senses = new ArrayList<Sense>();
		result.setSenses(senses);

		/* Comments */
		final Collection<Comment> comments = new ArrayList<Comment>();
		result.setComments(comments);

		/* Receipts */
		final Collection<Receipt> receipts = new ArrayList<Receipt>();
		result.setReceipts(receipts);

		/* Messages */
		final Collection<MessageEmail> sentMessages = new ArrayList<MessageEmail>();
		final Collection<MessageEmail> receivedMessages = new ArrayList<MessageEmail>();
		result.setSentMessages(sentMessages);
		result.setReceivedMessages(receivedMessages);

		/* ShoppingCart */
		final ShoppingCart shoppingCart = new ShoppingCart();
		result.setShoppingCart(shoppingCart);

		return result;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		Customer result;

		result = this.customerRepository.save(customer);
		return result;
	}

	public Customer saveRegister(final Customer customer) {
		Assert.notNull(customer);
		Customer result;
		ShoppingCart shoppingCart;

		shoppingCart = this.shoppingCartService.create();
		shoppingCart = this.shoppingCartService.saveRegister(shoppingCart);
		customer.setShoppingCart(shoppingCart);
		result = this.customerRepository.save(customer);
		return result;
	}

	// Other business methods -------------------------------------------------
	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Customer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Customer result;

		result = this.customerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Customer reconstructProfile(final CreateCustomerForm createCustomerForm, final String type) {
		Assert.notNull(createCustomerForm);
		Customer customer = null;
		Md5PasswordEncoder encoder;
		String password;

		Assert.isTrue(createCustomerForm.getPassword().equals(createCustomerForm.getConfirmPassword()));

		//Creo uno nuevo vacio para meterle los datos del formulario a dicho customer
		if (type.equals("create")) {
			customer = this.create();
			Assert.isTrue(createCustomerForm.getIsAgree());
		} else if (type.equals("edit"))
			customer = this.findByPrincipal();

		password = createCustomerForm.getPassword();

		encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);

		customer.getUserAccount().setUsername(createCustomerForm.getUsername());
		customer.getUserAccount().setPassword(password);
		customer.setName(createCustomerForm.getName());
		customer.setSurname(createCustomerForm.getSurname());
		customer.setEmail(createCustomerForm.getEmail());
		customer.setPhone(createCustomerForm.getPhone());
		customer.setBirthdate(createCustomerForm.getBirthdate());

		return customer;
	}
	public CreateCustomerForm constructProfile(final Customer customer) {
		Assert.notNull(customer);
		CreateCustomerForm createCustomerForm;

		createCustomerForm = new CreateCustomerForm();
		createCustomerForm.setUsername(customer.getUserAccount().getUsername());
		createCustomerForm.setPassword(customer.getUserAccount().getPassword());
		createCustomerForm.setName(customer.getName());
		createCustomerForm.setSurname(customer.getSurname());
		createCustomerForm.setEmail(customer.getEmail());
		createCustomerForm.setPhone(customer.getPhone());
		createCustomerForm.setBirthdate(customer.getBirthdate());

		return createCustomerForm;
	}

}
