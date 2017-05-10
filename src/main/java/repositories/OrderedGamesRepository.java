
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.OrderedGames;

@Repository
public interface OrderedGamesRepository extends JpaRepository<OrderedGames, Integer> {

}
