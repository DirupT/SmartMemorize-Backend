package com.smartmemorize.backend.shareddeck;

import com.smartmemorize.backend.card.Card;
import com.smartmemorize.backend.deck.Deck;
import com.smartmemorize.backend.deck.DeckRepository;
import com.smartmemorize.backend.deck.exceptions.UnauthorizedDeckAccessException;
import com.smartmemorize.backend.deck.util.DeckUtil;
import com.smartmemorize.backend.review.Review;
import com.smartmemorize.backend.user.User;
import com.smartmemorize.backend.user.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SharedDeckServiceImpl implements SharedDeckService {
    private final UserUtil userUtil;
    private final DeckUtil deckUtil;
    private final SharedDeckRepository sharedDeckRepository;
    private final DeckRepository deckRepository;

    public SharedDeckServiceImpl(UserUtil userUtil,
                                 DeckUtil deckUtil,
                                 SharedDeckRepository sharedDeckRepository,
                                 DeckRepository deckRepository) {
        this.userUtil = userUtil;
        this.deckUtil = deckUtil;
        this.sharedDeckRepository = sharedDeckRepository;
        this.deckRepository = deckRepository;
    }

    @Override
    @Transactional
    public void shareDeck(Long deckId, Long userId) {
        User user = userUtil.getUser();
        User recipient = userUtil.getUserById(userId);

        Deck deck = deckUtil.getDeckById(deckId);

        if (!deck.isOwner(user)) {
            throw new UnauthorizedDeckAccessException("User is not authorized to share deck with id: " + deckId);
        }

        Deck newDeck = new Deck(deck.getName(), recipient);

        for (Card card : deck.getCards()) {
            Card newCard = new Card(card.getFront(), card.getBack(), newDeck);
            Review review = new Review(recipient, newCard);

            newDeck.addCard(newCard);
            newCard.addReview(review);
        }

        recipient.addOwnedDeck(newDeck);
        deckRepository.save(newDeck);
    }

    @Override
    @Transactional
    public void inviteUser(Long deckId, Long userId) {
        User user = userUtil.getUser();
        User recipient = userUtil.getUserById(userId);

        Deck deck = deckUtil.getDeckById(deckId);

        if (!deck.isOwner(user)) {
            throw new UnauthorizedDeckAccessException("User is not authorized to invite to deck with id: " + deckId);
        }

        SharedDeck sharedDeck = new SharedDeck(recipient, deck);

        recipient.addSharedDeck(sharedDeck);
        deck.addSharedDeck(sharedDeck);

        sharedDeckRepository.save(sharedDeck);
    }
}
