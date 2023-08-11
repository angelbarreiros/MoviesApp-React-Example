package es.udc.paproject.backend.test.model.services;


import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.AlreadyStartedSessionException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.TimeExceededException;
import es.udc.paproject.backend.model.services.BillboardRow;
import es.udc.paproject.backend.model.services.BillboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BillboardServiceTest {
    private final static Long NON_EXISTENT_ID = (long) -1;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate TOMORROW = LocalDate.now().plusDays(1);
    private final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    @Autowired
    private BillboardService billboardService;
    @Autowired
    private MovieTheaterDao movieTheaterDao;
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private SessionDao sessionDao;

    private Movie createMovie(String Title){
        return new Movie(Title, "SumaryTest", Integer.toUnsignedLong(120));
    }
    private MovieTheater createMovieTheater(String name){
        return  new MovieTheater(name, 180);
    }
    private Session createSessionToday(Movie movie, MovieTheater movieTheater){
        return new Session(movie,movieTheater, LocalDateTime.of(TODAY,LocalTime.now().plusMinutes(5)), new BigDecimal(3),60);
    }
    private Session createSessionTomorrow(Movie movie, MovieTheater movieTheater){
        return new Session(movie,movieTheater,LocalDateTime.of(TOMORROW,LocalTime.now()), new BigDecimal(3),60);
    }
    private Session createSessionYesterday(Movie movie, MovieTheater movieTheater){
        return new Session(movie,movieTheater, LocalDateTime.of(YESTERDAY,LocalTime.now()), new BigDecimal(3),60);
    }

    /*[FUNC-3]*/
    @Test
    public void sessionInfoTest() throws InstanceNotFoundException, AlreadyStartedSessionException {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater= createMovieTheater("sala1");

        Session session = createSessionToday(movie,movieTheater);

        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);

        assertEquals(session, billboardService.findSessionById(session.getId()));

    }
    @Test
    public void sessionAlreadyStarted()  {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater= createMovieTheater("sala1");

        Session session = createSessionYesterday(movie,movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        assertThrows(AlreadyStartedSessionException.class, ()-> billboardService.findSessionById(session.getId()));


    }
    @Test
    public void nonExistSession(){
        assertThrows(InstanceNotFoundException.class, ()-> billboardService.findSessionById(NON_EXISTENT_ID));
    }
    /*[FUNC-1]*/
    @Test
    public void getTodayBillboardTest() throws TimeExceededException {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater= createMovieTheater("sala1");
        Session session = createSessionToday(movie,movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        List<BillboardRow> billboardRowList= billboardService.completeBillboard(TODAY);
        assertEquals(movie,billboardRowList.get(0).getMovie());
        assertEquals(session,billboardRowList.get(0).getSessions().get(0));

    }
    @Test
    public void getTomorrowBillboardTest() throws TimeExceededException {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater= createMovieTheater("sala1");
        Session session = createSessionTomorrow(movie,movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        List<BillboardRow> billboardRowList= billboardService.completeBillboard(TOMORROW);
        assertEquals(movie,billboardRowList.get(0).getMovie());
        assertEquals(session,billboardRowList.get(0).getSessions().get(0));

    }
    @Test
    public void getYesterdayBillboardTest() {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater= createMovieTheater("sala1");
        Session session = createSessionYesterday(movie,movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        assertThrows(TimeExceededException.class, ()->billboardService.completeBillboard(YESTERDAY));

    }

    @Test
    public void sessionHasStarted() throws TimeExceededException {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater= createMovieTheater("sala1");
        Session session = createSessionToday(movie,movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        Session session2 = createSessionToday(movie,movieTheater);
        session2.setDate(LocalDateTime.of(LocalDate.now(),LocalTime.now().minusMinutes(1)));

        sessionDao.save(session);
        sessionDao.save(session2);

        List<BillboardRow> billboardRowList= billboardService.completeBillboard(TODAY);
        assertEquals(billboardRowList.size(),1);
        assertEquals(billboardRowList.get(0).getSessions().size(),1);

    }
    @Test
    public void notGettingSessionsStartingTomorrow() throws TimeExceededException {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater= createMovieTheater("sala1");
        Session session2 = createSessionTomorrow(movie,movieTheater);
        Session session = createSessionTomorrow(movie,movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        sessionDao.save(session);
        sessionDao.save(session2);
        List<BillboardRow> billboardRowList= billboardService.completeBillboard(TODAY);
        assertEquals(billboardRowList.size(),0);
    }





    /*[FUNC-4]*/
    @Test
    public void insertAndGetMovie() throws InstanceNotFoundException {
        Movie movie= new Movie("algo","algomas",100L);
        Long id =movieDao.save(movie).getId();
        assertEquals(movie, billboardService.getMovie(id));
    }
    @Test
    public void filmNotFound(){
        assertThrows(InstanceNotFoundException.class ,() -> billboardService.getMovie(NON_EXISTENT_ID));
    }

}
