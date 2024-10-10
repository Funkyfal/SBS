package Test.sbs.tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "my_order_good")
public class MyOrderGood {
    @EmbeddedId
    private
    MyOrderGoodKey id;
    @ManyToOne
    @MapsId("myOrderId")
    @JoinColumn(name = "my_order_id")
    private
    MyOrder myOrder;
    @ManyToOne
    @MapsId("goodId")
    @JoinColumn(name = "good_id")
    private
    Good good;
    private int quantity;

    public MyOrderGood(){

    }
}
