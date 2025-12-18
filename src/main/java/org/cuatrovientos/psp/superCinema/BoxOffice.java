package org.cuatrovientos.psp.superCinema;

import java.util.concurrent.atomic.AtomicInteger;

public class BoxOffice implements Runnable {

    private final int MIN_TIME_IN_SECONDS_FOR_SELLING_TICKET = 20;
    private final int MAX_TIME_IN_SECONDS_FOR_SELLING_TICKET = 30;

    private final int TIME_IN_MINUTES_FOR_OPENING_EARLIER = 30;

    private final String IDENTIFIER;
    private final InfiniteQueue INFINITEQUEUE;

    private long openedAtTimestap;

    private final AtomicInteger soldTickets = new AtomicInteger(0);

    public BoxOffice(String name, InfiniteQueue INFINITEQUEUE) {
        this.IDENTIFIER = name;
        this.INFINITEQUEUE = INFINITEQUEUE;

        this.openedAtTimestap = System.currentTimeMillis();
    }

    @Override
    public void run() {

        while (isBoxOfficeStillOpen()) {
            try {
                Cinephile waitingCinephile = INFINITEQUEUE.getWaitingCinephile();
                if (waitingCinephile != null) {
                    System.out.printf("%s - Started selling tickets to %s\n", this.IDENTIFIER, waitingCinephile.getIDENTIFIER());
                    Thread.sleep((long) Maths.randomIntBetweenRangeInclusive(MIN_TIME_IN_SECONDS_FOR_SELLING_TICKET, MAX_TIME_IN_SECONDS_FOR_SELLING_TICKET) * Maths.SCALE_FOR_MILLISECONDS_FROM_SECONDS);
                    System.out.printf("%s - Finished selling tickets to %s\n", this.IDENTIFIER, waitingCinephile.getIDENTIFIER());
                    soldTickets.incrementAndGet();
                }
            } catch (InterruptedException e) {
                closeBoxOffice();
            }
        }

    }

    public void closeBoxOffice() {
        closeQueue();
        System.out.printf("Box Office closed. Total sold tickets in this box office: %s\n", soldTickets.get());
    }

    private void closeQueue() {
        INFINITEQUEUE.closeQueue();
    }

    private long getTimeFromOpeningInMiliseconds() {
        return System.currentTimeMillis() - openedAtTimestap;
    }

    private boolean checkIfTheFilmHasStarted() {
        return getTimeFromOpeningInMiliseconds() > TIME_IN_MINUTES_FOR_OPENING_EARLIER * Maths.SCALE_FOR_MILLISECONDS_FROM_MINUTES;
    }

    public boolean isBoxOfficeStillOpen() {
        return !checkIfTheFilmHasStarted();
    }

    public int getSoldTickets() {
        return soldTickets.get();
    }

}
