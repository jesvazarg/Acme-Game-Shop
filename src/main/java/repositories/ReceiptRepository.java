
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

}
