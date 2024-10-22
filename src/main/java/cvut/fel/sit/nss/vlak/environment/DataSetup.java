package cvut.fel.sit.nss.vlak.environment;

import cvut.fel.sit.nss.vlak.model.*;
import cvut.fel.sit.nss.vlak.model.enums.carriageType;
import cvut.fel.sit.nss.vlak.model.enums.seatType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataSetup {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    public DataSetup(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString() == encodedPassword;
            }
        };
    }

    @Transactional
    public void dataSetup() {
        for (int i = 0; i < 3; i++) {
            Connection connection = createConnection();
            entityManager.persist(connection);
        }
    }


    private Connection createConnection() {
        Connection connection = new Connection();
        Train train = createTrain();
        train.setConnection(connection);
        connection.setTrain(train);

        ConnectionRoute connectionRoute = createConnectionRoute();
        connectionRoute.setConnection(connection);
        connection.addConnectionRoute(connectionRoute);

        List<Station> stations = createStations();

        return connection;
    }

    private Train createTrain() {
        Train train = new Train();
        train.setTrainType(1);
        train.setPower(1000);

        List<Carriage> carriages = createCarriages();
        train.setCarriages(carriages);
        carriages.forEach(carriage -> carriage.setTrain(train));

        return train;
    }

    private List<Carriage> createCarriages() {
        List<Carriage> carriages = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Carriage carriage = new Carriage();
            carriage.setAccessibility(true);
            carriage.setNumber(i + 1);
            carriage.setCarriageType(carriageType.SecondClass);

            List<Seat> seats = createSeats();
            carriage.setSeats(seats);
            seats.forEach(seat -> seat.setCarriage(carriage));

            carriages.add(carriage);
        }
        return carriages;
    }

    private List<Seat> createSeats() {
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Seat seat = new Seat();
            seat.setNumber(i + 1);
            seat.setSeatType(seatType.normal);
            seat.setAvailable(true);
            seats.add(seat);
        }
        return seats;
    }
    private ConnectionRoute createConnectionRoute() {
        ConnectionRoute connectionRoute = new ConnectionRoute();
        Times times = createTimes();

        times.setConnectionRoute(connectionRoute);
        connectionRoute.addTime(times);

        // Ensure origin and destination stations are managed (persistent)
        List<Station> stations = createStations();
        Station originStation = stations.get(0);
        Station destinationStation = stations.get(1);

        // Reattach entities if they are detached
        originStation = entityManager.merge(originStation);
        destinationStation = entityManager.merge(destinationStation);

        times.setOriginStation(originStation);
        times.setDestinationStation(destinationStation);

        return connectionRoute;
    }



    private Times createTimes() {
        // Create Calendar instance and set the departure time for connection1
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.JUNE); // Note: Calendar month is zero-based, so June is 5
        calendar.set(Calendar.DAY_OF_MONTH, 21);
        calendar.set(Calendar.HOUR_OF_DAY, 10); // Set the hour to 8 (24-hour clock)
        calendar.set(Calendar.MINUTE, 0);      // Set the minutes to 0
        calendar.set(Calendar.SECOND, 0);      // Set the seconds to 0
        calendar.set(Calendar.MILLISECOND, 0); // Set the milliseconds to 0
        Date departureTime = calendar.getTime();

        // Increment the calendar by 7 hours for arrival time (8:00 + 7:00 = 15:00)
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date arrivalTime = calendar.getTime();

        // Create Times object and set the properties
        Times times = new Times();
        times.setDepartureTime(departureTime);
        times.setArrivalTime(arrivalTime);

        // Set origin and destination stations based on createStations() method
        List<Station> stations = createStations();
        times.setOriginStation(stations.get(0));
        times.setDestinationStation(stations.get(1));

        return times;
    }


    private List<Station> createStations() {
        List<Station> stations = new ArrayList<>();

        // Creating stations based on the provided JSON example
        Station pragueStation = new Station();
        pragueStation.setId(1);
        pragueStation.setCity("Prague");
        pragueStation.setName("Prague Main Station");
        pragueStation.setPostNumber(11000);
        pragueStation.setState("Czech Republic");
        pragueStation.setStreet("Wilsonova");

        Station brnoStation = new Station();
        brnoStation.setId(2);
        brnoStation.setCity("Brno");
        brnoStation.setName("Brno Main Station");
        brnoStation.setPostNumber(60200);
        brnoStation.setState("Czech Republic");
        brnoStation.setStreet("Nádražní");

        stations.add(pragueStation);
        stations.add(brnoStation);

        return stations;
    }
}
