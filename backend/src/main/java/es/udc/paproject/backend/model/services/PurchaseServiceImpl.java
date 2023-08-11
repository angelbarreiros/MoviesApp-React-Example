package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService{

    private final PermissionChecker permissionChecker;

    private final SessionDao sessionDao;

    private final PurchaseDao purchaseDao;
    public PurchaseServiceImpl(PermissionChecker permissionChecker, SessionDao sessionDao, PurchaseDao purchaseDao) {
        this.permissionChecker = permissionChecker;
        this.sessionDao = sessionDao;
        this.purchaseDao = purchaseDao;
    }


    @Override
    public Purchase buy(int reservedSeats, Long sessionId, Long userId, String transactionNumber) throws
            InstanceNotFoundException, AlreadyStartedSessionException, FullSessionException {

        Optional<Session> optionalSession = sessionDao.findById(sessionId);
        User user = permissionChecker.checkUser(userId); //función dada por el checker
        if(optionalSession.isEmpty()){
            throw new InstanceNotFoundException("project.entities.session", sessionId);
        }
        Session session = optionalSession.get();
        if(session.getDate().isBefore(LocalDateTime.now())){
            throw new AlreadyStartedSessionException(session.getMovie().getTitle(),session.getDate().toLocalTime());
        }
        if (session.getSeatsAvailable()<reservedSeats){
            throw new FullSessionException(session.getSeatsAvailable());
        }

        session.setSeatsAvailable(session.getSeatsAvailable()-reservedSeats);
        Purchase purchase= new Purchase(reservedSeats,session,user,transactionNumber,LocalDateTime.now(),false);

        purchaseDao.save(purchase);
        return purchase;
    }

    @Override
    public Block<Purchase> history(Long Id, int page, int size) {
        Slice<Purchase> purchaseSlice = purchaseDao.findAllByUserId_IdOrderByPurchaseDateDescIdDesc(Id, PageRequest.of(page, size));
        return new Block<>(purchaseSlice.getContent(),purchaseSlice.hasNext());
    }

    @Override
    public void ticketDelivery(Long purchaseId, String transactionNumber)
            throws PurchaseNotFoundException, SessionNotFoundException, AlreadyUsedException,
            AlreadyStartedSessionException, InvalidCardException {
        Optional<Purchase> optionalPurchase = purchaseDao.findById(purchaseId);
        if(!optionalPurchase.isPresent()){
            throw new PurchaseNotFoundException("project.entities.purchase");
        }
        Purchase purchase= optionalPurchase.get();
        if (purchase.isUsed()){
            throw new AlreadyUsedException("Already used");
        }
        Optional<Session> session = sessionDao.findById(purchase.getSession().getId());
        if (session.isEmpty()){
            throw new SessionNotFoundException("project.entities.session");
        }
        if(session.get().getDate().isBefore(LocalDateTime.now())){
            throw new AlreadyStartedSessionException(session.get().getMovie().getTitle(),session.get().getDate().toLocalTime());
        }
        if(!purchase.getCardNumber().equals(transactionNumber)){
            throw new InvalidCardException(transactionNumber);
        }
        purchase.setUsed(true);
    }

}
