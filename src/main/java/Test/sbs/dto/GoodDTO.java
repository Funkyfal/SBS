package Test.sbs.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodDTO {
    private String name;
    private String shortName;
    private String description;
    private BigDecimal price;
}
