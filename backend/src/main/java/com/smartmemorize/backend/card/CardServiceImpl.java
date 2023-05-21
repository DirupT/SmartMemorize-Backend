package com.smartmemorize.backend.card;

import com.smartmemorize.backend.card.dto.CardResponseDTO;
import com.smartmemorize.backend.card.dto.CreateCardDTO;
import com.smartmemorize.backend.card.exceptions.CardNotFoundException;
import com.smartmemorize.backend.card.exceptions.UnauthorizedCardAccessException;
import com.smartmemorize.backend.deck.Deck;
import com.smartmemorize.backend.deck.DeckRepository;
import com.smartmemorize.backend.deck.exceptions.DeckNotFoundException;
import com.smartmemorize.backend.user.User;
import com.smartmemorize.backend.user.UserRepository;
import com.smartmemorize.backend.review.Review;
import com.smartmemorize.backend.review.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    private final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public CardServiceImpl(UserRepository userRepository,
                           CardRepository cardRepository,
                           DeckRepository deckRepository,
                           ReviewRepository reviewRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void createCard(CreateCardDTO card) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));

        Deck deck = deckRepository.findById(card.getDeckId())
                .orElseThrow(() -> new DeckNotFoundException("Deck not found with id: " + card.getDeckId()));

        Card newCard = new Card(card.getFront(), card.getBack(), deck);
        Review userReview = reviewRepository.save(new Review(user, newCard));

        deck.addCard(newCard);
        newCard.addReview(userReview);
        user.addReview(userReview);

        cardRepository.save(newCard);
    }

    @Override
    public List<CardResponseDTO> getCardsByDeckId(Long deckId) {
        return cardRepository.findAllByDeckId(deckId)
                .stream()
                .map(card -> modelMapper.map(card, CardResponseDTO.class))
                .toList();
    }

    @Override
    public void deleteCard(Long cardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("Card not found with id: " + cardId));

        if (!card.getDeck().getOwner().equals(user)) {
            throw new UnauthorizedCardAccessException("User is not authorized to delete card with id: " + cardId);
        }

        cardRepository.delete(card);
    }

    @Override
    public void updateCard(Long cardId, CreateCardDTO card) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
               .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));

        Card cardToUpdate = cardRepository.findById(cardId)
               .orElseThrow(() -> new CardNotFoundException("Card not found with id: " + cardId));

        if (!cardToUpdate.getDeck().getOwner().equals(user)) {
            throw new UnauthorizedCardAccessException("User is not authorized to update card with id: " + cardId);
        }

        cardRepository.save(modelMapper.map(card, Card.class));
    }
}
