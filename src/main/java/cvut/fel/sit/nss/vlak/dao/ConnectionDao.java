package cvut.fel.sit.nss.vlak.dao;

import cvut.fel.sit.nss.vlak.environment.DataSetup;
import cvut.fel.sit.nss.vlak.model.Connection;
import cvut.fel.sit.nss.vlak.model.Station;
import cvut.fel.sit.nss.vlak.model.Train;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;

@Repository
public class ConnectionDao extends BaseDao<Connection> {

    public ConnectionDao() {
        super(Connection.class);
    }

    public List<Connection> findByStationAndTime(Station originStation, Station destinationStation, Date departureTime, Date departureTimeEnd) {
        TypedQuery<Connection> query = em.createQuery(
                "SELECT c FROM Connection c JOIN c.connectionRoutes r JOIN r.times t " +
                        "WHERE t.originStation = :originStation " +
                        "AND t.destinationStation = :destinationStation " +
                        "AND t.departureTime >= :departureTimeStart " +
                        "AND t.departureTime < :departureTimeEnd", Connection.class);
        query.setParameter("originStation", originStation);
        query.setParameter("destinationStation", destinationStation);

        // Create a time range for the query
        query.setParameter("departureTimeStart", departureTime);
        query.setParameter("departureTimeEnd", new Date(departureTime.getTime() + 3600 * 1000)); // 1 hour window

        return query.getResultList();
    }

    public void dataSetup(){
        DataSetup dataSetup = new DataSetup(em);
        dataSetup.dataSetup();
    }

    public Connection findByTrain(Train train) {
        TypedQuery<Connection> query = em.createQuery(
                "SELECT c FROM Connection c WHERE c.train = :train", Connection.class);
        query.setParameter("train", train);
        List<Connection> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }


    public Date findArrivalTime(Integer connectionId, Integer stationId, LocalDate dayOfDeparture) {
        TypedQuery<Date> query = em.createQuery(
                "SELECT t.arrivalTime FROM Connection c " +
                        "JOIN c.connectionRoutes r " +
                        "JOIN r.times t " +
                        "WHERE c.id = :connectionId " +
                        "AND t.destinationStation.id = :stationId " +
                        "AND FUNCTION('DATE', t.departureTime) = :dayOfDeparture", Date.class);

        query.setParameter("connectionId", connectionId);
        query.setParameter("stationId", stationId);
        query.setParameter("dayOfDeparture", java.sql.Date.valueOf(dayOfDeparture));

        List<Date> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public Date findDepartureTime(Integer connectionId, Integer stationId, LocalDate dayOfDeparture) {
        TypedQuery<Date> query = em.createQuery(
                "SELECT t.departureTime FROM Connection c " +
                        "JOIN c.connectionRoutes r " +
                        "JOIN r.times t " +
                        "WHERE c.id = :connectionId " +
                        "AND t.originStation.id = :stationId " +
                        "AND FUNCTION('DATE', t.departureTime) = :dayOfDeparture", Date.class);

        query.setParameter("connectionId", connectionId);
        query.setParameter("stationId", stationId);
        query.setParameter("dayOfDeparture", java.sql.Date.valueOf(dayOfDeparture));

        List<Date> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }


}
