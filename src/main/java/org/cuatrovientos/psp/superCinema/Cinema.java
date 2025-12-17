package org.cuatrovientos.psp.superCinema;

import java.util.concurrent.atomic.AtomicInteger;

public class Cinema {

    private final int MAX_CAPACITY_FOR_CINEPHILES = 200;

    private AtomicInteger cinephilesInCinema = new AtomicInteger(0);

    private final InfiniteQueue INFINITEQUEUE1 = new InfiniteQueue("infiniteQueue-1");
    private final InfiniteQueue INFINITEQUEUE2 = new InfiniteQueue("infiniteQueue-2");

    private final BoxOffice BOXOFFICE1 = new BoxOffice("boxOffice-1", INFINITEQUEUE1, INFINITEQUEUE2);

    private final String IDENTIFIER;

    public Cinema(String name) {
        this.IDENTIFIER = name;
    }

}
