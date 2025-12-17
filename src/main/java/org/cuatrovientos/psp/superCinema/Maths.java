package org.cuatrovientos.psp.superCinema;

import java.util.Random;

public class Maths {

    private static final Random RANDOM = new Random();

    public static int randomIntBetweenRangeInclusive(int start, int bound) {
        return RANDOM.nextInt(bound - start + 1) + start;
    }

}
