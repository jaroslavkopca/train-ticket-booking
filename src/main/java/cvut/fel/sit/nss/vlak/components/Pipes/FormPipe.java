package cvut.fel.sit.nss.vlak.components.Pipes;

import cvut.fel.sit.nss.vlak.components.Abstract.Pipe;
import cvut.fel.sit.nss.vlak.model.Form;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import cvut.fel.sit.nss.vlak.util.Pair;

@Component
public class FormPipe implements Pipe<Pair<HttpSession,Form>> {
    private final Queue<Pair<HttpSession,Form>> buffer = new LinkedList<>();
    private boolean isOpenForWriting = true;
    private boolean hasReadLastObject = false;

    @Override
    public synchronized boolean put(Pair<HttpSession,Form> formPair) {
        if (!isOpenForWriting) {
            throw new RuntimeException(new IOException("pipe is closed; cannot write to it"));
        } else if (formPair == null) {
            throw new IllegalArgumentException("cannot put null in pipe; null is reserved for pipe-empty sentinel value");
        }

        boolean wasAdded = buffer.add(formPair);
        notify();
        System.out.println("FORM PIPE:          "+"added to pipe: " + (formPair==null?"<null>":formPair.toString()));
        return wasAdded;
    }

    @Override
    public synchronized Pair<HttpSession,Form> nextOrNullIfEmptied() throws InterruptedException {
        if (hasReadLastObject) {
            throw new NoSuchElementException("Pipe is closed and empty; will never contain any further values");
        }

        while (buffer.isEmpty() && isOpenForWriting) {
            wait(); // Pipe empty - wait
        }

        Pair<HttpSession,Form> formPair = buffer.poll();
        if (formPair == null) {
            hasReadLastObject = true;
        }
        return formPair;
    }

    @Override
    public synchronized void closeForWriting() {
        isOpenForWriting = false;
        buffer.add(null); // Sentinel value to signify end of pipe
        notify();
    }
}
