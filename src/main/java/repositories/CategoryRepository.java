
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//C9 Las categorías con mayor y menor número de ventas.
	@Query("select c, sum(g.sellsNumber) from Category c join c.games g group by c order by sum(g.sellsNumber) DESC")
	List<Object[]> findCategoryOrderBySellsNumber();

}
