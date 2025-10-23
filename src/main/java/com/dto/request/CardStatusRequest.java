package com.dto.request;

import com.enums.CardStatus;
import jakarta.validation.constraints.NotNull;

public class CardStatusRequest {
    @NotNull
    private CardStatus status;

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public CardStatus getStatus() {
        return status;
    }

    public CardStatusRequest(CardStatus status) {
        this.status = status;
    }
}
