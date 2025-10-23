package com.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_card_id", nullable = false)
    private Card fromCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_card_id", nullable = false)
    private Card toCard;

    @Column(nullable = false)
    private BigDecimal amount;

    public Transaction() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFromCard(Card fromCard) {
        this.fromCard = fromCard;
    }

    public void setToCard(Card toCard) {
        this.toCard = toCard;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public Card getFromCard() {
        return fromCard;
    }

    public Card getToCard() {
        return toCard;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Transaction(int id, Card fromCard, Card toCard, BigDecimal amount, LocalDateTime createdAt) {
        this.id = id;
        this.fromCard = fromCard;
        this.toCard = toCard;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
