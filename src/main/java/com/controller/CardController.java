package com.controller;

import com.dto.request.CardRequest;
import com.dto.request.CardStatusRequest;
import com.dto.response.CardResponse;
import com.enums.CardStatus;
import com.security.UserPrincipal;
import com.service.CardService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CardResponse> createCard(@RequestBody @Valid CardRequest cardRequest) {
        CardResponse cardResponse = cardService.createCard(cardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CardResponse>> getAllCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<CardResponse> cardPage = cardService.getAllCards(
                currentUser.getId(),
                currentUser.getRoles(),
                pageable
        );
        return ResponseEntity.ok(cardPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCardById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        CardResponse cardResponse = cardService.getCardById(
                id,
                currentUser.getId(),
                currentUser.getRoles());
        return ResponseEntity.ok(cardResponse);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CardResponse> updateCard(
            @PathVariable Long id,
            @RequestBody @Valid CardStatusRequest cardRequest,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        CardResponse cardResponse = cardService.updateCard(
                id,
                cardRequest.getStatus(),
                currentUser.getId(),
                currentUser.getRoles());
        return ResponseEntity.ok(cardResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCard(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        cardService.deleteCard(id, currentUser.getId(), currentUser.getRoles());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-cards")
    public ResponseEntity<List<CardResponse>> getUserCards(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        List<CardResponse> cardResponses = cardService.getUserCards(currentUser.getId());
        return ResponseEntity.ok(cardResponses);
    }

    @GetMapping("/{cardId}/balance")
    public ResponseEntity<BigDecimal> getCardBalance(
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        BigDecimal balance = cardService.getCardBalance(cardId, currentUser.getId(), currentUser.getRoles());

        return ResponseEntity.ok(balance);
    }
}
