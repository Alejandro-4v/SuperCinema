package org.cuatrovientos.psp.superCinema;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class InfiniteQueue {

    private final BlockingQueue<Cinephile> cinephilesWaitingInLine = new LinkedBlockingQueue<>();

    private final String IDENTIFIER;
    private final AtomicBoolean isStillWaitingForCinephiles = new AtomicBoolean(true);

    public InfiniteQueue(String name) {
        this.IDENTIFIER = name;
    }

    public void addCinephilesToQueue(ArrayList<Cinephile> cinephilesToCheckIn) {
        System.out.printf("%s - %s cinephiles added to queue", this.IDENTIFIER, cinephilesToCheckIn.size());
        cinephilesWaitingInLine.addAll(cinephilesToCheckIn);
    }

    public Cinephile getWaitingCinephile() throws InterruptedException {
        return cinephilesWaitingInLine.take();
    }

    public void closeQueue() {
        isStillWaitingForCinephiles.set(false);
    }

    public boolean isStillWaitingForCinephiles() {
        return isStillWaitingForCinephiles.get();
    }

}
