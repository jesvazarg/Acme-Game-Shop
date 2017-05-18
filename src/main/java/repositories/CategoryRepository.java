
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//C9 Las categorías con mayor y menor número de ventas.
	//	@Query("")
	//	Collection<Category> findCategoriesMostSells();
	//
	//	@Query("")
	//	Collection<Category> findCategoriesLessSells();

}
