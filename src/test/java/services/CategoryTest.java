
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Category;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CategoryTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private CategoryService	categoryService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	// Un actor autenticado como administrador debe ser capaz de:
	// Administrar las categorías, lo que incluye crear, listar, editar y borrar.
	// Una categoría que tenga asociado un juego no puede ser borrada o editada del sistema.

	//Registrar una nueva categoría
	@Test
	public void driverRegisterCategory() {
		final Object testingData[][] = {
			{
				"admin", "Erótica", null
			}, {
				"admin", "Indie", null
			}, {
				"critic1", "Titulo4", IllegalArgumentException.class
			}, {
				"admin", "", ConstraintViolationException.class
			}, {
				"admin", "strategy", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registerCategory((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void registerCategory(final String loged, final String name, final Class<?> expected) {

		Category category = null;
		Class<?> caught = null;

		try {
			this.authenticate(loged);

			category = this.categoryService.create();

			category.setName(name);

			category = this.categoryService.save(category);
			this.categoryService.findAll();
			this.categoryService.findOne(category.getId());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Editar una categoría
	@Test
	public void driverEditCategory() {
		final Object testingData[][] = {
			{
				"admin", 89, "Erótica", null
			}, {
				"admin", 89, "Indie", null
			}, {
				"critic1", 89, "Terror", IllegalArgumentException.class
			}, {
				"admin", 89, "", ConstraintViolationException.class
			}, {
				"admin", 90, "Aventura", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editCategory((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void editCategory(final String loged, final int categoryId, final String name, final Class<?> expected) {

		Category category = null;
		Class<?> caught = null;

		try {
			this.authenticate(loged);

			category = this.categoryService.findOne(categoryId);

			category.setName(name);

			category = this.categoryService.save(category);
			this.categoryService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//Borrar una categoría
	@Test
	public void driverBorrarCategory() {
		final Object testingData[][] = {
			{
				"admin", 89, null
			}, {
				"admin", 90, IllegalArgumentException.class
			}, {
				"critic1", 89, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.borrarCategory((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void borrarCategory(final String loged, final int categoryId, final Class<?> expected) {

		Category category = null;
		Class<?> caught = null;

		try {
			this.authenticate(loged);

			category = this.categoryService.findOne(categoryId);

			this.categoryService.delete(category);
			this.categoryService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
