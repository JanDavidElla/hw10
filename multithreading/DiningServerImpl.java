package multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DiningServerImpl implements DiningServer {

    enum State {THINKING, HUNGRY, EATING};

    public static final int NUM_OF_PHILS = 5;

    private State[] state;

    Lock lock;
    Condition[] self;

    public DiningServerImpl() {
        state = new State[NUM_OF_PHILS];

        for(int i = 0; i < NUM_OF_PHILS; i++) {
            state[i] = State.THINKING;
        }

        lock = new ReentrantLock();

        self = new Condition[NUM_OF_PHILS];

        for(int i = 0; i < NUM_OF_PHILS; i++) {
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
            state[pnum] = State.THINKING;

            test(leftNeighbor(pnum));
            test(rightNeighbor(pnum));
        } finally {
            lock.unlock();
        }
    }

    private void test(int i) {
        if(state[i] == State.HUNGRY && state[leftNeighbor(i)] != State.EATING && state[rightNeighbor(i)] != State.EATING) {
            state[i] = State.EATING;
            self[i].signal();
        }
    }

    private int leftNeighbor(int i) {
        return ((i+1)% NUM_OF_PHILS);
    }

    private int rightNeighbor(int i) {
        return((i+4)%NUM_OF_PHILS);
    }  
}