public class SharedResource {
    private int counter = 0;

    public synchronized void incrementCounter() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }
}