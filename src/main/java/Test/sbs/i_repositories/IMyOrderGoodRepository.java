package Test.sbs.i_repositories;

import Test.sbs.tables.MyOrderGood;
import Test.sbs.tables.MyOrderGoodKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMyOrderGoodRepository extends JpaRepository<MyOrderGood, MyOrderGoodKey> {
    List<MyOrderGood> findByMyOrderId(Long orderId);
    List<MyOrderGood> findByGoodId(Long goodId);
}
