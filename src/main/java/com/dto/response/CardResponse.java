package com.dto.response;

import com.entity.User;
import com.enums.CardStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.net.CacheResponse;

@Builder
public class CardResponse extends User {
    private Long id;
    private String maskedCardNumber;
    private String ownerName;
    private String expiryDate;
    private CardStatus status;
    private BigDecimal balance;
    private Long userId;
    private String userName;

    public void setId(Long id) {
        this.id = id;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public CardStatus getStatus() {
        return status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public CardResponse(Long id, String maskedCardNumber, String ownerName, String expiryDate, CardStatus status, BigDecimal balance, Long userId, String userName) {
        this.id = id;
        this.maskedCardNumber = maskedCardNumber;
        this.ownerName = ownerName;
        this.expiryDate = expiryDate;
        this.status = status;
        this.balance = balance;
        this.userId = userId;
        this.userName = userName;
    }
}
