package cvut.fel.sit.nss.vlak.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer amount;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
