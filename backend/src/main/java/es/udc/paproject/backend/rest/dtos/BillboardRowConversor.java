package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.services.BillboardRow;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillboardRowConversor {
    private BillboardRowConversor(){};
    private static BillboardRowDto toBillboardDto(BillboardRow billboardRow){
        Map<Long,Long> map=new HashMap<>();
        for(Session item : billboardRow.getSessions()){
            map.put(item.getId(),toMillis(item.getDate()));
        }
        return new BillboardRowDto(billboardRow.getMovie().getTitle(),billboardRow.getMovie().getId(),map );
    }
    private  static long toMillis(LocalDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }
    public static List<BillboardRowDto> toBillboardDtos(List<BillboardRow> billboardRows){
        List<BillboardRowDto> billboardRowDtos=new ArrayList<>();
        for (BillboardRow item: billboardRows){
            billboardRowDtos.add(BillboardRowConversor.toBillboardDto(item));
        }
        return billboardRowDtos;
    }
}
