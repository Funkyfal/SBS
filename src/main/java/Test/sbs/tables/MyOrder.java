package Test.sbs.tables;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "my_order")
public class MyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "delivery")
    private boolean delivery;
    @Column(name = "date")
    private Date date;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;
    @Column(name = "sum_of_an_order")
    private BigDecimal sumOfAnOrder;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @OneToMany(mappedBy = "myOrder")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    Set<MyOrderGood> myOrderGood;

    @PrePersist
    public void prePersist(){
        date = Calendar.getInstance().getTime();
    }
}
