
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

	@Query("select d from Discount d where d.code=?1")
	Discount getDiscountWithCode(String code);

	//A3 El mínimo, media y máximo de descuento de los cupones.
	@Query("select max(d.percentage), avg(d.percentage), min(d.percentage) from Discount d")
	Double[] MaxAvgMinPercentagePerDiscount();

}
