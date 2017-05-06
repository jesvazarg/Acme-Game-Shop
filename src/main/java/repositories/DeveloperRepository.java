
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Developer;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {

	@Query("select d from Developer d where d.userAccount.id = ?1")
	Developer findByUserAccountId(int userAccountId);

	//C7: Los desarrolladores que tengan el mayor número de juegos cuyas ventas superen las 1000 unidades
	@Query("select d,count(g) from Developer d join d.games g where g.sellsNumber>1000 group by g.developer order by g.sellsNumber DESC")
	Collection<Object[]> developersWithBestSellersQuantity();
}
