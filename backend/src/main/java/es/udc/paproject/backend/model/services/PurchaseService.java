package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.exceptions.*;

import java.math.BigInteger;
import java.util.List;

public interface PurchaseService {
    Purchase buy(int reservedSeats, Long sessionId, Long userId, String transactionNumber) throws InstanceNotFoundException,  AlreadyStartedSessionException, FullSessionException;
    void ticketDelivery(Long purchaseId, String transactionNumber) throws PurchaseNotFoundException,
            SessionNotFoundException, AlreadyUsedException, AlreadyStartedSessionException, InvalidCardException;
    Block<Purchase> history(Long Id, int page , int size);
}
