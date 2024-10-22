package cvut.fel.sit.nss.vlak.components.Pipes;

import cvut.fel.sit.nss.vlak.components.Abstract.Pipe;
import cvut.fel.sit.nss.vlak.model.Train;
import cvut.fel.sit.nss.vlak.util.Pair;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.List;

@Component
public class TrainPipe implements Pipe<Pair<HttpSession,List<Train>>> {
    private final Queue<Pair<HttpSession,List<Train>>> buffer = new LinkedList<>();
    private boolean isOpenForWriting = true;
    private boolean hasReadLastObject = false;

    @Override
    public synchronized boolean put(Pair<HttpSession,List<Train>> trainsPair) {
        if (!isOpenForWriting) {
            throw new RuntimeException(new IOException("pipe is closed; cannot write to it"));
        } else if (trainsPair == null) {
            throw new IllegalArgumentException("cannot put null in pipe; null is reserved for pipe-empty sentinel value");
        }

        boolean wasAdded = buffer.add(trainsPair);
        notify();
        System.out.println("TRAIN PIPE:          "+"added to pipe: " + (trainsPair==null?"<null>":trainsPair.toString()));
        return wasAdded;
    }

    @Override
    public synchronized Pair<HttpSession,List<Train>> nextOrNullIfEmptied() throws InterruptedException {
        if (hasReadLastObject) {
            throw new NoSuchElementException("Pipe is closed and empty; will never contain any further values");
        }

        while (buffer.isEmpty() && isOpenForWriting) {
            wait(); // Pipe empty - wait
        }

        Pair<HttpSession,List<Train>> trainsPair = buffer.poll();
        if (trainsPair == null) {
            hasReadLastObject = true;
        }
        return trainsPair;
    }

    @Override
    public synchronized void closeForWriting() {
        isOpenForWriting = false;
        buffer.add(null); // Sentinel value to signify end of pipe
        notify();
    }
}
