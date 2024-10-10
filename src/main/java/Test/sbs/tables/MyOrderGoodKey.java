package Test.sbs.tables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class MyOrderGoodKey implements Serializable {
    @Column(name = "good_id")
    private
    Long goodId;
    @Column(name = "my_order_id")
    private
    Long myOrderId;

    public MyOrderGoodKey(Long goodId, Long myOrderId) {
        this.goodId = goodId;
        this.myOrderId = myOrderId;
    }

    public MyOrderGoodKey() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyOrderGoodKey that = (MyOrderGoodKey) o;
        return Objects.equals(getGoodId(), that.getGoodId()) &&
                Objects.equals(getMyOrderId(), that.getMyOrderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGoodId(), getMyOrderId());
    }
}
