package org.cuatrovientos.psp.superCinema.v1;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CinephileGenerator implements Runnable {

    private final int MIN_CINEPHILES_PER_MINUTE_RATIO = 10;
    private final int MAX_CINEPHILES_PER_MINUTE_RATIO = 15;
    private final int TIME_IN_SECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILES = 60;

    private final int TIME_IN_MILLISECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILES = TIME_IN_SECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILES * Maths.SCALE_FOR_MILLISECONDS_FROM_SECONDS;

    private final String IDENTIFIER;
    private final InfiniteQueue INFINITEQUEUE;

    private final AtomicInteger currentCinephileId = new AtomicInteger(0);

    public CinephileGenerator(String name, InfiniteQueue INFINITEQUEUE) {
        this.IDENTIFIER = name;
        this.INFINITEQUEUE = INFINITEQUEUE;
    }

    @Override
    public void run() {

        System.out.printf("%s - Started generating cinephiles\n", this.IDENTIFIER);

        while (INFINITEQUEUE.isStillWaitingForCinephiles()) {
            int cinephilesPerMinute = Maths.randomIntBetweenRangeInclusive(MIN_CINEPHILES_PER_MINUTE_RATIO, MAX_CINEPHILES_PER_MINUTE_RATIO);

            try {
                Thread.sleep(TIME_IN_MILLISECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILES);
                ArrayList<Cinephile> cinephilesToAddToQueue = new ArrayList<>();
                for (int i = 0; i < cinephilesPerMinute; i++) {
                    cinephilesToAddToQueue.add(new Cinephile(String.format("%s-cinephile-%s", IDENTIFIER, currentCinephileId.getAndIncrement())));
                }
                INFINITEQUEUE.addCinephilesToQueue(cinephilesToAddToQueue);
            } catch (InterruptedException e) {
                closeCinephileGenerator();
                break;
            }
        }
    }

    private void closeCinephileGenerator() {
        System.out.printf("%s - Queue closed\n", this.IDENTIFIER);
    }
}
