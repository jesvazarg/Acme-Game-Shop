
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.MessageEmail;

@Repository
public interface MessageRepository extends JpaRepository<MessageEmail, Integer> {

}
