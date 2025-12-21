package org.cuatrovientos.psp.superCinema.v2;

public class Finals {

    protected static final int MIN_CINEPHILES_PER_MINUTE_RATIO = 10; // Cinéfilos mínimos por minuto
    protected static final int MAX_CINEPHILES_PER_MINUTE_RATIO = 15; // Cinéfilos máximos por minuto
    protected static final int TIME_IN_SECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILES = 60; // Tiempo que se tarda en generar nuevos cinéfilos

    protected static final int MIN_TIME_IN_SECONDS_FOR_SELLING_TICKET = 20; // Tiempo mínimo que tardará un taquillero en vender una entrada
    protected static final int MAX_TIME_IN_SECONDS_FOR_SELLING_TICKET = 30; // Tiempo máximo que tardará un taquillero en vender una entrada

    protected static final int TIME_IN_MINUTES_FOR_OPENING_EARLIER = 30; // Tiempo en minutos para abrir la(s) taquilla(s) antes de la película

    protected static final int MAX_CAPACITY_FOR_CINEPHILES = 200; // Asientos en el cine
    protected static final int NUMBER_OF_BOX_OFFICES = 2; // Taquilla(s)
    protected static final int NUMBER_OF_QUEUES_PER_BOX_OFFICE = 1; // Colas por taquilla
    protected static final int MAX_CINEPHILES_PER_QUEUE = 10; // Tamaño máximo de las colas de la taquilla

    protected static final int TIME_IN_MILLISECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILES = TIME_IN_SECONDS_TO_SLEEP_UNTIL_NEW_CINEPHILES * Maths.SCALE_FOR_MILLISECONDS_FROM_SECONDS; // No tocar (esto es para pasar de segundos a milisegundos)
    protected static final int TIME_IN_MILLISECONDS_FOR_OPENING_EARLIER = TIME_IN_MINUTES_FOR_OPENING_EARLIER * Maths.SCALE_FOR_MILLISECONDS_FROM_MINUTES; // No tocar (esto es para pasar de segundos a milisegundos)
}
