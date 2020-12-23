package com.aqualen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        Port port = new Port(2, 6);

        List<Ship> ships = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            ships.add(new Ship(false, i, port));
        }
        for (int i = 6; i <= 10; i++) {
            ships.add(new Ship(true, i - 5, port));
        }

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (Ship ship : ships) {
            service.submit(ship);
        }
    }
}
