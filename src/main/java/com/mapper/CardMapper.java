package com.mapper;

import com.dto.response.CardResponse;
import com.entity.Card;
import com.entity.User;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class CardMapper {
    public CardResponse toResponse(Card card) {

        User user = card.getUser();
        if (user == null) {
            throw new IllegalStateException("Card must have an associated user");
        }

        return CardResponse.builder()
                .id(card.getId())
                .maskedCardNumber("**** **** ****" + card.getLastFourDigits())
                .ownerName(card.getOwnerName())
                .expiryDate(card.getExpiryDate().format(DateTimeFormatter.ofPattern("MM/yy")))
                .status(card.getStatus())
                .balance(card.getBalance())
                .userId(card.getUser().getId())
                .userName(card.getUser().getFullName())
                .build();

    }
}
