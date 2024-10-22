package cvut.fel.sit.nss.vlak.service;

import cvut.fel.sit.nss.vlak.components.Filters.ConnectionFilter;
import cvut.fel.sit.nss.vlak.components.Pipes.ConnectionPipe;
import cvut.fel.sit.nss.vlak.components.Pipes.FormPipe;
import cvut.fel.sit.nss.vlak.components.TicketIssuance;
import cvut.fel.sit.nss.vlak.dao.ConnectionDao;
import cvut.fel.sit.nss.vlak.dao.SeatDao;
import cvut.fel.sit.nss.vlak.model.*;
import cvut.fel.sit.nss.vlak.model.util.ConnectionDetailsDTO;
import cvut.fel.sit.nss.vlak.util.Pair;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final FormPipe formPipe;
    private final ConnectionPipe connectionPipe;
    private final ConnectionFilter connectionFilter;
    private final TicketIssuance ticketIssuance;
    private final SeatDao seatDao;
    private final ConnectionDao connectionDao;

    @Autowired
    public SearchService(FormPipe formPipe, ConnectionPipe connectionPipe, ConnectionFilter connectionFilter, TicketIssuance ticketIssuance, SeatDao seatDao, ConnectionDao connectionDao) {
        this.formPipe = formPipe;
        this.connectionPipe = connectionPipe;
        this.connectionFilter = connectionFilter;
        this.ticketIssuance = ticketIssuance;
        this.seatDao = seatDao;
        this.connectionDao = connectionDao;
    }

    @Transactional
    public List<ConnectionDetailsDTO> searchConnections(Pair<HttpSession, Form> formPair) {
        formPipe.put(formPair);
        List<ConnectionDetailsDTO> connections = new ArrayList<>();
        List<Ticket> tickets = new ArrayList<>();
        ConnectionDetailsDTO connectionDetailsDTO = new ConnectionDetailsDTO();
        try {
            tickets = ticketIssuance.waitForResult(formPair.getLeft(), 100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // DEBUUUUUUUUUUUUUUG
        tickets.forEach(ticket -> System.out.println(ticket.toString()));

            // Find connections based on the tickets
            for (Ticket ticket : tickets) {
                TicketRoute tr = ticket.getTicketRoute();
                connectionDetailsDTO.setArrivalDateTime(tr.getArrivalTime());
                connectionDetailsDTO.setDepartureDateTime(tr.getDepartureTime());
                connectionDetailsDTO.setOriginStation(tr.getOriginStation().getName());
                connectionDetailsDTO.setDestinationStation(tr.getDestinationStation().getName());
                connectionDetailsDTO.setCarriageType(ticket.getSeat().getCarriage().getCarriageType());
                connectionDetailsDTO.setTicketPrice(ticket.getPrice());
                connections.add(connectionDetailsDTO);
            }


            return connections;
        }
    }
