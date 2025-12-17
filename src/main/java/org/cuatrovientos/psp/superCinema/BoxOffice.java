package org.cuatrovientos.psp.superCinema;

public class BoxOffice {

    private final int MIN_TIME_IN_SECONDS_FOR_SELLING_TICKET = 20;
    private final int MAX_TIME_IN_SECONDS_FOR_SELLING_TICKET = 30;

    private final int TIME_IN_MINUTES_FOR_OPENING_EARLIER = 30;

    private final InfiniteQueue INFINITEQUEUE1;
    private final InfiniteQueue INFINITEQUEUE2;

    private final String IDENTIFIER;

    public BoxOffice(String name, InfiniteQueue INFINITEQUEUE1, InfiniteQueue INFINITEQUEUE2) {
        this.IDENTIFIER = name;
        this.INFINITEQUEUE1 = INFINITEQUEUE1;
        this.INFINITEQUEUE2 = INFINITEQUEUE2;
    }

}
