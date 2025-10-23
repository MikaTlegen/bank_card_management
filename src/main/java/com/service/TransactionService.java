package com.service;

import com.dto.request.TransactionRequest;
import com.entity.Card;
import com.entity.Transaction;
import com.repository.CardRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionService {

    private final CardRepository cardRepository;

    public TransactionService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void transferMoney(TransactionRequest request, Long id) {

        if (request.getFromCardId().equals(request.getToCardId())) {
            throw new RuntimeException("You can't transfer money to the same card");
        }

        Card toCard = cardRepository.findById(request.getToCardId())
                .orElseThrow(() -> new RuntimeException("Card not found"));

        Card fromCard = cardRepository.findById(request.getFromCardId())
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (!fromCard.getUser().getId().equals(id) ||
                !toCard.getUser().getId().equals(id)) {
            throw new RuntimeException("You can't transfer money from or to a card that is not yours");
        }

        if (fromCard.isExpired() || toCard.isExpired()) {
            throw new RuntimeException("You can't transfer money from or to an expired card");
        }

        fromCard.setBalance(fromCard.getBalance().subtract(request.getAmount()));
        toCard.setBalance(toCard.getBalance().add(request.getAmount()));

        cardRepository.save(fromCard);
        cardRepository.save(toCard);

    }
}
