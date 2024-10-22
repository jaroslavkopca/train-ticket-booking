package cvut.fel.sit.nss.vlak.components.Pipes;

import cvut.fel.sit.nss.vlak.components.Abstract.Pipe;
import cvut.fel.sit.nss.vlak.model.Connection;
import cvut.fel.sit.nss.vlak.util.Pair;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import java.util.List;
@Component
public class ConnectionPipe implements Pipe<Pair<HttpSession,List<Connection>>> {
    private final Queue<Pair<HttpSession,List<Connection>>> buffer = new LinkedList<>();
    private boolean isOpenForWriting = true;
    private boolean hasReadLastObject = false;

    @Override
    public synchronized boolean put(Pair<HttpSession,List<Connection>> connectionsPair) {
        if (!isOpenForWriting) {
            throw new RuntimeException(new IOException("pipe is closed; cannot write to it"));
        } else if (connectionsPair == null) {
            throw new IllegalArgumentException("cannot put null in pipe; null is reserved for pipe-empty sentinel value");
        }

        boolean wasAdded = buffer.add(connectionsPair);
        notify();
        System.out.println("CONNECTION PIPE:    "+"added to pipe: " + (connectionsPair==null?"<null>":connectionsPair.toString()));
        return wasAdded;
    }

    @Override
    public synchronized Pair<HttpSession,List<Connection>> nextOrNullIfEmptied() throws InterruptedException {
        if (hasReadLastObject) {
            throw new NoSuchElementException("Pipe is closed and empty; will never contain any further values");
        }

        while (buffer.isEmpty() && isOpenForWriting) {
            wait(); // Pipe empty - wait
        }

        Pair<HttpSession,List<Connection>> connection = buffer.poll();
        if (connection == null) {
            hasReadLastObject = true;
        }
        return connection;
    }

    public synchronized Pair<HttpSession,List<Connection>> read(){
        return buffer.peek();
    }

    @Override
    public synchronized void closeForWriting() {
        isOpenForWriting = false;
        buffer.add(null); // Sentinel value to signify end of pipe
        notify();
    }
}
