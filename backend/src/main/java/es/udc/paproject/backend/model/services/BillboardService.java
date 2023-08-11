package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.exceptions.AlreadyStartedSessionException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.TimeExceededException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface BillboardService {
    Session findSessionById(Long id) throws InstanceNotFoundException, AlreadyStartedSessionException;
    List<BillboardRow> completeBillboard(LocalDate date) throws TimeExceededException;
    Movie getMovie(Long id) throws InstanceNotFoundException;

}
