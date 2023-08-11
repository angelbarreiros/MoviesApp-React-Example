package es.udc.paproject.backend.model.entities;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface   SessionDao extends PagingAndSortingRepository<Session,Long> {
    List<Session> findAllByDateBetween(LocalDateTime ini,LocalDateTime end);
}
