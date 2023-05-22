package com.smartmemorize.backend.card;

import com.smartmemorize.backend.card.dto.CardResponseDTO;
import com.smartmemorize.backend.card.dto.CreateCardDTO;
import com.smartmemorize.backend.card.exceptions.UnauthorizedCardAccessException;
import com.smartmemorize.backend.card.util.CardUtil;
import com.smartmemorize.backend.deck.Deck;
import com.smartmemorize.backend.deck.util.DeckUtil;
import com.smartmemorize.backend.user.User;
import com.smartmemorize.backend.review.Review;
import com.smartmemorize.backend.review.ReviewRepository;
import com.smartmemorize.backend.user.util.UserUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    private final UserUtil userUtil;
    private final DeckUtil deckUtil;
    private final CardUtil cardUtil;
    private final CardRepository cardRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public CardServiceImpl(UserUtil userUtil,
                           DeckUtil deckUtil,
                           CardUtil cardUtil,
                           CardRepository cardRepository,
                           ReviewRepository reviewRepository,
                           ModelMapper modelMapper) {
        this.userUtil = userUtil;
        this.deckUtil = deckUtil;
        this.cardUtil = cardUtil;
        this.cardRepository = cardRepository;
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void createCard(CreateCardDTO card) {
        User user = userUtil.getUser();
        Deck deck = deckUtil.getDeckById(card.getDeckId());

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
        User user = userUtil.getUser();
        Card card = cardUtil.getCardById(cardId);

        if (!card.getDeck().isOwner(user)) {
            throw new UnauthorizedCardAccessException("User is not authorized to delete card with id: " + cardId);
        }

        cardRepository.delete(card);
    }

    @Override
    public void updateCard(Long cardId, CreateCardDTO card) {
        User user = userUtil.getUser();
        Card cardToUpdate = cardUtil.getCardById(cardId);

        if (!cardToUpdate.getDeck().isOwner(user)) {
            throw new UnauthorizedCardAccessException("User is not authorized to update card with id: " + cardId);
        }

        cardRepository.save(modelMapper.map(card, Card.class));
    }
}
