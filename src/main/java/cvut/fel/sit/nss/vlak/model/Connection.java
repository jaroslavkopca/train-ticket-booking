package cvut.fel.sit.nss.vlak.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "train_id", referencedColumnName = "id")
    @JsonBackReference
    private Train train;

    @OneToMany(
            mappedBy = "connection",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ConnectionRoute> connectionRoutes = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "connection_stations",
            joinColumns = @JoinColumn(name = "connection_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id")
    )
    private List<Station> stations = new ArrayList<>();

    public void addConnectionRoute(ConnectionRoute route) {
        connectionRoutes.add(route);
    }

    //GETTERS AND SETTER


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public List<ConnectionRoute> getConnectionRoutes() {
        return connectionRoutes;
    }

    public void setConnectionRoutes(List<ConnectionRoute> connectionRoutes) {
        this.connectionRoutes = connectionRoutes;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}
