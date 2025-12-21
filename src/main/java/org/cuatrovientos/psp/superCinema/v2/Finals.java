package org.cuatrovientos.psp.superCinema.v2;

public class Finals {

    protected static final int MIN_CINEPHILES_PER_MINUTE_RATIO = 10;
    protected static final int MAX_CINEPHILES_PER_MINUTE_RATIO = 15;
    protected static final int TIME_IN_SECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILES = 60;
    protected static final int TIME_IN_MILLISECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILES = TIME_IN_SECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILES * Maths.SCALE_FOR_MILLISECONDS_FROM_SECONDS;

    protected static final int MIN_TIME_IN_SECONDS_FOR_SELLING_TICKET = 20;
    protected static final int MAX_TIME_IN_SECONDS_FOR_SELLING_TICKET = 30;

    protected static final int TIME_IN_MINUTES_FOR_OPENING_EARLIER = 30;

    protected static final int MAX_CAPACITY_FOR_CINEPHILES = 200;
    protected static final int NUMBER_OF_BOX_OFFICES = 2;
}
