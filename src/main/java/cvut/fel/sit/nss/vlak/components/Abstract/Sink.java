package cvut.fel.sit.nss.vlak.components.Abstract;

public abstract class Sink<T> extends ThreadedRunner {
    protected Pipe<T> input;

    public Sink(Pipe<T> input) {
        this.input = input;
        this.start();
    }

    @Override
    public void run() {
        takeFrom(input);
    }

    public abstract void takeFrom(Pipe<T> pipe);
}
