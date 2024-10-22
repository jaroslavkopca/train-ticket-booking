package cvut.fel.sit.nss.vlak.model;

import jakarta.persistence.*;
import cvut.fel.sit.nss.vlak.model.enums.*;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer number;
    @Enumerated(EnumType.STRING)
    private seatType seatType = cvut.fel.sit.nss.vlak.model.enums.seatType.normal;

    @ManyToOne
    @JoinColumn(name = "carriage_id")
    private Carriage carriage;

    @OneToOne(mappedBy = "seat")
    private Ticket ticket;

    private boolean available;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public cvut.fel.sit.nss.vlak.model.enums.seatType getSeatType() {
        return seatType;
    }

    public void setSeatType(cvut.fel.sit.nss.vlak.model.enums.seatType seatType) {
        this.seatType = seatType;
    }

    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
