package Test.sbs.dto;

import Test.sbs.tables.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyOrderDTO {
    private Long id;
    private boolean delivery;
    private Date date;
    private StatusEnum status;
    private BigDecimal sumOfAnOrder;
    private Long personId;
    public MyOrderDTO(boolean delivery, StatusEnum status, Long personId){
        this.delivery = delivery;
        this.personId = personId;
        this.status = status;
    }
}
