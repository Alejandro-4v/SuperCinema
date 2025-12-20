package org.cuatrovientos.psp.superCinema;

import java.util.Random;

public class Maths {

    public static final int SCALE_FOR_MILLISECONDS_FROM_SECONDS = 1; // DEBUG
    public static final int SCALE_FOR_MILLISECONDS_FROM_MINUTES = 600; // DEBUG
//    protected static final int SCALE_FOR_MILLISECONDS_FROM_SECONDS = 1000;
//    protected static final int SCALE_FOR_MILLISECONDS_FROM_MINUTES = 60000;

    private static final Random RANDOM = new Random();

    public static int randomIntBetweenRangeInclusive(int start, int bound) {
        return RANDOM.nextInt(bound - start + 1) + start;
    }

}
