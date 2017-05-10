
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Sense;

@Repository
public interface SenseRepository extends JpaRepository<Sense, Integer> {

}
