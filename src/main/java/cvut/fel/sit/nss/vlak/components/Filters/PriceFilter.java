package cvut.fel.sit.nss.vlak.components.Filters;

import cvut.fel.sit.nss.vlak.components.Abstract.SimpleFilter;
import cvut.fel.sit.nss.vlak.components.Pipes.PricePipe;
import cvut.fel.sit.nss.vlak.components.Pipes.SeatPipe;
import cvut.fel.sit.nss.vlak.dao.TicketDao;
import cvut.fel.sit.nss.vlak.model.*;
import cvut.fel.sit.nss.vlak.model.enums.ticketStatus;
import cvut.fel.sit.nss.vlak.util.Pair;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class PriceFilter extends SimpleFilter<Pair<HttpSession,List<Seat>>, Pair<HttpSession,List<Ticket>>> {
    private final TicketDao ticketDao;
    @Autowired
    public PriceFilter(SeatPipe input, PricePipe output, TicketDao ticketDao) {
        super(input, output);
        this.ticketDao = ticketDao;
    }

    @Override
    protected Pair<HttpSession, List<Ticket>> transformOne(Pair<HttpSession, List<Seat>> seatsPair) {
        HttpSession session = seatsPair.getLeft();
        List<Seat> seats = seatsPair.getRight();
        List<Ticket> tickets = new ArrayList<>();

        User user = (User) session.getAttribute("user");
        Station originStation = (Station) session.getAttribute("originStation");
        Station destinationStation = (Station) session.getAttribute("destinationStation");
        Date arrivalTime = (Date) session.getAttribute("arrivalTime");
        Date departureTimeFromOrigin = (Date) session.getAttribute("departureTime");

        for (Seat seat : seats) {
            Ticket ticket = new Ticket();
            ticket.setPrice(calculatePrice(seat, user));  // Implement the logic for price calculation
            ticket.setTicketStatus(ticketStatus.reserved);
            ticket.setUser(user);
            ticket.setSeat(seat);

            // Optionally create and set TicketRoute
            TicketRoute ticketRoute = new TicketRoute();
            ticketRoute.setOriginStation(originStation);
            ticketRoute.setDestinationStation(destinationStation);
            ticketRoute.setDistance(calculateDistance(originStation, destinationStation)); // Implement the logic for distance calculation
            ticketRoute.setPayFee(calculatePayFee(ticketRoute));  // Implement the logic for pay fee calculation
            ticketRoute.setArrivalTime(arrivalTime);
            ticketRoute.setDepartureTime(departureTimeFromOrigin);
            ticket.setTicketRoute(ticketRoute);

            tickets.add(ticket);
        }

        return new Pair<>(session, tickets);
    }

    private int calculatePrice(Seat seat, User user) {
        // Implement logic to calculate price based on seat type, carriage type, user type, etc.
        return 100;  // Placeholder value
    }

    private int calculateDistance(Station origin, Station destination) {
        // Implement logic to calculate distance between stations
        return 50;  // Placeholder value
    }

    private int calculatePayFee(TicketRoute route) {
        // Implement logic to calculate pay fee based on distance, etc.
        return 10;  // Placeholder value
    }
}
