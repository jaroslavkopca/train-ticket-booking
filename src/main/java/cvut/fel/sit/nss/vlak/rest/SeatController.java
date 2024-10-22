package cvut.fel.sit.nss.vlak.rest;

import cvut.fel.sit.nss.vlak.model.Seat;
import cvut.fel.sit.nss.vlak.model.Train;
import cvut.fel.sit.nss.vlak.model.User;
import cvut.fel.sit.nss.vlak.model.util.SeatDetailsDTO;
import cvut.fel.sit.nss.vlak.security.model.UserDetails;
import cvut.fel.sit.nss.vlak.service.SeatService;
import cvut.fel.sit.nss.vlak.service.TrainService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/seat")
public class SeatController {
    private static final Logger logger = LoggerFactory.getLogger(SeatController.class);
    private final SeatService seatService;
    private final TrainService trainService;

    @Autowired
    public SeatController(SeatService seatService, TrainService trainService) {
        this.seatService = seatService;
        this.trainService = trainService;
    }

    @GetMapping("/train/{trainId}")
    public List<SeatDetailsDTO> getAllSeatsForTrain(HttpSession session, @PathVariable Integer trainId) {
        Train train = trainService.findTrain(trainId);
        return seatService.getAllSeatsForTrain(train);
    }

    @PostMapping("/select")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void selectSeat(HttpSession session, @RequestParam Integer trainId, @RequestParam Integer seatNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            session.setAttribute("user",user);
        }
        Train train = trainService.findTrain(trainId);
        seatService.selectSeat(session, train, seatNumber);
    }


}
