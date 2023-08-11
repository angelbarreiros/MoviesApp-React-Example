package es.udc.paproject.backend.rest.dtos;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class SessionDto {
    private Long id;
    private Long movieId;
    private String movieName;
    private String movieTheaterName ;
    private Long duration;
    private Long date;
    private BigDecimal price;
    private int seatsAvailable;

    public SessionDto() {}

    public SessionDto(Long id, Long movieId, String movieName,
                      String movieTheaterName,
                      Long duration, Long date,
                      BigDecimal price, int seatsAvailable) {
        this.id = id;
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieTheaterName = movieTheaterName;
        this.duration = duration;
        this.date = date;
        this.price = price;
        this.seatsAvailable = seatsAvailable;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @NotBlank
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    @NotBlank
    public String getMovieTheaterName() {
        return movieTheaterName;
    }

    public void setMovieTheaterName(String movieTheaterName) {
        this.movieTheaterName = movieTheaterName;
    }

    @NotNull
    @FutureOrPresent
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @NotNull
    @PositiveOrZero
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    @NotNull
    @PositiveOrZero

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
