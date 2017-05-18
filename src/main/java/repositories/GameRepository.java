
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

	@Query("select g from Game g where g.age<13")
	Collection<Game> findAllUnderThirteen();

	@Query("select g from Game g where g.age<=?1")
	Collection<Game> findAllByAge(int age);

	//C1: Los juegos que más "me gusta" tiene 
	@Query("select distinct g from Game g join g.senses s where ((s.like=true) and (s.size=(select max(s.size) from Game g join g.senses s where s.like=true)))")
	Collection<Game> gameMoreLikes();

	//C1: Los juegos que menos "me gusta" tiene
	@Query("select distinct g from Game g join g.senses s where ((s.like=false) and (s.size=(select max(s.size) from Game g join g.senses s where s.like=false)))")
	Collection<Game> gameLessLikes();

	//C4: Los juegos que superen la media de ventas por juego del sistema
	@Query("select g from Game g where ((g.sellsNumber)>=(select avg(g.sellsNumber) from Game g))")
	Collection<Game> gamesMoreThatAVG();

	//C5:Los juegos con mayor número de ventas.
	@Query("select g1 from Game g1 where g1.sellsNumber=(select max(g.sellsNumber) from Game g)")
	Collection<Game> findBestSellerGames();

	//C5:Los juegos con menor número de ventas.
	@Query("select g1 from Game g1 where g1.sellsNumber=(select min(g.sellsNumber) from Game g)")
	Collection<Game> findWorstSellerGames();

	//C6: Los juegos con mayor y menor puntuación en sus comentarios.
	//	@Query("")
	//	Collection<Game> findGamesMostComments();
	//
	//	@Query("")
	//	Collection<Game> findGamesLessComments();

	//C10: Ratio de "me gusta" vs "no me gusta" por cada juego.

	//C10.1 : "Me gusta"'s de un juego
	@Query("select count(s) *1.0 from Game g join g.senses s where s.like=true and g.id=?1")
	Double likeFromAGame(int gameId);

	//C10.2: "No me gusta"'s de un juego
	@Query("select count(s) *1.0 from Game g join g.senses s where s.like=false and g.id=?1")
	Double dislikeFromAGame(int gameId);
	/* select (select count(s) from Sense s where s.like=true group by s.game)*1.0/count(s1) from Sense s1 where s1.like=false group by s1.game; */

	//B1 Los juegos con mejores y peores críticas.
	//	@Query("")
	//	Collection<Game> findGamesMostReviews();
	//
	//	@Query("")
	//	Collection<Game> findGamesLessReviews();

}
