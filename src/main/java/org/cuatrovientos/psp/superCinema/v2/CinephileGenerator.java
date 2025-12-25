package org.cuatrovientos.psp.superCinema.v2;

import java.util.concurrent.atomic.AtomicInteger;

public class CinephileGenerator implements Runnable {

    private final String IDENTIFIER;
    private final FiniteQueue[] finiteQueues;

    private final AtomicInteger currentCinephileId = new AtomicInteger(0);

    public CinephileGenerator(String name, FiniteQueue[] INFINITEQUEUES) {
        this.IDENTIFIER = name;
        this.finiteQueues = INFINITEQUEUES;
    }

    @Override
    public void run() {

        System.out.printf("%s - Started generating cinephiles\n", this.IDENTIFIER);

        while (isAnyFiniteQueueWaitingForCinephiles() && !Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(Finals.TIME_IN_MILLISECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILE);

                Cinephile newCinephile = new Cinephile(String.format("%s-cinephile-%s", IDENTIFIER, currentCinephileId.getAndIncrement()));

                FiniteQueue finiteQueueToAddTheCinephile = getFiniteQueueWithMoreRemainingPlaces();

                finiteQueueToAddTheCinephile.addCinephileToQueue(newCinephile);

            } catch (InterruptedException e) {
                closeCinephileGenerator();
                break;
            } catch (IllegalStateException e) {
                System.out.println("Queue is full, the Cinephile walks home sadly because he won't get to see Avatar 2");
            }
        }
    }

    private void closeCinephileGenerator() {
        System.out.printf("%s - CinephileGenerator closed\n", this.IDENTIFIER);
    }

    private boolean isAnyFiniteQueueWaitingForCinephiles() {
        for (FiniteQueue finiteQueue : finiteQueues) {
            if (finiteQueue.isStillWaitingForCinephiles()) {
                return true;
            }
        }
        return false;
    }

    private FiniteQueue getFiniteQueueWithMoreRemainingPlaces() {
        if (finiteQueues.length == 1) {
            return finiteQueues[0];
        }

        FiniteQueue finiteQueueWithMoreRemainingPlaces = finiteQueues[0];
        for (FiniteQueue finiteQueue : finiteQueues) {
            if (finiteQueue.getFreeSpacesInQueue() > finiteQueueWithMoreRemainingPlaces.getFreeSpacesInQueue()) {
                finiteQueueWithMoreRemainingPlaces = finiteQueue;
            }
        }
        return finiteQueueWithMoreRemainingPlaces;
    }

}
