package com.service;

import com.dto.request.CardRequest;
import com.dto.response.CardResponse;
import com.entity.Card;
import com.entity.User;
import com.enums.CardStatus;
import com.enums.UserRole;
import com.exception.card.AccessDeniedException;
import com.exception.card.CardNotFoundException;
import com.exception.card.CardNumberNotFoundException;
import com.exception.card.InvalidCardStatusException;
import com.exception.user.UserNotFoundException;
import com.mapper.CardMapper;
import com.repository.CardRepository;
import com.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper;

    public CardService(CardRepository cardRepository, UserRepository userRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.cardMapper = cardMapper;
    }

    @Transactional
    public CardResponse createCard(CardRequest cardRequest) {
        User user = userRepository.findById(cardRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(cardRequest.getUserId()));

        String cardNumber = generateCardNumber();
        String encryptedCardNumber = encryptCardNumber(cardNumber);
        String lastFourDigits = cardNumber.substring(12);

        Card card = Card.builder()
                .cardNumber(encryptedCardNumber)
                .lastFourDigits(lastFourDigits)
                .ownerName(cardRequest.getOwnerName())
                .expiryDate(cardRequest.getExpiryDate())
                .status(CardStatus.ACTIVE)
                .balance(BigDecimal.ZERO)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        Card savedCard = cardRepository.save(card);

        return cardMapper.toResponse(savedCard);
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();

        for (int i = 0; i < 16; i++)
            cardNumber.append(random.nextInt(10));

        return cardNumber.toString();
    }

    private String encryptCardNumber(String cardNumber) {
        return Base64.getEncoder().encodeToString(cardNumber.getBytes());
    }

    public Page<CardResponse> getAllCards(
            Long currentUserId,
            UserRole userRole,
            Pageable pageable) {

        Page<Card> cards;

        if (userRole == UserRole.ADMIN) {
            cards = cardRepository.findAll(pageable);
        } else {
            cards = cardRepository.findByUserId(currentUserId, pageable);
        }

        return cards.map(cardMapper::toResponse);
    }

    public CardResponse getCardById(
            Long сardId,
            Long сurrentUserId,
            UserRole roles) {

        Card card = cardRepository.findById(сardId)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        if (roles != UserRole.ADMIN && !card.getUser().getId().equals(сurrentUserId)) {
            throw new AccessDeniedException("You do not have permission to access this card");
        }

        return cardMapper.toResponse(card);
    }

    @Transactional
    public CardResponse updateCard(
            Long cardId,
            CardStatus status,
            Long currentUserId,
            UserRole roles) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("Card not found with id: " + cardId));

        if (roles != UserRole.ADMIN && !card.getUser().getId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not have permission to update this card");
        }

        if (roles == UserRole.USER && status != CardStatus.BLOCKED) {
            throw new InvalidCardStatusException("You can only block the card");

        }

        if (card.getExpiryDate().isBefore(LocalDate.now())) {
            card.setStatus(CardStatus.EXPIRED);
        } else {
            card.setStatus(status);
        }

        Card updatedCard = cardRepository.save(card);
        return cardMapper.toResponse(updatedCard);

    }

    @Transactional
    public void deleteCard(Long cardId, Long userId, UserRole roles) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNumberNotFoundException("Card not found"));

        if (roles != UserRole.ADMIN) {
            throw new AccessDeniedException("Only administrators can delete cards");
        }

        card.setDeleted(true);
        cardRepository.save(card);
    }

    public List<CardResponse> getUserCards(Long userId) {
        List<Card> cards = cardRepository.findAllByUserId(userId);
        return cards.stream()
                .map(cardMapper::toResponse)
                .collect(Collectors.toList());
    }

    public BigDecimal getCardBalance(Long cardId, Long userId, UserRole roles) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNumberNotFoundException("Card not found"));

        if (roles.equals(UserRole.USER) && !card.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to view this card balance");
        }

        return card.getBalance();
    }
}
