package Test.sbs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    /*private Long id;*/
    private String name;
    private String secondName;
    private String middleName;
    private String city;
    private String address;
    private String phoneNumber;
    private int postcode;
}
