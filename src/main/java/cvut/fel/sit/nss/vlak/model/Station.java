package cvut.fel.sit.nss.vlak.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String city;
    private String name;
    private Integer postNumber;
    private String state;
    private String street;


    @OneToMany(mappedBy = "originStation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)

    private List<Times> originTimes;


    @OneToMany(mappedBy = "destinationStation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)

    private List<Times> departureTimes;


    @OneToMany(mappedBy = "originStation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TicketRoute> originTicketRoute;


    @OneToMany(mappedBy = "destinationStation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TicketRoute> departureTicketRoute;


    @ManyToMany(mappedBy = "stations", fetch = FetchType.EAGER)
    private List<Connection> connections;

    // Default constructor for JSON deserialization
    public Station() {}

//    GETTERS AND SETTERS


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(Integer postNumber) {
        this.postNumber = postNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<Times> getOriginTimes() {
        return originTimes;
    }

    public void setOriginTimes(List<Times> originTimes) {
        this.originTimes = originTimes;
    }

    public List<Times> getDepartureTimes() {
        return departureTimes;
    }

    public void setDepartureTimes(List<Times> departureTimes) {
        this.departureTimes = departureTimes;
    }

    public List<TicketRoute> getOriginTicketRoute() {
        return originTicketRoute;
    }

    public void setOriginTicketRoute(List<TicketRoute> originTicketRoute) {
        this.originTicketRoute = originTicketRoute;
    }

    public List<TicketRoute> getDepartureTicketRoute() {
        return departureTicketRoute;
    }

    public void setDepartureTicketRoute(List<TicketRoute> departureTicketRoute) {
        this.departureTicketRoute = departureTicketRoute;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
