package com.projectGroupWD55.bikeRentalAndRideSharing.repository;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.BillingAndInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BillingAndInvoiceRepository extends JpaRepository<BillingAndInvoice, Long> {
    List<BillingAndInvoice> findByUserId(Long userId);

}