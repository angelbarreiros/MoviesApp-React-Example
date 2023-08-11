package es.udc.paproject.backend.model.exceptions;

import es.udc.paproject.backend.model.entities.Session;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class TimeExceededException extends Exception {
    public TimeExceededException() {
    }
    public List<LocalDate> getAllowedDays() {
        List<LocalDate> localDates = new ArrayList<>();
        for (int i =0; i< Session.MAX_DAYS;i++){
            localDates.add(LocalDate.now().plusDays(i));
        }
        return localDates;
    }

}

