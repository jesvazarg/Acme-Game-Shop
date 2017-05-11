
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int userAccountId);

	//A1: El mínimo, media y máximo de número de mensajes enviados por actor
	@Query("select min(a.sentMessages.size), avg(a.sentMessages.size), max(a.sentMessages.size) from Actor a")
	Double[] minAvgMaxMessagesSent();

	//A2: El mínimo, media y máximo de número de mensajes recibidos por actor
	@Query("select min(a.receivedMessages.size), avg(a.receivedMessages.size), max(a.receivedMessages.size) from Actor a")
	Double[] minAvgMaxMessagesReceived();

}
