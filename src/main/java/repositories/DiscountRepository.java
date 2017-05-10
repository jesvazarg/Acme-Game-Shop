
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

}
