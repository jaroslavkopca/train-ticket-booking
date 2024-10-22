package cvut.fel.sit.nss.vlak.model;

import cvut.fel.sit.nss.vlak.model.enums.ticketStatus;
import jakarta.persistence.*;
import org.springframework.format.number.money.MonetaryAmountFormatter;

import java.util.Date;
import java.util.List;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;
    private Integer price;
    private ticketStatus ticketStatus = cvut.fel.sit.nss.vlak.model.enums.ticketStatus.reserved;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(optional = true)
    private Seat seat;

    public TicketRoute getTicketRoute() {
        return ticketRoute;
    }

    public void setTicketRoute(TicketRoute ticketRoute) {
        this.ticketRoute = ticketRoute;
    }

    @OneToOne(optional = true)
    private TicketRoute ticketRoute

            ;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public cvut.fel.sit.nss.vlak.model.enums.ticketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(cvut.fel.sit.nss.vlak.model.enums.ticketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
    @Override
    public String toString() {
        return String.format(
                "Ticket{id=%d, date=%s, price=%d, ticketStatus=%s, user=%s, seat=%s, ticketRoute=%s}",
                id,
                date != null ? date.toString() : "null",
                price,
                ticketStatus != null ? ticketStatus.name() : "null",
                user != null ? user.getId() : "null",
                seat != null ? seat.getId() : "null",
                ticketRoute != null ? ticketRoute.getId() : "null"
        );
    }
}
