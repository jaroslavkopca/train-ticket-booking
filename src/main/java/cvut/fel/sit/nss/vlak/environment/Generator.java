package cvut.fel.sit.nss.vlak.environment;

import cvut.fel.sit.nss.vlak.model.*;

import java.util.*;

public class Generator {

    private static final Random RAND = new Random();
    private static final String[] NAMES = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace"};
    private static final String[] SURNAMES = {"Smith", "Johnson", "Brown", "Lee", "Davis", "Taylor"};
    private static final Set<String> usedUsernames = new HashSet<>();

    public static int randomInt() {
        return RAND.nextInt();
    }

    public static int randomInt(int max) {
        return RAND.nextInt(max);
    }

    public static int randomInt(int min, int max) {
        assert min >= 0;
        assert min < max;

        int result;
        do {
            result = randomInt(max);
        } while (result < min);
        return result;
    }

    public static boolean randomBoolean() {
        return RAND.nextBoolean();
    }

    public static User generateUser() {
        final User user = new User();
        user.setName(getRandomElement(NAMES));
        user.setSurname(getRandomElement(SURNAMES));
        user.setUsername(generateUniqueUsername(user.getName(), user.getSurname()));
        return user;
    }

    private static String getRandomElement(String[] array) {
        int randomIndex = new Random().nextInt(array.length);
        return array[randomIndex];
    }

    private static String generateUniqueUsername(String name, String surname) {
        String username = (name.charAt(0) + surname).toLowerCase();
        int count = 1;

        while (usedUsernames.contains(username)) {
            username = (name.charAt(0) + surname + count).toLowerCase();
            count++;
        }

        usedUsernames.add(username);
        return username;
    }

    public static Station generateStation(String name){
        Station station = new Station();
        station.setName(name);
        station.setState("Cesko");
        station.setCity("Prague");
        station.setPostNumber(1012);
        return station;
    }

    public static Connection generateConnection(List<Station> stations, int numberOfCarriages, int seatsPerCarriage) {
        Train train = generateTrain(numberOfCarriages, seatsPerCarriage);
        List<ConnectionRoute> routes = generateConnectionRoutes(stations);
        List<Times> times = generateTimesForRoutes(routes);
        for (ConnectionRoute route : routes) {
            route.setTimes(times);
        }
        Date departureTime = generateRandomTime();
        Date arrivalTime = generateRandomArrivalTime(departureTime, routes.size());

        Connection connection = new Connection();
        connection.setTrain(train);
        connection.setConnectionRoutes(routes);
//        connection.setStations(stations);

        // Assign train to connection
        train.setConnection(connection);

        // Assign connection to routes
        for (ConnectionRoute route : routes) {
            route.setConnection(connection);
        }

        return connection;
    }

    private static Train generateTrain(int numberOfCarriages, int seatsPerCarriage) {
        Train train = new Train();
        List<Carriage> carriages = new ArrayList<>();

        for (int i = 0; i < numberOfCarriages; i++) {
            Carriage carriage = new Carriage();
            List<Seat> seats = new ArrayList<>();

            for (int j = 0; j < seatsPerCarriage; j++) {
                Seat seat = new Seat();
                seats.add(seat);
            }

            carriage.setSeats(seats);
            carriages.add(carriage);
        }

        train.setCarriages(carriages);
        return train;
    }

    private static List<ConnectionRoute> generateConnectionRoutes(List<Station> stations) {
        List<ConnectionRoute> routes = new ArrayList<>();

        for (int i = 0; i < stations.size() - 1; i++) {
            ConnectionRoute route = new ConnectionRoute();
            route.setId(i + 1);
            routes.add(route);
        }

        return routes;
    }

    private static List<Times> generateTimesForRoutes(List<ConnectionRoute> routes) {
        List<Times> timesList = new ArrayList<>();
        Date previousDepartureTime = generateRandomTime();

        for (ConnectionRoute route : routes) {
            Times times = new Times();
            times.setDepartureTime(previousDepartureTime);
            Date arrivalTime = generateRandomArrivalTime(previousDepartureTime, 1);
            times.setArrivalTime(arrivalTime);
            timesList.add(times);
            previousDepartureTime = generateRandomTimeAfter(arrivalTime);
        }

        return timesList;
    }

    private static Date generateRandomTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, RAND.nextInt(24));
        calendar.set(Calendar.MINUTE, RAND.nextInt(60));
        return calendar.getTime();
    }

    private static Date generateRandomArrivalTime(Date departureTime, int numberOfRoutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(departureTime);
        calendar.add(Calendar.HOUR, numberOfRoutes + RAND.nextInt(5)); // Each route takes at least one hour, plus some random time
        return calendar.getTime();
    }

    private static Date generateRandomTimeAfter(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MINUTE, 30 + RAND.nextInt(120)); // Add 30 to 150 minutes
        return calendar.getTime();
    }
}
