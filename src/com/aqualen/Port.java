package com.aqualen;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Port {
    private final int amountOfDocks;

    private int amountOfContainers;
    private final Queue<Integer> docks = new LinkedBlockingDeque<>();

    public Port(int amountOfDocks, int amountOfContainers) {
        this.amountOfDocks = amountOfDocks;
        this.amountOfContainers = amountOfContainers;
    }

    public synchronized void unload(int cargo) throws InterruptedException {
        checkDocks();

        docks.offer(1);
        TimeUnit.MILLISECONDS.sleep(1000);

        amountOfContainers+= cargo;
        System.out.println(Thread.currentThread().getName() + "unloaded " + cargo + " containers");

        TimeUnit.MILLISECONDS.sleep(1000);
        docks.poll();
        notifyAll();
    }

    public synchronized void load(int cargo) throws InterruptedException {
        checkDocks();

        docks.offer(1);
        TimeUnit.MILLISECONDS.sleep(1000);

        while (cargo >= amountOfContainers){
            System.out.println("There are not enough containers!");
            System.out.println(Thread.currentThread().getName() + " is waiting for containers");

            wait();
        }

        amountOfContainers -= cargo;
        System.out.println(Thread.currentThread().getName() + " loaded" + cargo + "  containers");

        TimeUnit.MILLISECONDS.sleep(1000);
        docks.poll();

        notifyAll();
    }

    private synchronized void checkDocks() throws InterruptedException {
        while (docks.size() > amountOfDocks) {
            System.out.println("All docks are busy!");
            System.out.println(Thread.currentThread().getName() + " is waiting to load/unload it's cargo");

            wait();
        }
    }
}
