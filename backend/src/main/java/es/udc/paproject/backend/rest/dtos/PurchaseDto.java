package es.udc.paproject.backend.rest.dtos;

import javax.validation.constraints.*;

public class PurchaseDto {
    private Long id;
    private String transactionNumber;
    private int numberOfTickets;

    public PurchaseDto(){}

    public PurchaseDto(Long id, String transactionNumber, int numberOfTickets) {
        this.id = id;
        this.transactionNumber = transactionNumber;
        this.numberOfTickets = numberOfTickets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @NotNull
    @Positive
    @Size(min = 13,max = 18)

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
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


}
