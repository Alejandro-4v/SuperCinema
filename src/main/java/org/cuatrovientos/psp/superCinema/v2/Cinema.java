package org.cuatrovientos.psp.superCinema.v2;

public class Cinema {

    private static final int TIME_IN_MILLISECONDS_TO_WAIT_BEFORE_CHECKING_CINEMA_STATUS = 10;

    private static final FiniteQueue[] FINITE_QUEUES = new FiniteQueue[Finals.NUMBER_OF_BOX_OFFICES * Finals.NUMBER_OF_QUEUES_PER_BOX_OFFICE];

    private static final BoxOffice[] BOX_OFFICES = new BoxOffice[Finals.NUMBER_OF_BOX_OFFICES];
    private static final Thread[] BOX_OFFICES_THREADS = new Thread[Finals.NUMBER_OF_BOX_OFFICES];

    private static CinephileGenerator MAIN_CINEPHILEGENERATOR;
    private static Thread MAIN_CINEPHILEGENERATOR_THREAD;

    public static void main(String[] args) throws InterruptedException {

        Finals.checkFinals();

        generateQueues();

        generateMainCinephileGenerator();

        startMainCinephileGenerator();

        assignFiniteQueuesToCorrespondingBoxOffice();

        generateBoxOfficeThreads();

        openAllBoxOffices();

        while (!shouldTheCinemaClose()) {
            Thread.sleep(TIME_IN_MILLISECONDS_TO_WAIT_BEFORE_CHECKING_CINEMA_STATUS);
        }

        closeAllBoxOffices();

        stopMainCinephileGenerator();

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

    private static void generateQueues() {
        for (int queueIndex = 0; queueIndex < FINITE_QUEUES.length; queueIndex++) {
            FiniteQueue newQueue = new FiniteQueue(String.format("queue-%s", queueIndex));
            FINITE_QUEUES[queueIndex] = newQueue;
        }
    }

    private static void generateMainCinephileGenerator() {
        MAIN_CINEPHILEGENERATOR = new CinephileGenerator("MAIN_CINEPHILEGENERATOR", FINITE_QUEUES);
        MAIN_CINEPHILEGENERATOR_THREAD = new Thread(MAIN_CINEPHILEGENERATOR);
    }

    private static void startMainCinephileGenerator() {
        MAIN_CINEPHILEGENERATOR_THREAD.start();
    }

    private static void assignFiniteQueuesToCorrespondingBoxOffice() {
        for (int currentBoxOfficeIndex = 0; currentBoxOfficeIndex < BOX_OFFICES.length; currentBoxOfficeIndex++) {
            FiniteQueue[] finiteQueuesForCurrentBoxOffice = new FiniteQueue[Finals.NUMBER_OF_QUEUES_PER_BOX_OFFICE];
            for (int currentQueueIndex = 0; currentQueueIndex < finiteQueuesForCurrentBoxOffice.length; currentQueueIndex++) {
                finiteQueuesForCurrentBoxOffice[currentQueueIndex] = FINITE_QUEUES[getCorrespondingFiniteQueueIndexForBoxOffice(currentBoxOfficeIndex, currentQueueIndex)];
            }

            BoxOffice newBoxOffice = new BoxOffice(String.format("boxOffice-%s", currentBoxOfficeIndex), finiteQueuesForCurrentBoxOffice);
            BOX_OFFICES[currentBoxOfficeIndex] = newBoxOffice;
        }
    }

    private static void generateBoxOfficeThreads() {
        for (int currentBoxOfficeThreadIndex = 0; currentBoxOfficeThreadIndex < BOX_OFFICES_THREADS.length; currentBoxOfficeThreadIndex++) {
            Thread currentBoxOfficeThread = new Thread(BOX_OFFICES[currentBoxOfficeThreadIndex]);
            BOX_OFFICES_THREADS[currentBoxOfficeThreadIndex] = currentBoxOfficeThread;
        }
    }

    private static void openAllBoxOffices() {
        for (Thread currentBoxOfficeThread : BOX_OFFICES_THREADS) {
            currentBoxOfficeThread.start();
        }
    }

    private static void closeAllBoxOffices() {
        for (Thread currentBoxOfficeThread : BOX_OFFICES_THREADS) {
            currentBoxOfficeThread.interrupt();
        }
    }

    private static void stopMainCinephileGenerator() {
        MAIN_CINEPHILEGENERATOR_THREAD.interrupt();
    }

    private static int getCorrespondingFiniteQueueIndexForBoxOffice(int currentBoxOfficeIndex, int queueIndex) {
        return currentBoxOfficeIndex * Finals.NUMBER_OF_QUEUES_PER_BOX_OFFICE + queueIndex;
    }

}
