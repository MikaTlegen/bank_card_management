package com.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransactionRequest {

    @NotNull
    private Long fromCardId;

    @NotNull
    private Long toCardId;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    public void setFromCardId(Long fromCardId) {
        this.fromCardId = fromCardId;
    }

    public void setToCardId(Long toCardId) {
        this.toCardId = toCardId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getFromCardId() {
        return fromCardId;
    }

    public Long getToCardId() {
        return toCardId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionRequest(Long fromCardId, Long toCardId, BigDecimal amount) {
        this.fromCardId = fromCardId;
        this.toCardId = toCardId;
        this.amount = amount;
    }
}
