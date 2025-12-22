package org.cuatrovientos.psp.superCinema.v2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class FiniteQueue {

    private final BlockingQueue<Cinephile> cinephilesWaitingInLine = new ArrayBlockingQueue<>(Finals.MAX_CINEPHILES_PER_QUEUE);

    private final String IDENTIFIER;
    private final AtomicBoolean isStillWaitingForCinephiles = new AtomicBoolean(true);

    public FiniteQueue(String name) {
        this.IDENTIFIER = name;
    }

    public int getFreeSpacesInQueue() {
        return cinephilesWaitingInLine.remainingCapacity();
    }

    public void addCinephileToQueue(Cinephile cinephileToCheckIn) {
        System.out.printf("%s - cinephile added to queue \n", this.IDENTIFIER);
        cinephilesWaitingInLine.add(cinephileToCheckIn);
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
