package es.udc.paproject.backend.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Purchase {
    public static final int PAGE_SIZE=2;
    private Long id;
    private int reservedSeats;
    private Session session;

    private User user;
    private String cardNumber;
    private LocalDateTime purchaseDate;
    private boolean used;

    public Purchase() {
    }
    public Purchase(int reservedSeats,
                    Session session, User user, String cardNumber,
                    LocalDateTime purchaseDate, boolean used) {

        this.reservedSeats = reservedSeats;
        this.session = session;
        this.user = user;
        this.cardNumber = cardNumber;
        this.purchaseDate = purchaseDate;
        this.used = used;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        id=Id;
    }

    public int getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(int reservedSeats)   {
        this.reservedSeats = reservedSeats;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "sessionId")
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getCardNumber() {
        return cardNumber;
    }

        public void setCardNumber(String  cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Transient
    public BigDecimal getTotalPrice(){
        BigDecimal seats= new BigDecimal(this.reservedSeats);
        return this.getSession().getPrice().multiply(seats);
    }

}

