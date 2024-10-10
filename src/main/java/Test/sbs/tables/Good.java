package Test.sbs.tables;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "good")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @OneToMany(mappedBy = "good")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    Set<MyOrderGood> myOrderGood;
}
