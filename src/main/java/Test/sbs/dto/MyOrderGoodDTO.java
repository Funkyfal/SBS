package Test.sbs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyOrderGoodDTO {
    private int quantity;
    private Long myOrderId;
    private Long goodId;
}
