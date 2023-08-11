package es.udc.paproject.backend.model.exceptions;

import java.time.LocalTime;

@SuppressWarnings("serial")
public class AlreadyStartedSessionException extends Exception{
    private final String title;
    private LocalTime time;


    public AlreadyStartedSessionException(String title, LocalTime time) {
        this.title = title;
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }


}
