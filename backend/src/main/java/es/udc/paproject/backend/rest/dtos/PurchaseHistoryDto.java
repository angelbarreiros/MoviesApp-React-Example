package es.udc.paproject.backend.rest.dtos;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PurchaseHistoryDto {
    private Long id;
    private Long purchaseDate;
    private String transactionNumber;
    private String movieTitle;
    private int numberOfTickets;
    private BigDecimal price;
    private Long sessionDate;
    private boolean used;

    public PurchaseHistoryDto() {
    }

    public PurchaseHistoryDto(Long id, Long purchaseDate, String transactionNumber,
                              String movieTitle, int numberOfTickets,
                              BigDecimal price, Long sessionDate,
                              boolean used) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.transactionNumber = transactionNumber;
        this.movieTitle = movieTitle;
        this.numberOfTickets = numberOfTickets;
        this.price = price;
        this.sessionDate = sessionDate;
        this.used = used;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public Long getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    @NotNull
    @Positive
    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }
    @NotBlank
    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    @NotNull
    @Min(1)
    @Max(10)
    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Long sessionDate) {
        this.sessionDate = sessionDate;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }


}
