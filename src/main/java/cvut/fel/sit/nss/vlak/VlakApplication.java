package cvut.fel.sit.nss.vlak;

import cvut.fel.sit.nss.vlak.components.Filters.ConnectionFilter;
import cvut.fel.sit.nss.vlak.components.Filters.PriceFilter;
import cvut.fel.sit.nss.vlak.components.Filters.SeatFilter;
import cvut.fel.sit.nss.vlak.components.Filters.TrainFilter;
import cvut.fel.sit.nss.vlak.components.Pipes.*;
import cvut.fel.sit.nss.vlak.components.TicketIssuance;
import cvut.fel.sit.nss.vlak.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;

import static cvut.fel.sit.nss.vlak.environment.Generator.generateStation;

@SpringBootApplication
public class VlakApplication {

    public static void main(String[] args) {


        SpringApplication.run(VlakApplication.class, args);
        //ALL PIPES

//
//        FormPipe formPipe = new FormPipe();
//        ConnectionPipe connectionPipe = new ConnectionPipe();
//        TrainPipe trainPipe = new TrainPipe();
//        SeatPipe seatPipe = new SeatPipe();
//        PricePipe pricePipe = new PricePipe();
//
//        //ALL FILTERS
//        ConnectionFilter connectionFilter = new ConnectionFilter(formPipe, connectionPipe);
//        TrainFilter trainFilter = new TrainFilter(connectionPipe, trainPipe);
//        SeatFilter seatFilter = new SeatFilter(trainPipe,seatPipe);
//        PriceFilter priceFilter = new PriceFilter(seatPipe,pricePipe);
//
//        //SINK
//        TicketIssuance ticketIssuance = new TicketIssuance(pricePipe);
//
//        // Start threads for concurrent processing
//        ticketIssuance.start();
//        connectionFilter.start();
//        trainFilter.start();
//        seatFilter.start();
//        priceFilter.start();
//
//
//
//
//
//        //STATIONS
//        Station originStation = generateStation("Origin Station");
//        Station destinationStation = new Station();
//        destinationStation.setName("Destination Station");
//
//
//
//        Connection connection1 = new Connection();
//        ConnectionRoute route1 = new ConnectionRoute();
//        Times time1 = new Times();
//
//
//        // Set departure time for connection1
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 8); // Set the hour to 8 (24-hour clock)
//        calendar.set(Calendar.MINUTE, 0);      // Set the minutes to 0
//        calendar.set(Calendar.SECOND, 0);      // Set the seconds to 0
//        calendar.set(Calendar.MILLISECOND, 0); // Set the milliseconds to 0
//        Date departureTime1 = calendar.getTime();
//        time1.setDepartureTime(departureTime1);
//        time1.setOriginStation(originStation);
//        time1.setDestinationStation(destinationStation);
//        route1.addTime(time1);
//        time1.setConnectionRoute(route1);
//        connection1.addConnectionRoute(route1);
//        route1.setConnection(connection1);
//
//        // Create another connection with a different departure time
//        Connection connection2 = new Connection();
//        ConnectionRoute route2 = new ConnectionRoute();
//        Times time2 = new Times();
//        calendar.set(Calendar.HOUR_OF_DAY, 10); // Set the hour to 10 (24-hour clock)
//        Date departureTime2 = calendar.getTime();
//        time2.setDepartureTime(departureTime2);
//        time2.setOriginStation(originStation);
//        time2.setDestinationStation(destinationStation);
//        route2.addTime(time2);
//        connection2.addConnectionRoute(route2);
//
//        // Example: Simulate sending form data
//        Form formData = new Form(originStation, destinationStation, calendar.getTime());
//        formPipe.put(formData);
    }

}
