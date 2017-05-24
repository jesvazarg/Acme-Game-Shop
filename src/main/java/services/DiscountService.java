
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DiscountRepository;
import domain.Discount;

@Service
@Transactional
public class DiscountService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private DiscountRepository		discountRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomerService			customerService;


	// Constructors------------------------------------------------------------
	public DiscountService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Discount findOne(final int discountId) {
		Discount result;

		result = this.discountRepository.findOne(discountId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Discount> findAll() {
		Collection<Discount> result;

		result = this.discountRepository.findAll();

		return result;
	}

	public Discount create() {
		Discount result;
		String code;

		Assert.notNull(this.administratorService.findByPrincipal());
		code = this.getRandomCode();

		result = new Discount();
		result.setCode(code);
		result.setUsed(false);

		return result;
	}

	public Discount save(final Discount discount) {
		Assert.notNull(discount);
		Discount result;

		result = this.discountRepository.save(discount);

		return result;
	}

	public void delete(final Discount discount) {
		Assert.notNull(discount);
		Assert.isTrue(discount.getId() != 0);

		Assert.notNull(this.administratorService.findByPrincipal());

		this.discountRepository.delete(discount);
	}

	// Other business methods -------------------------------------------------
	public void useDiscount(final Discount discount) {
		Assert.notNull(discount);
		Assert.notNull(this.customerService.findByPrincipal());
		Assert.isTrue(discount.getUsed() == false);

		discount.setUsed(true);
		this.save(discount);
	}

	public String getRandomCode() {
		String result;
		StringBuffer buffer;
		String characters;
		int index;

		do {
			buffer = new StringBuffer();
			characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

			for (int i = 1; i <= 9; i++) {
				index = (int) (Math.random() * characters.length());
				buffer.append(characters.charAt(index));
				if ((i % 3 == 0) && (i < 9))
					buffer.append("-");
			}
			result = buffer.toString();
		} while (this.getDiscountWithCode(result) != null);

		return result;
	}

	public Discount getDiscountWithCode(final String code) {
		return this.discountRepository.getDiscountWithCode(code);
	}

	public Object[] MaxAvgMinPercentagePerDiscount() {
		final Object[] res = new Object[3];
		final Double[] aux = this.discountRepository.MaxAvgMinPercentagePerDiscount();
		res[0] = (int) (double) aux[0];
		res[1] = Math.round(aux[1] * 100) / 100.0;
		res[2] = (int) (double) aux[2];
		return res;
	}

}
