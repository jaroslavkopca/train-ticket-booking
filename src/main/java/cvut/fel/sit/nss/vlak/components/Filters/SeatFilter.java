package cvut.fel.sit.nss.vlak.components.Filters;

import cvut.fel.sit.nss.vlak.components.Abstract.SimpleFilter;
import cvut.fel.sit.nss.vlak.components.Pipes.SeatPipe;
import cvut.fel.sit.nss.vlak.components.Pipes.TrainPipe;
import cvut.fel.sit.nss.vlak.dao.SeatDao;
import cvut.fel.sit.nss.vlak.model.Seat;
import cvut.fel.sit.nss.vlak.model.Train;
import cvut.fel.sit.nss.vlak.model.enums.carriageType;
import cvut.fel.sit.nss.vlak.util.Pair;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeatFilter extends SimpleFilter<Pair<HttpSession,List<Train>>, Pair<HttpSession,List<Seat>>> {
    private final SeatDao seatDao;
    @Autowired
    public SeatFilter(TrainPipe input, SeatPipe output, SeatDao seatDao) {
        super(input, output);
        this.seatDao = seatDao;
    }

    @Override
    protected Pair<HttpSession, List<Seat>> transformOne(Pair<HttpSession, List<Train>> trainsPair) {
        HttpSession session = trainsPair.getLeft();
        List<Seat> seats = new ArrayList<>();

        // Check for seat class and seat number attributes
        Boolean isSeatSelected = (Boolean) session.getAttribute("isSeatSelected");
        String selectedSeatClass = (String) session.getAttribute("selectedSeatClass");
        Integer selectedSeatNumber = (Integer) session.getAttribute("selectedSeatNumber");

        if (isSeatSelected == null || !isSeatSelected) {
            // If no seat is selected or attribute does not exist, find default second class seat
            trainsPair.getRight().forEach(train -> {seats.add(seatDao.findSeatByClass(train, carriageType.SecondClass));});
        } else if (selectedSeatClass != null) {
            // If seat class is selected, find based on the class
            trainsPair.getRight().forEach(train -> seats.add(seatDao.findSeatByClass(train, carriageType.valueOf(selectedSeatClass))));
        } else if (selectedSeatNumber != null) {
            // If seat number is selected, find the specific seat
            trainsPair.getRight().forEach(train -> {
                Seat seat = seatDao.findSeatByNumber(train, selectedSeatNumber);
                if (seat != null) {
                    seats.add(seat);
                }
            });
        }

        return new Pair<>(session, seats);
    }

}
