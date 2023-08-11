package es.udc.paproject.backend.model.exceptions;

import java.math.BigInteger;

@SuppressWarnings("serial")
public class InvalidCardException extends Exception{
    private final String cardNumber;

    public InvalidCardException(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }


}
