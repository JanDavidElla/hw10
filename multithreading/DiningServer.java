package multithreading;

public interface DiningServer {
    public void takeForks(int philosopherId);
    public void returnForks(int philosopherId);
}