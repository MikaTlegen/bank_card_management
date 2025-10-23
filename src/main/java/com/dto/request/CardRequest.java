package com.dto.request;

import com.enums.CardStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

public class CardRequest {
    @NotBlank
    private String ownerName;

    @NotNull
    @FutureOrPresent
    private LocalDate expiryDate;

    private Long userId;

    public CardRequest(String ownerName, LocalDate expiryDate, Long userId) {
        this.ownerName = ownerName;
        this.expiryDate = expiryDate;
        this.userId = userId;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public Long getUserId() {
        return userId;
    }

}
