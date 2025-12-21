package org.cuatrovientos.psp.superCinema.v2;

public class Cinema {

    private static final InfiniteQueue INFINITEQUEUE1 = new InfiniteQueue("infiniteQueue-1");

    private static final CinephileGenerator CINEPHILEGENERATOR1 = new CinephileGenerator("mainCinephileGenerator", INFINITEQUEUE1);
    private static final int TIME_IN_MILLISECONDS_TO_WAIT_BEFORE_CHECKING_CINEMA_STATUS = 10;

    private static final BoxOffice BOXOFFICE1 = new BoxOffice("boxOffice-1", INFINITEQUEUE1);
    private static final BoxOffice BOXOFFICE2 = new BoxOffice("boxOffice-2", INFINITEQUEUE1);;

    private static final BoxOffice[] BOX_OFFICES = new BoxOffice[Finals.NUMBER_OF_BOX_OFFICES];

    public static void main(String[] args) throws InterruptedException {
        Thread cinephileGenerator1Thread = new Thread(CINEPHILEGENERATOR1);
        Thread boxOffice1Thread = new Thread(BOXOFFICE1);
        Thread boxOffice2Thread = new Thread(BOXOFFICE2);

        cinephileGenerator1Thread.start();
        boxOffice1Thread.start();
        boxOffice2Thread.start();

        while (!shouldTheCinemaClose()) {
            Thread.sleep(TIME_IN_MILLISECONDS_TO_WAIT_BEFORE_CHECKING_CINEMA_STATUS);
        }

        boxOffice1Thread.interrupt();
        boxOffice2Thread.interrupt();
        cinephileGenerator1Thread.interrupt();

        System.out.printf("Cinema open! The movie is starting! Total sold tickets: %s\n", getSoldTicketsAmongAllBoxOffices());
    }

    public static int getSoldTicketsAmongAllBoxOffices() {
        return BOXOFFICE1.getSoldTickets() + BOXOFFICE2.getSoldTickets();
    }

    public static boolean isAnyBoxOfficeStillOpen() {
        return BOXOFFICE1.isBoxOfficeStillOpen() || BOXOFFICE2.isBoxOfficeStillOpen();
    }

    public static boolean allAreTicketsSold() {
        return getSoldTicketsAmongAllBoxOffices() >= Finals.MAX_CAPACITY_FOR_CINEPHILES;
    }

    public static boolean shouldTheCinemaClose() {
        return !isAnyBoxOfficeStillOpen() || allAreTicketsSold();
    }

}
