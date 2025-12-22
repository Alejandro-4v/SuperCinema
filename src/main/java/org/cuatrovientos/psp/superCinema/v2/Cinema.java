package org.cuatrovientos.psp.superCinema.v2;

public class Cinema {

    private static final int TIME_IN_MILLISECONDS_TO_WAIT_BEFORE_CHECKING_CINEMA_STATUS = 10;

    private static final FiniteQueue[] FINITE_QUEUES = new FiniteQueue[Finals.NUMBER_OF_BOX_OFFICES * Finals.NUMBER_OF_QUEUES_PER_BOX_OFFICE];

    private static final BoxOffice[] BOX_OFFICES = new BoxOffice[Finals.NUMBER_OF_BOX_OFFICES];
    private static final Thread[] BOX_OFFICES_THREADS = new Thread[Finals.NUMBER_OF_BOX_OFFICES];

    public static void main(String[] args) throws InterruptedException {

        //TODO: En este nuevo planteamiento ahora cada BoxOffice tendrá X filas, todas estas filas estarán dentro de un mismo array o colección
        // de forma que siempre estén localizables para poder ver cual es la que menos gente tiene e insertar un nuevo cinéfilo.
        // El Cinema tendrá un solo generador y este generador dará servicio a todas las colas.

        // Entonces se seguirá el mismo planteamiento añadiendo a las Queue la posibilidad de devolver la cantidad de espacios libres.
        // Esto será util para que el generador sepa dónde meter un nuevo cinéfilo. Y entonces dentro del propio generador habrá una colección de colas
        // con las que computará a dónde meter el nuevo cinéfilo.

        for (int queueIndex = 0; queueIndex < FINITE_QUEUES.length; queueIndex++) {
            FiniteQueue newQueue = new FiniteQueue(String.format("queue-%s", queueIndex));
            FINITE_QUEUES[queueIndex] = newQueue;
        }

        CinephileGenerator MAIN_CINEPHILEGENERATOR = new CinephileGenerator("MAIN_CINEPHILEGENERATOR", FINITE_QUEUES);
        Thread MAIN_CINEPHILEGENERATOR_THREAD = new Thread(MAIN_CINEPHILEGENERATOR);
        MAIN_CINEPHILEGENERATOR_THREAD.start();

        for (int currentBoxOfficeIndex = 0; currentBoxOfficeIndex < BOX_OFFICES.length; currentBoxOfficeIndex++) {
            FiniteQueue[] finiteQueuesForCurrentBoxOffice = new FiniteQueue[Finals.NUMBER_OF_QUEUES_PER_BOX_OFFICE];
            for (int queueIndex = 0; queueIndex < finiteQueuesForCurrentBoxOffice.length; queueIndex++) {
                finiteQueuesForCurrentBoxOffice[queueIndex] = FINITE_QUEUES[currentBoxOfficeIndex * Finals.NUMBER_OF_QUEUES_PER_BOX_OFFICE + queueIndex];
            }

//            System.arraycopy(
//                    FINITE_QUEUES,
//                    currentBoxOfficeIndex * Finals.NUMBER_OF_QUEUES_PER_BOX_OFFICE,
//                    finiteQueuesForCurrentBoxOffice,
//                    0,
//                    Finals.NUMBER_OF_QUEUES_PER_BOX_OFFICE
//            ); Esto también funcionaría, pero es menos legible y menos claro

            BoxOffice newBoxOffice = new BoxOffice(String.format("boxOffice-%s", currentBoxOfficeIndex), finiteQueuesForCurrentBoxOffice);
            BOX_OFFICES[currentBoxOfficeIndex] = newBoxOffice;

        }

        for (int currentBoxOfficeThreadIndex = 0; currentBoxOfficeThreadIndex < BOX_OFFICES_THREADS.length; currentBoxOfficeThreadIndex++) {
            Thread currentBoxOfficeThread = new Thread(BOX_OFFICES[currentBoxOfficeThreadIndex]);
            BOX_OFFICES_THREADS[currentBoxOfficeThreadIndex] = currentBoxOfficeThread;
        }

        for (Thread currentBoxOfficeThread : BOX_OFFICES_THREADS) {
            currentBoxOfficeThread.start();
        }

        while (!shouldTheCinemaClose()) {
            Thread.sleep(TIME_IN_MILLISECONDS_TO_WAIT_BEFORE_CHECKING_CINEMA_STATUS);
        }

        closeAllBoxOffices();
        MAIN_CINEPHILEGENERATOR_THREAD.interrupt();

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

    private static void closeAllBoxOffices() {
        for (Thread currentBoxOfficeThread : BOX_OFFICES_THREADS) {
            currentBoxOfficeThread.interrupt();
        }
    }

}
