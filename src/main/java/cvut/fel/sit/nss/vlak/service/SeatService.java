package cvut.fel.sit.nss.vlak.service;

import cvut.fel.sit.nss.vlak.components.TicketIssuance;
import cvut.fel.sit.nss.vlak.dao.SeatDao;
import cvut.fel.sit.nss.vlak.model.Seat;
import cvut.fel.sit.nss.vlak.model.Ticket;
import cvut.fel.sit.nss.vlak.model.TicketRoute;
import cvut.fel.sit.nss.vlak.model.Train;
import cvut.fel.sit.nss.vlak.components.Pipes.SeatPipe;
import cvut.fel.sit.nss.vlak.model.util.SeatDetailsDTO;
import cvut.fel.sit.nss.vlak.model.util.TicketDetailsDTO;
import cvut.fel.sit.nss.vlak.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SeatService {
    private final SeatPipe seatPipe;
    private final SeatDao seatDao;
    private final TicketIssuance ticketIssuance;
    @Autowired
    public SeatService(SeatPipe seatPipe, SeatDao seatDao, TicketIssuance ticketIssuance) {
        this.seatPipe = seatPipe;
        this.seatDao = seatDao;
        this.ticketIssuance = ticketIssuance;
    }



    public List<SeatDetailsDTO> getAllSeatsForTrain(Train train) {
        return seatDao.findAllSeatsByTrain(train);
    }
    @Transactional
    public void selectSeat(HttpSession session, Train train, Integer seatNumber) {
        Seat seat = seatDao.findSeatByNumber(train, seatNumber);
        if (seat != null && seat.isAvailable()) {
            seatDao.changeAvailability(seat);

            seatPipe.put(new Pair<>(session, List.of(seat)));
            List<Ticket> tickets = new ArrayList<>();
            try {
                tickets = ticketIssuance.waitForResult(session,100, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (Ticket ticket : tickets) {
                TicketDetailsDTO ticketDetailsDTO = new TicketDetailsDTO();
                TicketRoute tr = ticket.getTicketRoute();
                ticketDetailsDTO.setDate(ticket.getDate());
                ticketDetailsDTO.setPrice(ticket.getPrice());
                ticketDetailsDTO.setTicketStatus(ticket.getTicketStatus());
                ticketDetailsDTO.setUsername(ticket.getUser().getUsername());
                ticketDetailsDTO.setSeatNumber(ticket.getSeat().getNumber());
                ticketDetailsDTO.setCarriageNumber(ticket.getSeat().getCarriage().getNumber());
                ticketDetailsDTO.setOriginStationName(ticket.getTicketRoute().getOriginStation().getName());
                ticketDetailsDTO.setDestinationStationName(ticket.getTicketRoute().getDestinationStation().getName());
                ticketDetailsDTO.setArrivalTime(ticket.getTicketRoute().getArrivalTime());
                ticketDetailsDTO.setDepartureTime(ticket.getTicketRoute().getDepartureTime());
                System.out.println(ticketDetailsDTO);
            }
        } else {
            throw new RuntimeException("Seat is not available");
        }
    }
}
