
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

	/* Encontrar los juegos que tengan una critica publicada por el critico pasado por parametro */
	@Query("select g from Game g join g.reviews r where r.critic.id=?1 and r.draft=false")
	Collection<Game> findGamesWithPublishedReviewsByCritic(int criticId);

	@Query("select g from Game g where g.developer.id=?1")
	Collection<Game> findByCustomerId(int id);

	@Query("select g from Game g where g.age<13")
	Collection<Game> findAllUnderThirteen();

	@Query("select g from Game g where g.age<=?1")
	Collection<Game> findAllByAge(int age);

	//C1: Los juegos que más "me gusta" tiene 
	@Query("select distinct g from Game g join g.senses s where ((s.like=true) and (s.size=(select max(s.size) from Game g join g.senses s where s.like=true)))")
	Collection<Game> gameMoreLikes();

	//Opcional
	@Query("select count(s) from Sense s where s.game.id=?1 and s.like=true")
	Integer gameMoreLikes2(int gameId);

	//C1: Los juegos que menos "me gusta" tiene
	@Query("select distinct g from Game g join g.senses s where ((s.like=false) and (s.size=(select max(s.size) from Game g join g.senses s where s.like=false)))")
	Collection<Game> gameLessLikes();

	//Opcional
	@Query("select count(s) from Sense s where s.game.id=?1 and s.like=false")
	Integer gameLessLikes2(int gameId);

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
	@Query("select g, avg(c.score) from Game g join g.comments c group by g order by avg(c.score) DESC")
	List<Object[]> findGamesOrderByScoreComments();

	//C10: Ratio de "me gusta" vs "no me gusta" por cada juego.

	//C10.1 : "Me gusta"'s de un juego
	@Query("select count(s) *1.0 from Game g join g.senses s where s.like=true and g.id=?1")
	Double likeFromAGame(int gameId);

	//C10.2: "No me gusta"'s de un juego
	@Query("select count(s) *1.0 from Game g join g.senses s where s.like=false and g.id=?1")
	Double dislikeFromAGame(int gameId);
	/* select (select count(s) from Sense s where s.like=true group by s.game)*1.0/count(s1) from Sense s1 where s1.like=false group by s1.game; */

	//B1 Los juegos con mejores y peores críticas.
	@Query("select g, avg(r.score) from Game g join g.reviews r where r.draft=false group by g order by avg(r.score) DESC")
	List<Object[]> findGamesOrderByScoreReviews();

	@Query("select g from Game g where g.title like concat('%', ?1, '%') or g.description like concat('%', ?1, '%')")
	Collection<Game> findByRecipeKeyWord(String key);

	@Query("select g from Game g where g.age<=?1 and (g.title like concat('%', ?2, '%') or g.description like concat('%', ?2, '%'))")
	Collection<Game> findByRecipeKeyWordWithAge(int age, String key);

	@Query("select g from Game g join g.categories cat where cat.name like ?1")
	Collection<Game> findGameByCategoryKeyWord(String key);

	@Query("select g from Game g join g.categories cat where cat.name like ?1 and g.price>=?2 and g.price<=?3")
	Collection<Game> findGameByCategoryKeyWordWithMinPriceAndMaxPrice(String key, Double minPrice, Double maxPrice);

	@Query("select g from Game g join g.categories cat where cat.name like ?1 and g.price>=?2")
	Collection<Game> findGameByCategoryKeyWordWithMinPrice(String key, Double minPrice);

	@Query("select g from Game g join g.categories cat where g.age<=?1 and cat.name like ?2")
	Collection<Game> findGameByCategoryKeyWordWithAge(Integer edadCustomer, String key);

	@Query("select g from Game g join g.categories cat where g.age<=?1 and cat.name like ?2 and g.price>=?3 and g.price<=?4")
	Collection<Game> findGameByCategoryKeyWordWithAgeMinPriceAndMaxPrice(Integer edadCustomer, String key, Double minPrice, Double maxPrice);

	@Query("select g from Game g join g.categories cat where g.age<=?1 and cat.name like ?2 and g.price>=?3")
	Collection<Game> findGameByCategoryKeyWordWithAgeMinPrice(Integer edadCustomer, String key, Double minPrice);

	@Query("select g from Game g where g.price>=?1 and g.price<=?2")
	Collection<Game> findGameByMinPriceAndMaxPrice(Double minPrice, Double maxPrice);

	@Query("select g from Game g where g.price>=?1")
	Collection<Game> findGameByMinPrice(Double minPrice);

	@Query("select g from Game g where g.age<=?1 and g.price>=?2 and g.price<=?3")
	Collection<Game> findGameByAgeMinPriceAndMaxPrice(Integer edadCustomer, Double minPrice, Double maxPrice);

	@Query("select g from Game g where g.age<=?1 and g.price>=?2")
	Collection<Game> findGameByAgeMinPrice(Integer edadCustomer, Double minPrice);

	@Query("select avg(r.score) from Game g join g.reviews r where g.id=?1 and r.draft=false group by g")
	Double avgFromGame(Integer gameid);

}
