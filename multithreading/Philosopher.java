package multithreading;

public class Philosopher implements Runnable{
    private DiningServer server;
    private int philNum;

    public Philosopher(DiningServer server, int philNum) {
        this.server = server;
        this.philNum = philNum;
    }
    
    private void thinking() {
        SleepUtilities.nap();
    }

    private void eating() {
        SleepUtilities.nap();
    }

    public void run() {
        while(true) {
            System.out.println("Philosopher " + philNum + " is thinking.");
            thinking();

            System.out.println("Philosopher " + philNum + " is hungry and waiting to pick up both forks.");

            server.takeForks(philNum);

            System.out.println("Philosopher " + philNum + " has picked up both forks and is eating.");

            eating();

            System.out.println("Philosopher " + philNum + " has finished eating and is returning both forks.");

            server.returnForks(philNum);

            System.out.println("Philosopher " + philNum + " has returned both forks and will think again.");
        }
    }
}
