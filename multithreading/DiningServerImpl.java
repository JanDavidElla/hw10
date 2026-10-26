package multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DiningServerImpl implements DiningServer{

    enum State {THINKING, HUNGRY, EATING};

    public static final int NUM_OF_PHILS = 5;

    private State[] state;

    Lock lock;
    Condition[] self;

    public DiningServerImpl(Lock lock, Condition[] self) {
        this.lock = lock;
        this.self = self;
        state = new State[NUM_OF_PHILS];
        for (int i = 0; i < NUM_OF_PHILS; i++) {
            state[i] = State.THINKING;
        }

        lock = new ReentrantLock();

        self = new Condition[NUM_OF_PHILS];

        for (int i = 0; i < NUM_OF_PHILS; i++) {
            self[i] = lock.newCondition();
        }
    }



    public void takeForks(int pnum) {
        lock.lock();
        try {
            state[pnum] = State.HUNGRY;
            test(pnum);
            if (state[pnum] != State.EATING) {
                self[pnum].await();
            }
        }
        catch (InterruptedException ie) { }
        finally {
            lock.unlock();
        }
    }

    public void returnForks(int pnum) {
        lock.lock();
        try {

        }
    }

    private void test(int i) {
    }

    private int leftNeighbor(int i) {

    }

    private int rightNeighbor(int i) {

    }

    

    
}