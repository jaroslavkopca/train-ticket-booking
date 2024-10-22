package cvut.fel.sit.nss.vlak.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Times {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date arrivalTime;
    private Date departureTime;

    @ManyToOne
    @JoinColumn(name = "originStation_id")

    private Station originStation;


    @ManyToOne
    @JoinColumn(name = "destinationStation_id")
    private Station destinationStation;

    @ManyToOne
    @JoinColumn(name = "connection_route_id")
    @JsonBackReference
    private ConnectionRoute connectionRoute;

//    GETTERS AND SETTERS


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Station getOriginStation() {
        return originStation;
    }

    public void setOriginStation(Station originStation) {
        this.originStation = originStation;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(Station destinationStation) {
        this.destinationStation = destinationStation;
    }

    public ConnectionRoute getConnectionRoute() {
        return connectionRoute;
    }

    public void setConnectionRoute(ConnectionRoute connectionRoute) {
        this.connectionRoute = connectionRoute;
    }
}
