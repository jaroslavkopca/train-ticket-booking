package cvut.fel.sit.nss.vlak.dao;

import cvut.fel.sit.nss.vlak.model.Seat;
import cvut.fel.sit.nss.vlak.model.Train;
import cvut.fel.sit.nss.vlak.model.enums.carriageType;
import cvut.fel.sit.nss.vlak.model.util.SeatDetailsDTO;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SeatDao extends BaseDao<Seat> {
    public SeatDao() {
        super(Seat.class);
    }

    public Seat findSeatsByTrain(Train train) {
        TypedQuery<Seat> query = em.createQuery(
                "SELECT s FROM Seat s WHERE s.carriage.train = :train AND s.available = true", Seat.class);
        query.setParameter("train", train);
        return query.getResultList().get(0);
    }

    public List<SeatDetailsDTO> findAllSeatsByTrain(Train train) {
        List<SeatDetailsDTO> seatDetailsDTOList = new ArrayList<>();
        TypedQuery<Seat> query = em.createQuery(
                "SELECT s FROM Seat s WHERE s.carriage.train = :train", Seat.class);
        query.setParameter("train", train);
        List<Seat> seats = query.getResultList();

        seats.forEach(seat -> {
            SeatDetailsDTO seatDetailsDTO = new SeatDetailsDTO();
            seatDetailsDTO.setNumber(seat.getNumber());
            seatDetailsDTO.setSeatType(seat.getSeatType());
            seatDetailsDTO.setTrainType(seat.getCarriage().getTrain().getTrainType());
            seatDetailsDTO.setCarriageType(seat.getCarriage().getCarriageType());
            seatDetailsDTO.setConnectionNumber(seat.getCarriage().getTrain().getConnection().getId());
            seatDetailsDTO.setAvailable(seat.isAvailable());
            seatDetailsDTOList.add(seatDetailsDTO);
        });

        return seatDetailsDTOList;
    }


    public Seat findSeatByClass(Train train, carriageType carriageType) {
        TypedQuery<Seat> query = em.createQuery(
                "SELECT s FROM Seat s WHERE s.carriage.train = :train AND s.carriage.carriageType = :carriageType AND s.available = true", Seat.class);
        query.setParameter("train", train);
        query.setParameter("carriageType", carriageType);
        List<Seat> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0); // Return the first seat found or null if none found
    }

    public Seat findSeatByNumber(Train train, Integer seatNumber) {
        TypedQuery<Seat> query = em.createQuery(
                "SELECT s FROM Seat s WHERE s.carriage.train = :train AND s.number = :seatNumber", Seat.class);
        query.setParameter("train", train);
        query.setParameter("seatNumber", seatNumber);
        List<Seat> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0); // Return the first seat found or null if none found
    }

    public void changeAvailability(Seat seat) {
        seat.setAvailable(false);
        em.merge(seat);
    }
}
