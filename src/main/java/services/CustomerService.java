
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
import domain.Game;
import domain.MessageEmail;
import domain.Receipt;
import domain.Sense;
import domain.ShoppingCart;
import forms.CreateCustomerForm;

/**
 * Esta clase permite definir los metodos correspondientes
 * al usuario Customer de nuestra aplicación
 * 
 * @author Student
 * 
 */
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
	/**
	 * Metodo que permite dado un id devuelve el Customer con ese id.
	 * 
	 * @param customerId
	 *            Int
	 * @return Customer
	 */
	public Customer findOne(final int customerId) {
		Customer result;

		result = this.customerRepository.findOne(customerId);
		Assert.notNull(result);

		return result;
	}

	/**
	 * Metodo que devuelve todos los Customers de nuestra aplicación
	 * 
	 * @return Collection<Customer>
	 */
	public Collection<Customer> findAll() {
		Collection<Customer> result;

		result = this.customerRepository.findAll();

		return result;
	}

	/**
	 * Este metodo permite crear un nuevo customer
	 * 
	 * @return Customer
	 */
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

	/**
	 * Nos guarda un Customer en la base de datos. Este metodo se usa cuando queremos
	 * editar el perfil de un Customer
	 * 
	 * @param customer
	 * @return Customer
	 */
	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		Customer result;

		result = this.customerRepository.save(customer);
		return result;
	}

	/**
	 * Nos guarda un Customer en la base de datos. Este metodo se usa cuando queremos
	 * registrar un nuevo Customer en el sistema.
	 * 
	 * @param customer
	 * @return Customer
	 */
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
	/**
	 * Este metodo nos devuelve el Customer que esta logueado en nuestro sistema actualmente
	 * 
	 * @return Customer
	 */
	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	/**
	 * Este metodo nos devuelve el un Customer dado su UserAccount
	 * 
	 * @param userAccount
	 * @return Customer
	 */
	public Customer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Customer result;

		result = this.customerRepository.findByUserAccountId(userAccount.getId());
		//Assert.notNull(result);

		return result;
	}

	/**
	 * Metodo que dado un formulario nos devuelve un Customer
	 * 
	 * @param createCustomerForm
	 *            Formulario
	 * @param type
	 *            Puede tomar los valores create o edit
	 * @return Customer
	 */
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
	/**
	 * Metodo que dado un Customer nos devuelve un formulario
	 * 
	 * @param customer
	 * @return CreateCustomerForm
	 */
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

	/**
	 * Metodo que dado un Customer nos devuelve la edad de dicho Customer
	 * 
	 * @param customer
	 * @return Integer
	 */
	@SuppressWarnings("deprecation")
	public Integer edadCustomer(final Customer customer) {
		Assert.notNull(customer);
		Integer result;
		Date calendarHoy;
		Date cumple;

		cumple = customer.getBirthdate();
		calendarHoy = Calendar.getInstance().getTime();

		result = calendarHoy.getYear() - cumple.getYear();
		return result;
	}

	/**
	 * Dado un Customer y un Game nos dice si ese Customer puede comprar dicho Game
	 * 
	 * @param customer
	 * @param game
	 * @return Boolean
	 */
	public Boolean canBuyThisGame(final Customer customer, final Game game) {
		Assert.notNull(customer);
		Assert.notNull(game);
		Boolean result = false;

		if (this.edadCustomer(customer) >= game.getAge())
			result = true;
		return result;
	}

	public Collection<Customer> findCustomersWithMoreComments() {
		return this.customerRepository.findCustomersWithMoreComments();
	}
}
