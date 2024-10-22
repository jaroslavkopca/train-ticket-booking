package cvut.fel.sit.nss.vlak.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cvut.fel.sit.nss.vlak.model.enums.carriageType;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Carriage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean accessibility;
    private Integer number;
    @Enumerated(EnumType.STRING)
    private carriageType carriageType = cvut.fel.sit.nss.vlak.model.enums.carriageType.SecondClass;


    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @OneToMany(mappedBy = "carriage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;


    //GETTERY A SETTERY


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAccessibility() {
        return accessibility;
    }

    public void setAccessibility(boolean accessibility) {
        this.accessibility = accessibility;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public cvut.fel.sit.nss.vlak.model.enums.carriageType getCarriageType() {
        return carriageType;
    }

    public void setCarriageType(cvut.fel.sit.nss.vlak.model.enums.carriageType carriageType) {
        this.carriageType = carriageType;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
