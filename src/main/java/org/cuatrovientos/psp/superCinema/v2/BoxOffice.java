package org.cuatrovientos.psp.superCinema.v2;

import java.util.concurrent.atomic.AtomicInteger;

public class BoxOffice implements Runnable {

    private final String IDENTIFIER;
    private final FiniteQueue[] FINITEQUEUES;

    private final long openedAtTimestamp;

    private final AtomicInteger soldTickets = new AtomicInteger(0);

    public BoxOffice(String name, FiniteQueue[] FINITEQUEUES) {
        this.IDENTIFIER = name;
        this.FINITEQUEUES = FINITEQUEUES;

        this.openedAtTimestamp = System.currentTimeMillis();
    }

    @Override
    public void run() {

        while (isBoxOfficeStillOpen()) {
            try {
                Cinephile nextCinephile = getNextCinephile();
                if (nextCinephile != null) {
                    System.out.printf("%s - Started selling tickets to %s\n", this.IDENTIFIER, nextCinephile.getIDENTIFIER());
                    Thread.sleep((long) Maths.randomIntBetweenRangeInclusive(Finals.MIN_TIME_IN_SECONDS_FOR_SELLING_TICKET, Finals.MAX_TIME_IN_SECONDS_FOR_SELLING_TICKET) * Maths.SCALE_FOR_MILLISECONDS_FROM_SECONDS);
                    System.out.printf("%s - Finished selling tickets to %s\n", this.IDENTIFIER, nextCinephile.getIDENTIFIER());
                    soldTickets.incrementAndGet();
                }
            } catch (InterruptedException e) {
                closeBoxOffice();
                break;
            }
        }

    }

    private void closeBoxOffice() {
        closeQueues();
        System.out.printf("Box Office (%s) closed. Total sold tickets in this box office: %s\n", this.IDENTIFIER, soldTickets.get());
    }

    public boolean isBoxOfficeStillOpen() {
        return !checkIfTheFilmHasStarted();
    }

    public int getSoldTickets() {
        return soldTickets.get();
    }

    private void closeQueues() {
        for (FiniteQueue finiteQueue : FINITEQUEUES) {
            finiteQueue.closeQueue();
        }
    }

    private Cinephile getNextCinephile() throws InterruptedException {
        return getFiniteQueueWithMoreCinephilesWaiting().getWaitingCinephile();
    }

    private FiniteQueue getFiniteQueueWithMoreCinephilesWaiting() {
        if (FINITEQUEUES.length == 1) {
            return FINITEQUEUES[0];
        }

        FiniteQueue finiteQueueWithMoreCinephilesWaiting = FINITEQUEUES[0];

        for (FiniteQueue finiteQueue : FINITEQUEUES) {
            if (finiteQueue.getFreeSpacesInQueue() > finiteQueueWithMoreCinephilesWaiting.getFreeSpacesInQueue()) {
                finiteQueueWithMoreCinephilesWaiting = finiteQueue;
            }
        }

        return finiteQueueWithMoreCinephilesWaiting;
    }

    private long getTimeFromOpeningInMilliseconds() {
        return System.currentTimeMillis() - openedAtTimestamp;
    }

    private boolean checkIfTheFilmHasStarted() {
        return getTimeFromOpeningInMilliseconds() > Finals.TIME_IN_MILLISECONDS_FOR_OPENING_EARLIER;
    }

}
