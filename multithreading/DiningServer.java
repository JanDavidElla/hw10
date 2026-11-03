package multithreading;

public interface DiningServer {
    public void takeForks(int philNumber);
    public void returnForks(int philNumber);
}