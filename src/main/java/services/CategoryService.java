
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CategoryRepository		categoryRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	// Constructors------------------------------------------------------------
	public CategoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Category findOne(final int categoryId) {
		Category result;

		result = this.categoryRepository.findOne(categoryId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> result;

		result = this.categoryRepository.findAll();

		return result;
	}

	public Category create() {
		Category result;

		Assert.notNull(this.administratorService.findByPrincipal());

		result = new Category();

		return result;
	}

	public Category save(final Category category) {
		Assert.notNull(category);
		Category result;

		Assert.notNull(this.administratorService.findByPrincipal());
		Assert.isTrue(category.getGames().size() == 0);

		result = this.categoryRepository.save(category);

		return result;
	}

	public void delete(final Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);

		Assert.notNull(this.administratorService.findByPrincipal());
		Assert.isTrue(category.getGames().size() == 0);

		this.categoryRepository.delete(category);
	}

	// Other business methods -------------------------------------------------

}
