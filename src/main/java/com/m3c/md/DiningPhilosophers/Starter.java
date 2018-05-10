package com.m3c.md.DiningPhilosophers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Starter {
    public static void main(String[] args) {
        Fork fork = new Fork();
        Fork fork2 = new Fork();
        Fork fork3 = new Fork();
        Fork fork4 = new Fork();
        Fork fork5 = new Fork();

        Philosopher philosopher = new Philosopher("plato", fork, fork2);
        Philosopher philosopher2 = new Philosopher("aristotle", fork2, fork3);
        Philosopher philosopher3 = new Philosopher("socrates", fork3, fork4);
        Philosopher philosopher4 = new Philosopher("kant", fork4, fork5);
        Philosopher philosopher5 = new Philosopher("descartes", fork5, fork);

        Philosopher[] philosophers = {philosopher, philosopher2, philosopher3, philosopher4, philosopher5};

        for (Philosopher p : philosophers) {
            p.start();
        }
    }
}

class Fork {
    private Lock lock;

    Fork() {
        lock = new ReentrantLock();
    }

    public boolean pickUp() {
        return lock.tryLock();
    }

    public void putDown() {
        lock.unlock();
    }
}



class Philosopher extends Thread {
    private Fork left, right;
    private String name;
    private int numBites = 10;

    Philosopher(String name, Fork left, Fork right) {
        this.left = left;
        this.right = right;
        this.name = name;
    }

    private String getPhiloName() {
        return name;
    }

    private void putDown() {
        left.putDown();
        right.putDown();
    }

    private boolean pickUp() {
        if (!left.pickUp()) {
            return false;
        }

        if (!right.pickUp()) {
            left.putDown();
            return false;
        }
        return true;
    }

    private void chew() {
        System.out.println(getPhiloName() + " is eating " + numBites);
    }

    private void eat() {
        if (pickUp()) {
            chew();
            putDown();
            numBites--;
        }
    }

    public void run() {

        while (numBites > 0){
            eat();
        }

        System.out.println(Thread.currentThread().getName() + ": " + getPhiloName() + " has finished eating");
    }
}