
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Developer;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {

	@Query("select d from Developer d where d.userAccount.id = ?1")
	Developer findByUserAccountId(int userAccountId);

}
