
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Developer;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {

	@Query("select d from Developer d where d.userAccount.id = ?1")
	Developer findByUserAccountId(int userAccountId);

	//C7: Los desarrolladores que tengan el mayor número de juegos cuyas ventas superen las 1000 unidades
	@Query("select d.id,count(g) from Developer d join d.games g where g.sellsNumber>5 group by g.developer order by g.sellsNumber DESC")
	Collection<Object[]> developersWithBestSellersQuantity();

	//C3: Los desarrolladores con más ventas
	@Query("select distinct d from Developer d where (d.games.size=(select max(d.games.size) from Developer d))")
	Collection<Developer> developerMoreSells();

	//C3: Los desarrolladores con menos ventas
	@Query("select distinct d from Developer d where (d.games.size=(select min(d.games.size) from Developer d))")
	Collection<Developer> developerLessSells();

	//C8: La media de ventas de juegos por desarrollador
	@Query("select sum(g.sellsNumber)/(select count(d) from Developer d)*1.0 from Game g")
	Double avgDeveloperPerSellGames();

	//B2: Los desarrolladores cuyos juegos posean la mejor y peor crítica
	@Query("select r.game, avg(r.score) from Review r where r.draft=false group by r.game order by avg(r.score) DESC")
	List<Object[]> developerWithGameBetterAndWorstReview();

}
