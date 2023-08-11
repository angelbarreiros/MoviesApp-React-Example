package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PurchaseServiceTest {
    private final static Long NON_EXISTENT_ID = (long) -1;
    private final static String CARD_ID = "12345667743434";
    private final static long VERSION_NUMBER = 0L;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private MovieTheaterDao movieTheaterDao;
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private SessionDao sessionDao;

    private User createUser(String userName) {
        return new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com");
    }

    private Session createSessionToday(Movie movie, MovieTheater movieTheater) {
        return new Session(movie, movieTheater, LocalDateTime.now(), new BigDecimal(3), 60,VERSION_NUMBER);
    }

    private Session createSessionTomorrow(Movie movie, MovieTheater movieTheater) {
        return new Session(movie, movieTheater, LocalDateTime.now().plusDays(1), new BigDecimal(3), 60,VERSION_NUMBER);
    }
    private Session createFullSessionTomorrow(Movie movie, MovieTheater movieTheater){
        return new Session(movie,movieTheater, LocalDateTime.now().plusDays(1), new BigDecimal(3),0,VERSION_NUMBER);
    }

    private Movie createMovie(String Title) {
        return new Movie(Title, "SumaryTest", Integer.toUnsignedLong(120));
    }

    private MovieTheater createMovieTheater(String name) {
        return new MovieTheater(name, 180);
    }
    /*[FUNC-5]*/

    @Test
    public void testHistoryIsVoid() {

        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        assertEquals(purchaseService.history(user.getId(), 0, 10).getItems().size(), 0);

    }

    @Test
    public void getHistory() {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater = createMovieTheater("sala1");
        Session session = createSessionToday(movie, movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        Purchase purchase = new Purchase(5, session, user, CARD_ID, LocalDateTime.now(), false);
        purchaseDao.save(purchase);
        assertEquals(purchaseService.history(user.getId(), 0, 10).getItems().size(), 1);
    }

    @Test
    public void paginationTest() {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater = createMovieTheater("sala1");
        Session session = createSessionToday(movie, movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        for (int i = 0; i < 15; i++) {
            purchaseDao.save(new Purchase(5, session, user, CARD_ID, LocalDateTime.now(), false));
        }
        assertEquals(purchaseService.history(user.getId(), 0, 10).getItems().size(), 10);
        assertEquals(purchaseService.history(user.getId(), 1, 10).getItems().size(), 5);

    }

    @Test
    public void userNotFound() {
        assertEquals(purchaseService.history(NON_EXISTENT_ID, 0, 10).getItems().size(), 0);

    }

    /*[FUNC-4]*/
    @Test
    public void buyTest() throws InstanceNotFoundException, AlreadyStartedSessionException, FullSessionException {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater = createMovieTheater("sala1");
        Session session = createSessionTomorrow(movie, movieTheater);
        int total =session.getSeatsAvailable();
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        Purchase bought = purchaseService.buy(6, session.getId(), user.getId(), CARD_ID);
        assertTrue(purchaseDao.findById(bought.getId()).isPresent());
        assertEquals(bought, purchaseDao.findById(bought.getId()).get());
        assertEquals(6, purchaseDao.findById(bought.getId()).get().getReservedSeats());
        assertEquals(CARD_ID, purchaseDao.findById(bought.getId()).get().getCardNumber());
        assertFalse(purchaseDao.findById(bought.getId()).get().isUsed());

        assertNotEquals(session.getSeatsAvailable(),total);

    }

    @Test
    public void buyNonExistUserTest() {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater = createMovieTheater("sala1");
        Session session = createSessionToday(movie, movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);

        assertThrows(InstanceNotFoundException.class, () -> purchaseService.buy(6, session.getId(), NON_EXISTENT_ID, CARD_ID));
    }

    @Test
    public void buyNonExistSessionTest() {
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        assertThrows(InstanceNotFoundException.class, () -> purchaseService.buy(6, NON_EXISTENT_ID, user.getId(), CARD_ID));
    }


    @Test
    public void buyWhenFullSession()  {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater= createMovieTheater("sala1");
        Session session = createFullSessionTomorrow(movie,movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user= createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        assertThrows(FullSessionException.class, ()->purchaseService.buy(6,session.getId(), user.getId(),CARD_ID));
    }

    @Test
    public void buyWhenStartedSession() {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater= createMovieTheater("sala1");
        Session session = createSessionToday(movie,movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user= createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        assertThrows(AlreadyStartedSessionException.class, ()->purchaseService.buy(6,session.getId(), user.getId(),CARD_ID));
    }

    /*[FUNC-6]*/
    @Test
    public void ticketDeliveryCorrectlyTest() throws InstanceNotFoundException, AlreadyStartedSessionException,
            AlreadyUsedException, InvalidCardException, PurchaseNotFoundException, SessionNotFoundException {

        Movie movie = createMovie("Muscle Man");
        movieDao.save(movie);
        MovieTheater movieTheater = createMovieTheater("sala1");
        movieTheaterDao.save(movieTheater);
        Session session = createSessionTomorrow(movie, movieTheater);
        sessionDao.save(session);
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        Purchase purchase = new Purchase(11, session, user, CARD_ID, LocalDateTime.now(), false);
        purchaseDao.save(purchase);
        assertTrue(purchaseDao.findById(purchase.getId()).isPresent());
        purchaseService.ticketDelivery(purchase.getId(), CARD_ID);
        assertTrue(purchase.isUsed());
    }

    @Test
    public void ticketDeliveryFailDateTest(){
        Movie movie = createMovie("Muscle Man");
        movieDao.save(movie);
        MovieTheater movieTheater = createMovieTheater("sala1");
        movieTheaterDao.save(movieTheater);
        Session session = createSessionToday(movie, movieTheater);
        sessionDao.save(session);
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        Purchase purchase = new Purchase(10, session, user, CARD_ID, LocalDateTime.now(), false);
        purchaseDao.save(purchase);
        assertTrue(purchaseDao.findById(purchase.getId()).isPresent());
        assertThrows(AlreadyStartedSessionException.class, () -> purchaseService.ticketDelivery(purchase.getId(), CARD_ID));
    }

    @Test
    public void ticketDeliveryFailTransactionNumberTest()  {
        Movie movie = createMovie("Muscle Man");
        movieDao.save(movie);
        MovieTheater movieTheater = createMovieTheater("sala1");
        movieTheaterDao.save(movieTheater);
        Session session = createSessionTomorrow(movie, movieTheater);
        sessionDao.save(session);
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        Purchase purchase = new Purchase(10, session, user, CARD_ID, LocalDateTime.now(), false);
        purchaseDao.save(purchase);
        assertTrue(purchaseDao.findById(purchase.getId()).isPresent());
        assertThrows(InvalidCardException.class, () -> purchaseService.ticketDelivery(purchase.getId(), "23124311223459202039" ));
    }

    @Test
    public void alreadyUsedTickets() {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater = createMovieTheater("sala1");
        Session session = createSessionToday(movie, movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        Purchase purchase = new Purchase(10, session, user, CARD_ID, LocalDateTime.now(), true);
        purchaseDao.save(purchase);
        assertThrows(AlreadyUsedException.class, () -> purchaseService.ticketDelivery(purchase.getId(), CARD_ID));
    }
    @Test
    public void instanceNotFoundTickets(){
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater = createMovieTheater("sala1");
        Session session = createSessionToday(movie, movieTheater);
        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        Purchase purchase = new Purchase(10, session, user, CARD_ID, LocalDateTime.now(), true);
        purchaseDao.save(purchase);
        assertThrows(PurchaseNotFoundException.class, () -> purchaseService.ticketDelivery(NON_EXISTENT_ID, CARD_ID));
    }
}
