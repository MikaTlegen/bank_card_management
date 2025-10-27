package com.controller;

import com.dto.request.TransactionRequest;
import com.security.UserPrincipal;
import com.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;

    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(
            @RequestBody @Valid TransactionRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        transactionService.transferMoney(request, currentUser.getId());
        return ResponseEntity.ok("Money transferred successfully");
    }
}
