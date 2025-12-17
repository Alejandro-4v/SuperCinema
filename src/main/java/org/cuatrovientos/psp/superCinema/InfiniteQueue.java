package org.cuatrovientos.psp.superCinema;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InfiniteQueue {

    private final BlockingQueue<Cinephile> cinephilesWaitingInLine = new LinkedBlockingQueue<>();

    private final String IDENTIFIER;

    public InfiniteQueue(String name) {
        this.IDENTIFIER = name;
    }

}
