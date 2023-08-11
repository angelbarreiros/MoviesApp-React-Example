package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Session;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class SessionConversor {
    private SessionConversor(){}

    public static SessionDto toSessionDto(Session session){
        return new SessionDto(session.getId()
                ,session.getMovie().getId(),
                session.getMovie().getTitle()
                ,session.getMovieTheater().getName(),
                session.getMovie().getDuration()
                ,toMillis(session.getDate())
                ,session.getPrice(),session.getSeatsAvailable());
    }
    private  static long toMillis(LocalDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }
}
