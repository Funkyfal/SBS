package Test.sbs.i_repositories;

import Test.sbs.tables.MyOrder;
import Test.sbs.tables.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMyOrderRepository extends JpaRepository<MyOrder, Long> {
    MyOrder findByStatusAndDelivery(StatusEnum status, boolean Delivery);
}
