package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Purchase;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryConversor {
    private PurchaseHistoryConversor(){}
    public static PurchaseHistoryDto toPurchaseHistoryDto(Purchase purchase){
        return new PurchaseHistoryDto(purchase.getId(),
                toMillis(purchase.getPurchaseDate()),
                purchase.getCardNumber(),
                purchase.getSession().getMovie().getTitle(),
                purchase.getReservedSeats(),purchase.getTotalPrice(),
                toMillis(purchase.getSession().getDate()),
                purchase.isUsed());
    }
    public static List<PurchaseHistoryDto> toPurchaseDtos(List<Purchase> purchases){
        List<PurchaseHistoryDto> purchaseHistoryDtos = new ArrayList<>();
        for (Purchase item: purchases){
            purchaseHistoryDtos.add(toPurchaseHistoryDto(item));
        }
        return purchaseHistoryDtos;
    }


    private  static long toMillis(LocalDateTime date) {

        return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }

}
