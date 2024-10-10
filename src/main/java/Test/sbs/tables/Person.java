package Test.sbs.tables;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "person")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "post_code")
    private int postCode;
}
