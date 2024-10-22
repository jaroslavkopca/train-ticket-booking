package cvut.fel.sit.nss.vlak.dao;

import cvut.fel.sit.nss.vlak.dao.ConnectionDao;
import cvut.fel.sit.nss.vlak.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static cvut.fel.sit.nss.vlak.environment.Generator.generateConnection;
import static cvut.fel.sit.nss.vlak.environment.Generator.generateStation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest
public class ConnectionDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ConnectionDao connectionDao;

    @Test
    public void testFindByStationAndTime() {
        // Create instances of Station
        Station originStation = generateStation("Origin Station");
        em.persist(originStation);

        Station destinationStation = new Station();
         destinationStation.setName("Destination Station");
        em.persist(destinationStation);

        List<Station> stations = new ArrayList<>();
        stations.add(originStation);
        stations.add(destinationStation);



        em.flush();
        // Create instances of Connection, ConnectionRoute, and Time
        Connection connection1 = new Connection();
        ConnectionRoute route1 = new ConnectionRoute();
        Times time1 = new Times();


        // Set departure time for connection1
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8); // Set the hour to 8 (24-hour clock)
        calendar.set(Calendar.MINUTE, 0);      // Set the minutes to 0
        calendar.set(Calendar.SECOND, 0);      // Set the seconds to 0
        calendar.set(Calendar.MILLISECOND, 0); // Set the milliseconds to 0
        Date departureTime1 = calendar.getTime();
        time1.setDepartureTime(departureTime1);
        time1.setOriginStation(originStation);
        time1.setDestinationStation(destinationStation);
        route1.addTime(time1);
        time1.setConnectionRoute(route1);
        connection1.addConnectionRoute(route1);
        route1.setConnection(connection1);
        em.persist(connection1);

        // Create another connection with a different departure time
        Connection connection2 = new Connection();
        ConnectionRoute route2 = new ConnectionRoute();
        Times time2 = new Times();
        calendar.set(Calendar.HOUR_OF_DAY, 10); // Set the hour to 10 (24-hour clock)
        Date departureTime2 = calendar.getTime();
        time2.setDepartureTime(departureTime2);
        time2.setOriginStation(originStation);
        time2.setDestinationStation(destinationStation);
        route2.addTime(time2);
        connection2.addConnectionRoute(route2);
        em.persist(connection2);

//        final Connection connection = em.find(Connection.class, connection2.getId());

        // Call the findByStationAndTime method with a time between the two connections
        calendar.set(Calendar.HOUR_OF_DAY, 8); // Set the hour to 9 (24-hour clock)
        Date testTime = calendar.getTime();
        List<Connection> connections = connectionDao.findByStationAndTime(originStation, destinationStation, testTime, testTime);

        // Perform assertions based on the expected results
        assertEquals(1, connections.size());
        assertEquals(connection1, connections.get(0));
    }
}
