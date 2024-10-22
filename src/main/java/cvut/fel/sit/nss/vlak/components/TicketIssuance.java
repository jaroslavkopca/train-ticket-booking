package cvut.fel.sit.nss.vlak.components;

import cvut.fel.sit.nss.vlak.components.Abstract.SimpleSink;
import cvut.fel.sit.nss.vlak.components.Pipes.PricePipe;
import cvut.fel.sit.nss.vlak.model.Ticket;
import cvut.fel.sit.nss.vlak.util.Pair;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class TicketIssuance extends SimpleSink<Pair<HttpSession, List<Ticket>>> {
    private final Map<String, LinkedBlockingQueue<List<Ticket>>> sessionResults = new ConcurrentHashMap<>();

    public TicketIssuance(PricePipe input) {
        super(input);
    }

    @Override
    public void takeFrom(PricePipe pipe) {
        try {
            Pair<HttpSession, List<Ticket>> in;
            while ((in = pipe.nextOrNullIfEmptied()) != null) {
                handle(in);
                delayForDebug(300);
            }
            System.out.println("sink finished");
        } catch (InterruptedException e) {
            System.err.println("interrupted");
            e.printStackTrace();
        } finally {
            System.out.close();
        }
    }

    @Override
    protected void handle(Pair<HttpSession, List<Ticket>> ticketsPair) {
        // Store the results in the map
        String sessionId = ticketsPair.getLeft().getId();
        sessionResults.putIfAbsent(sessionId, new LinkedBlockingQueue<>());
        sessionResults.get(sessionId).add(ticketsPair.getRight());
//        System.out.println("Received tickets for session " + sessionId + ": " + ticketsPair.getRight());
    }

    @Override
    protected void cleanup() {
        // Perform cleanup operations if needed
        System.out.println("FormSink cleanup");
    }

    public List<Ticket> waitForResult(HttpSession session, long timeout, TimeUnit unit) throws InterruptedException {
        String sessionId = session.getId();
        LinkedBlockingQueue<List<Ticket>> queue = sessionResults.get(sessionId);
        if (queue == null) {
            queue = new LinkedBlockingQueue<>();
            sessionResults.put(sessionId, queue);
        }

        List<Ticket> result = queue.poll(timeout, unit);
        if (result != null) {
            sessionResults.remove(sessionId);
        }
        return result;
    }
}
