package cvut.fel.sit.nss.vlak.dao;

import cvut.fel.sit.nss.vlak.model.Seat;
import cvut.fel.sit.nss.vlak.model.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDao extends BaseDao<Ticket>{
    public TicketDao() {
        super(Ticket.class);
    }
}
