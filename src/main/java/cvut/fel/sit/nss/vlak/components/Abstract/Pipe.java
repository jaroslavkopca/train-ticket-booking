package cvut.fel.sit.nss.vlak.components.Abstract;

public interface Pipe<T> {
    public boolean put(T obj);
    public T nextOrNullIfEmptied() throws InterruptedException;
    public void closeForWriting();
}

