package org.cuatrovientos.psp.superCinema;

import java.util.ArrayList;

public class CinephileGenerator implements Runnable {

    private final int MIN_CINEPHILES_PER_MINUTE_RATIO = 10;
    private final int MAX_CINEPHILES_PER_MINUTE_RATIO = 15;
    private final int TIME_IN_MILLISECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILES = 60000;

    private final String IDENTIFIER;
    private final InfiniteQueue INFINITEQUEUE;

    public CinephileGenerator(String IDENTIFIER, InfiniteQueue INFINITEQUEUE) {
        this.IDENTIFIER = String.format("%s-cinephileGenerator", IDENTIFIER);
        this.INFINITEQUEUE = INFINITEQUEUE;
    }

    @Override
    public void run() {

        System.out.printf("%s - Started generating cinephiles", this.IDENTIFIER);

        while (INFINITEQUEUE.isStillWaitingForCinephiles()) {
            int cinephilesPerMinute = Maths.randomIntBetweenRangeInclusive(MIN_CINEPHILES_PER_MINUTE_RATIO, MAX_CINEPHILES_PER_MINUTE_RATIO);

            try {
                Thread.sleep(TIME_IN_MILLISECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILES);
                ArrayList<Cinephile> cinephilesToAddToQueue = new ArrayList<>();
                for (int i = 0; i < cinephilesPerMinute; i++) {
                    cinephilesToAddToQueue.add(new Cinephile(String.format("%s-%s", IDENTIFIER, i)));
                }
                INFINITEQUEUE.addCinephilesToQueue(cinephilesToAddToQueue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
