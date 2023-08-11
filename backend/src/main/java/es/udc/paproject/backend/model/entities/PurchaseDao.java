package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PurchaseDao extends PagingAndSortingRepository<Purchase, Long> {
    Slice<Purchase> findAllByUserId_IdOrderByPurchaseDateDescIdDesc(Long id, Pageable pageable);
}