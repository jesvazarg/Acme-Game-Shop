
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import domain.Actor;
import domain.Category;
import domain.Developer;
import domain.Game;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CategoryRepository		categoryRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private DeveloperService		developerService;

	@Autowired
	private GameService				gameService;

	@Autowired
	private ActorService			actorService;


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
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue((this.actorService.checkAuthority(principal, Authority.ADMIN)) || (this.actorService.checkAuthority(principal, Authority.DEVELOPER)));
		if (this.actorService.checkAuthority(principal, Authority.ADMIN))
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

	//Este método no realiza un save por lo que se tendrá que hacer en el controlador
	//Además se deberá realizar otro save para cada categoría
	//No se realiza por si se añade una categoria al crear un juego
	public Game addCategoryWoS(final Category category, final Game game) {
		Collection<Category> categories;

		Assert.notNull(category);
		Assert.notNull(game);
		Assert.notNull(this.developerService.findByPrincipal());
		Assert.isTrue(!game.getCategories().contains(category));

		categories = game.getCategories();
		categories.add(category);
		game.setCategories(categories);

		return game;
	}

	//Leer las notaciones del método addCategoryWoS
	public Game deleteCategoryWoS(final Category category, final Game game) {
		Collection<Category> categories;

		Assert.notNull(category);
		Assert.notNull(game);
		Assert.notNull(this.developerService.findByPrincipal());
		Assert.isTrue(game.getCategories().contains(category));

		categories = game.getCategories();
		categories.remove(category);
		game.setCategories(categories);

		return game;
	}

	public void addCategory(final Category category, final Game game) {
		Collection<Category> categories;
		Collection<Game> games;

		Assert.notNull(category);
		Assert.notNull(game);
		Assert.notNull(this.developerService.findByPrincipal());
		Assert.isTrue(!game.getCategories().contains(category));

		categories = game.getCategories();
		categories.add(category);
		game.setCategories(categories);
		this.gameService.save(game);

		Assert.isTrue(!category.getGames().contains(game));

		games = category.getGames();
		games.add(game);
		category.setGames(games);
		this.save(category);
	}

	public void deleteCategory(final Category category, final Game game) {
		Collection<Category> categories;
		Collection<Game> games;

		Assert.notNull(category);
		Assert.notNull(game);
		Assert.notNull(this.developerService.findByPrincipal());
		Assert.isTrue(game.getCategories().contains(category));

		categories = game.getCategories();
		categories.remove(category);
		game.setCategories(categories);
		this.gameService.save(game);

		Assert.isTrue(category.getGames().contains(game));

		games = category.getGames();
		games.remove(game);
		category.setGames(games);
		this.save(category);
	}

	public void deleteGame(final Category category, final Game game) {
		Assert.notNull(category);
		Assert.notNull(game);
		category.getGames().remove(game);
		this.categoryRepository.save(category);
	}

	public Collection<Category> select(final Collection<Category> categories, final Game game) {
		Assert.notNull(game);

		Developer developer;
		developer = this.developerService.findByPrincipal();
		Assert.notNull(developer);

		final Collection<Category> allCategories = this.categoryRepository.findAll();
		Collection<Game> games;
		for (final Category c : allCategories) {
			games = c.getGames();
			if (games.contains(game)) {
				games.remove(game);
				c.setGames(games);
				this.categoryRepository.save(c);
			}
		}

		for (Category category : categories) {
			Assert.notNull(category);
			category.addGame(game);
			category = this.categoryRepository.save(category);
		}

		return categories;
	}

	public List<Category> findCategoryOrderBySellsNumber() {
		List<Category> lista;
		final List<Category> res = new ArrayList<Category>();

		lista = this.categoryRepository.findCategoryOrderBySellsNumber();
		if (!lista.isEmpty()) {
			res.add(lista.get(0));
			res.add(lista.get(lista.size() - 1));
		}

		return res;
	}

}
