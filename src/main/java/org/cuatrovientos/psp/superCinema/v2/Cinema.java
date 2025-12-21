package org.cuatrovientos.psp.superCinema.v2;

public class Cinema {

    private static final InfiniteQueue INFINITEQUEUE1 = new InfiniteQueue("infiniteQueue-1");

    private static final CinephileGenerator CINEPHILEGENERATOR1 = new CinephileGenerator("mainCinephileGenerator", INFINITEQUEUE1);
    private static final int TIME_IN_MILLISECONDS_TO_WAIT_BEFORE_CHECKING_CINEMA_STATUS = 10;

    private static final BoxOffice[] BOX_OFFICES = new BoxOffice[Finals.NUMBER_OF_BOX_OFFICES];
    private static final Thread[] BOX_OFFICES_THREADS = new Thread[Finals.NUMBER_OF_BOX_OFFICES];

    public static void main(String[] args) throws InterruptedException {
        Thread cinephileGenerator1Thread = new Thread(CINEPHILEGENERATOR1);

        for (int boxOfficeIndex = 0; boxOfficeIndex < BOX_OFFICES.length; boxOfficeIndex++) {
            BoxOffice newBoxOffice = new BoxOffice(String.format("boxOffice-%s", boxOfficeIndex), INFINITEQUEUE1);
            BOX_OFFICES[boxOfficeIndex] = newBoxOffice;
            BOX_OFFICES_THREADS[boxOfficeIndex] = new Thread(newBoxOffice);
        }

        cinephileGenerator1Thread.start();
        for (Thread boxOfficesThread : BOX_OFFICES_THREADS) {
            boxOfficesThread.start();
        }

        while (!shouldTheCinemaClose()) {
            Thread.sleep(TIME_IN_MILLISECONDS_TO_WAIT_BEFORE_CHECKING_CINEMA_STATUS);
        }

        for (Thread boxOfficesThread : BOX_OFFICES_THREADS) {
            boxOfficesThread.interrupt();
        }

        cinephileGenerator1Thread.interrupt();

        System.out.printf("Cinema open! The movie is starting! Total sold tickets: %s\n", getSoldTicketsAmongAllBoxOffices());
    }

    public static int getSoldTicketsAmongAllBoxOffices() {
        int soldTickets = 0;
        for (BoxOffice boxOffice : BOX_OFFICES) {
            soldTickets += boxOffice.getSoldTickets();
        }
        return soldTickets;
    }

    public static boolean isAnyBoxOfficeStillOpen() {
        for (BoxOffice boxOffice : BOX_OFFICES) {
            if (boxOffice.isBoxOfficeStillOpen()) {
                return true;
            }
        }
        return false;
    }

    public static boolean allAreTicketsSold() {
        return getSoldTicketsAmongAllBoxOffices() >= Finals.MAX_CAPACITY_FOR_CINEPHILES;
    }

    public static boolean shouldTheCinemaClose() {
        return !isAnyBoxOfficeStillOpen() || allAreTicketsSold();
    }

}
