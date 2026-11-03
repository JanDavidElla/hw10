public class Worker implements Runnable {
    private SharedResource sr;

    public Worker(SharedResource sr) {
        this.sr = sr;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            sr.incrementCounter();
        }
    }
}