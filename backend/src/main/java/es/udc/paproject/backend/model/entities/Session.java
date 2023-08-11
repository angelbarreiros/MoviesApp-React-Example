package es.udc.paproject.backend.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Session {
    public static final int MAX_DAYS = 6;
    private Long id;

    private Movie movie;

    private MovieTheater movieTheater;

   private LocalDateTime date;

    private BigDecimal price;

    private int seatsAvailable;
    private long version;

    public Session(){}

    public Session(Long id, Movie movie, MovieTheater movieTheater, LocalDateTime date,
                    BigDecimal price, int seatsAvailable,
                   long version) {
        this.id = id;
        this.movie = movie;
        this.movieTheater = movieTheater;
        this.date = date;
        this.price = price;
        this.seatsAvailable = seatsAvailable;
        this.version = version;
    }
    public Session( Movie movie, MovieTheater movieTheater, LocalDateTime date, BigDecimal price, int seatsAvailable,
                   long version) {
        this.movie = movie;
        this.movieTheater = movieTheater;
        this.date = date;
        this.price = price;
        this.seatsAvailable = seatsAvailable;
        this.version = version;
    }
    public Session( Movie movie, MovieTheater movieTheater, LocalDateTime date,
                     BigDecimal price, int seatsAvailable) {
        this.movie = movie;
        this.movieTheater = movieTheater;
        this.date = date;
        this.price = price;
        this.seatsAvailable = seatsAvailable;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="movieTheaterId")
    public MovieTheater getMovieTheater() {
        return movieTheater;
    }

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="movieId")
    public Movie getMovie() {
        return movie;
    }

     public void setMovie(Movie movie) {
     this.movie = movie;
    }


    public void setMovieTheater(MovieTheater movieTheater) {
        this.movieTheater = movieTheater;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", movie=" + movie +
                ", movieTheater=" + movieTheater +
                ", date=" + date +
                ", price=" + price +
                ", seatsAvailable=" + seatsAvailable +
                ", version=" + version +
                '}';
    }

    @Version
    public long getVersion() {
        return version;
    }
    public void setVersion(long version) {
        this.version = version;
    }

}
