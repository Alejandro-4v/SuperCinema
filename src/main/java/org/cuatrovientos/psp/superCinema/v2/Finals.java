package org.cuatrovientos.psp.superCinema.v2;

public class Finals {

    // Aquí no habrá un ratio de cinéfilos por X cantidad de tiempo, pues las colas ahora son finitas.
    // En las colas esta vez se podrá configurar cada cuanto llega un nuevo cinéfilo
    // De no haber espacio el cinéfilo no entrará a la cola y marchará triste por no poder ver Avatar 2
    protected static final int TIME_IN_SECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILE = 15; // Tiempo que se tarda en generar un nuevo cinéfilos

    protected static final int MIN_TIME_IN_SECONDS_FOR_SELLING_TICKET = 20; // Tiempo mínimo que tardará un taquillero en vender una entrada
    protected static final int MAX_TIME_IN_SECONDS_FOR_SELLING_TICKET = 30; // Tiempo máximo que tardará un taquillero en vender una entrada

    protected static final int TIME_IN_MINUTES_FOR_OPENING_EARLIER = 30; // Tiempo en minutos para abrir la(s) taquilla(s) antes de la película

    protected static final int MAX_CAPACITY_FOR_CINEPHILES = 200; // Asientos en el cine
    protected static final int NUMBER_OF_BOX_OFFICES = 2; // Taquilla(s)
    protected static final int NUMBER_OF_QUEUES_PER_BOX_OFFICE = 1; // Colas por taquilla
    protected static final int MAX_CINEPHILES_PER_QUEUE = 10; // Tamaño máximo de las colas de la taquilla

    protected static final int TIME_IN_MILLISECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILE = TIME_IN_SECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILE * Maths.SCALE_FOR_MILLISECONDS_FROM_SECONDS; // No tocar (esto es para pasar de segundos a milisegundos)
    protected static final int TIME_IN_MILLISECONDS_FOR_OPENING_EARLIER = TIME_IN_MINUTES_FOR_OPENING_EARLIER * Maths.SCALE_FOR_MILLISECONDS_FROM_MINUTES; // No tocar (esto es para pasar de segundos a milisegundos)

    public static void checkFinals() {

        if (TIME_IN_SECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILE <= 0) {
            throwRuntimeException("TIME_IN_SECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILE must be greater than 0");

        } else if (MIN_TIME_IN_SECONDS_FOR_SELLING_TICKET <= 0) {
            throwRuntimeException("MIN_TIME_IN_SECONDS_FOR_SELLING_TICKET must be greater than 0");

        } else if (MAX_TIME_IN_SECONDS_FOR_SELLING_TICKET <= 0) {
            throwRuntimeException("MAX_TIME_IN_SECONDS_FOR_SELLING_TICKET must be greater than 0");

        } else if (MIN_TIME_IN_SECONDS_FOR_SELLING_TICKET > MAX_TIME_IN_SECONDS_FOR_SELLING_TICKET) {
            throwRuntimeException("MIN_TIME_IN_SECONDS_FOR_SELLING_TICKET cannot be greater than MAX_TIME_IN_SECONDS_FOR_SELLING_TICKET");

        } else if (TIME_IN_MINUTES_FOR_OPENING_EARLIER <= 0) {
            throwRuntimeException("TIME_IN_MINUTES_FOR_OPENING_EARLIER must be greater than 0");

        } else if (MAX_CAPACITY_FOR_CINEPHILES <= 0) {
            throwRuntimeException("MAX_CAPACITY_FOR_CINEPHILES must be greater than 0");

        } else if (NUMBER_OF_BOX_OFFICES <= 0) {
            throwRuntimeException("NUMBER_OF_BOX_OFFICES must be greater than 0");

        } else if (NUMBER_OF_QUEUES_PER_BOX_OFFICE <= 0) {
            throwRuntimeException("NUMBER_OF_QUEUES_PER_BOX_OFFICE must be greater than 0");

        } else if (MAX_CINEPHILES_PER_QUEUE <= 0) {
            throwRuntimeException("MAX_CINEPHILES_PER_QUEUE must be greater than 0");

        } else if (TIME_IN_MILLISECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILE <= 0) {
            throwRuntimeException("TIME_IN_MILLISECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILE must be greater than 0");

        } else if (TIME_IN_MILLISECONDS_FOR_OPENING_EARLIER <= 0) {
            throwRuntimeException("TIME_IN_MILLISECONDS_FOR_OPENING_EARLIER must be greater than 0");

        }
    }

    private static void throwRuntimeException(String msg) {
        throw new RuntimeException(msg);
    }

}
