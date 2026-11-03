public class Main {
    public static void main(String[] args) {
        SharedResource sr = new SharedResource();

        Thread t1 = new Thread(new Worker(sr));
        Thread t2 = new Thread(new Worker(sr));

        t1.start();
        t2.start();

        try {      
            t1.join();
            t2.join();
        } catch (InterruptedException e) {}

        System.out.println("Final Counter value: " + sr.getCounter());
    }
}
