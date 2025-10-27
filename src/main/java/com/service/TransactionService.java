package com.service;

import com.dto.request.TransactionRequest;
import com.entity.Card;
import com.entity.Transaction;
import com.exception.Transfer.CardOwnershipException;
import com.exception.Transfer.ExpiredCardTransferException;
import com.exception.Transfer.TransferToSameCardException;
import com.exception.card.CardNotFoundException;
import com.repository.CardRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class TransactionService {

    private final CardRepository cardRepository;

    public TransactionService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void transferMoney(TransactionRequest request, Long id) {

        if (request.getFromCardId().equals(request.getToCardId())) {
            throw new TransferToSameCardException("Cannot transfer money to the same card");
        }

        Card toCard = cardRepository.findById(request.getToCardId())
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        Card fromCard = cardRepository.findById(request.getFromCardId())
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        if (!fromCard.getUser().getId().equals(id)) {
            throw new CardOwnershipException("Source card does not belong to you");
        }
        if (!toCard.getUser().getId().equals(id)) {
            throw new CardOwnershipException("Destination card does not belong to you");
        }

        if (fromCard.isExpired()) {
            throw new ExpiredCardTransferException("Source card is expired");
        }
        if (toCard.isExpired()) {
            throw new ExpiredCardTransferException("Destination card is expired");
        }

        fromCard.setBalance(fromCard.getBalance().subtract(request.getAmount()));
        toCard.setBalance(toCard.getBalance().add(request.getAmount()));

        cardRepository.save(fromCard);
        cardRepository.save(toCard);
        log.info("Transfer completed: {} from card {} to card {}",
                request.getAmount(), fromCard.getId(), toCard.getId());

    }
}
