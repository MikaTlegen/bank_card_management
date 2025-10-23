package com.entity;

import com.enums.CardStatus;
import jakarta.persistence.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Builder
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Column(name = "last_four_digits", nullable = false, length = 4)
    private String lastFourDigits;

    @Column(name = "owner_name", nullable = false, length = 100)
    private String ownerName;

    @Column(name = "expiry_date", nullable = false, length = 100)
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Card(Long id, String cardNumber, String ownerName, LocalDate expiryDate, CardStatus status, BigDecimal balance, User user) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.ownerName = ownerName;
        this.expiryDate = expiryDate;
        this.status = status;
        this.balance = balance;
        this.user = user;
    }

    public Card() {
    }

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public CardStatus getStatus() {
        return status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }
}

