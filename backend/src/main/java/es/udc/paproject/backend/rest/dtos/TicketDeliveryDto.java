package es.udc.paproject.backend.rest.dtos;

import javax.validation.constraints.*;
import java.math.BigInteger;

public class TicketDeliveryDto {
    private Long id;
    private String transactionNumber;
    private Long purchaseId;

    public TicketDeliveryDto() {}

    public TicketDeliveryDto(Long id, String transactionNumber, Long purchaseId) {
        this.id = id;
        this.transactionNumber = transactionNumber;
        this.purchaseId = purchaseId;
    }

    public Long getId() {return id;}
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
    @Positive
    @NotNull

    public Long getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }
}
