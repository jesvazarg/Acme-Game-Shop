
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageEmail;

@Repository
public interface MessageEmailRepository extends JpaRepository<MessageEmail, Integer> {

	@Query("select m from MessageEmail m where m.sender.id=?1 and m.deletedForSender=false")
	Collection<MessageEmail> findMessageEmailsSentByActorId(int actorId);

	@Query("select m from MessageEmail m where m.recipient.id=?1 and m.deletedForRecipient=false")
	Collection<MessageEmail> findMessageEmailsReceivedByActorId(int actorId);

}
