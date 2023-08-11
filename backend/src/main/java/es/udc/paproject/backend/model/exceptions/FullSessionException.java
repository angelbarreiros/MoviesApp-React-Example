package es.udc.paproject.backend.model.exceptions;

public class FullSessionException extends Exception{
    private final int freeSeats;

    public FullSessionException(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public int getFreeSeats() {
        return freeSeats;
    }
}
