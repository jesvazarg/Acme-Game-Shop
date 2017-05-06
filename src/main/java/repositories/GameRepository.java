
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

	@Query("select g from Game g where g.developer.id=?1")
	Collection<Game> findByCustomerId(int id);

	//C5:Los juegos con mayor número de ventas.
	@Query("select g1 from Game g1 where g1.sellsNumber=(select max(g.sellsNumber) from Game g)")
	Collection<Game> findBestSellerGames();

	//C5:Los juegos con menor número de ventas.
	@Query("select g1 from Game g1 where g1.sellsNumber=(select min(g.sellsNumber) from Game g)")
	Collection<Game> findWorstSellerGames();

	//C10: Ratio de "me gusta" vs "no me gusta" por cada juego.

	//C10.1 : "Me gusta"'s de un juego
	@Query("select count(s) *1.0 from Game g join g.senses s where s.like=true and g.id=?1")
	Double likeFromAGame(int gameId);

	//C10.2: "No me gusta"'s de un juego
	@Query("select count(s) *1.0 from Game g join g.senses s where s.like=false and g.id=?1")
	Double dislikeFromAGame(int gameId);
	/* select (select count(s) from Sense s where s.like=true group by s.game)*1.0/count(s1) from Sense s1 where s1.like=false group by s1.game; */
}
