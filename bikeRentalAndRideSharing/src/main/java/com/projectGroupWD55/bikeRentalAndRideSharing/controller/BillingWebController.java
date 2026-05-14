package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.entity.BillingAndInvoice;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.BillingAndInvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/billing")
public class BillingWebController {

    private final BillingAndInvoiceService billingService;

    public BillingWebController(BillingAndInvoiceService billingService) {
        this.billingService = billingService;
    }

    @GetMapping("/history/{userId}")
    public String viewBillingHistory(@PathVariable Long userId, Model model) {
        List<BillingAndInvoice> history = billingService.getHistory(userId);
        model.addAttribute("invoices", history);
        model.addAttribute("userId", userId);
        return "billing-history";
    }

    @PostMapping("/{id}/pay")
    public String payInvoice(@PathVariable Long id, @RequestParam Long userId) {
        billingService.updateStatus(id, "PAID");
        return "redirect:/billing/history/" + userId;
    }
}