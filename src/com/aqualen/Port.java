package com.aqualen;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Port {
    private final int amountOfDocks;

    private int amountOfContainers;
    private final Queue<Integer> docks;

    public Port(int amountOfDocks, int amountOfContainers) {
        this.amountOfDocks = amountOfDocks;
        this.amountOfContainers = amountOfContainers;
        docks = new LinkedList<>();
        for (int i = 0; i < amountOfDocks; i++) {
            docks.offer(i);
        }
    }

    public void unload(int cargo) throws InterruptedException {
        checkDocks();
        docks.poll();

        TimeUnit.MILLISECONDS.sleep(100);

        amountOfContainers += cargo;
        System.out.println(Thread.currentThread().getName() + " unloaded " + cargo + " containers");

        TimeUnit.MILLISECONDS.sleep(100);

        docks.offer(1);
        notifyShips();
    }

    public void load(int cargo) throws InterruptedException {
        checkDocks();

        docks.poll();
        TimeUnit.MILLISECONDS.sleep(100);

        checkContainers(cargo);

        amountOfContainers -= cargo;
        System.out.println(Thread.currentThread().getName() + " loaded" + cargo + "  containers");

        TimeUnit.MILLISECONDS.sleep(100);
        docks.offer(1);

        notifyShips();
    }

    private synchronized void notifyShips(){
        notifyAll();
    }

    private synchronized void checkContainers(int cargo) throws InterruptedException {
        while (cargo >= amountOfContainers) {
            System.out.println("There are not enough containers!");
            System.out.println(Thread.currentThread().getName() + " is waiting for containers");

            wait();
        }
    }

    private synchronized void checkDocks() throws InterruptedException {
        while (docks.size() > amountOfDocks) {
            System.out.println("All docks are busy!");
            System.out.println(Thread.currentThread().getName() + " is waiting to load/unload it's cargo");

            wait();
        }
    }
}
