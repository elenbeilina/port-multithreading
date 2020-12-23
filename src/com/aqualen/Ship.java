package com.aqualen;

public class Ship extends Thread {

    private final boolean hasContainers;
    private final int capacity;
    private final Port port;

    public Ship(boolean hasContainers, int capacity, Port port) {
        this.hasContainers = hasContainers;
        this.capacity = capacity;
        this.port = port;
    }

    public void run() {
        try {
            if (hasContainers) {
                port.unload(capacity);
            } else {
                port.load(capacity);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
