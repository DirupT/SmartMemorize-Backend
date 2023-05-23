package com.smartmemorize.backend.shareddeck;

import com.smartmemorize.backend.card.Card;
import com.smartmemorize.backend.deck.Deck;
import com.smartmemorize.backend.deck.exceptions.UnauthorizedDeckAccessException;
import com.smartmemorize.backend.deck.util.DeckUtil;
import com.smartmemorize.backend.deckinvitation.DeckInvitation;
import com.smartmemorize.backend.deckinvitation.DeckInvitationRepository;
import com.smartmemorize.backend.deckinvitation.exception.InvitationNotFoundException;
import com.smartmemorize.backend.deckinvitation.exception.UserNotRecipientException;
import com.smartmemorize.backend.review.Review;
import com.smartmemorize.backend.deckinvitation.exception.InviteAlreadyExistsException;
import com.smartmemorize.backend.shareddeck.exception.UserAlreadyMemberException;
import com.smartmemorize.backend.user.User;
import com.smartmemorize.backend.user.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SharedDeckServiceImpl implements SharedDeckService {
    private final UserUtil userUtil;
    private final DeckUtil deckUtil;
    private final SharedDeckRepository sharedDeckRepository;
    private final DeckInvitationRepository deckInvitationRepository;

    public SharedDeckServiceImpl(UserUtil userUtil,
                                 DeckUtil deckUtil,
                                 SharedDeckRepository sharedDeckRepository,
                                 DeckInvitationRepository deckInvitationRepository) {
        this.userUtil = userUtil;
        this.deckUtil = deckUtil;
        this.sharedDeckRepository = sharedDeckRepository;
        this.deckInvitationRepository = deckInvitationRepository;
    }

    @Override
    @Transactional
    public void shareDeck(Long deckId, Long userId) {
        User user = userUtil.getUser();
        Deck deck = deckUtil.getDeckById(deckId);

        if (!deck.isOwner(user)) {
            throw new UnauthorizedDeckAccessException("User is not authorized to share deck with id: " + deckId);
        }

        User recipient = userUtil.getUserById(userId);
        Deck newDeck = new Deck(deck.getName(), recipient);

        for (Card card : deck.getCards()) {
            Card newCard = new Card(card.getFront(), card.getBack(), newDeck);
            Review review = new Review(recipient, newCard);

            newDeck.addCard(newCard);
            newCard.addReview(review);
        }

        recipient.addOwnedDeck(newDeck);
    }

    @Override
    @Transactional
    public void inviteUser(Long deckId, Long userId) {
        User user = userUtil.getUser();
        Deck deck = deckUtil.getDeckById(deckId);

        if (!deck.isOwner(user)) {
            throw new UnauthorizedDeckAccessException("User is not authorized to invite to deck with id: " + deckId);
        }

        if (sharedDeckRepository.existsByUserAndDeck(user, deck)) {
            throw new UserAlreadyMemberException("User is already a member of the deck with id: " + deckId);
        }

        deckInvitationRepository.findByUserAndDeck(user, deck)
                .filter(DeckInvitation::isPending)
                .ifPresent(invitation -> {
                    throw new InviteAlreadyExistsException("User is already invited to deck with id: " + deck);
                });

        User recipient = userUtil.getUserById(userId);
        DeckInvitation newDeckInvitation = new DeckInvitation(recipient, deck);

        deckInvitationRepository.save(newDeckInvitation);
    }

    @Override
    @Transactional
    public void acceptInvite(Long inviteId) {
        User user = userUtil.getUser();

        DeckInvitation invitation = deckInvitationRepository.findById(inviteId)
                .orElseThrow(() -> new InvitationNotFoundException("Invitation not found with id: " + inviteId));

        if (!invitation.isRecipient(user)) {
            throw new UserNotRecipientException("User is not the recipient for invitation with id: " + inviteId);
        }

        Deck deck = invitation.getDeck();

        for (Card card : deck.getCards()) {
            card.addReview(new Review(user, card));
        }

        sharedDeckRepository.save(new SharedDeck(user, deck));
        deckInvitationRepository.delete(invitation);
    }

    @Override
    @Transactional
    public void rejectInvite(Long inviteId) {
        User user = userUtil.getUser();

        DeckInvitation invitation = deckInvitationRepository.findById(inviteId)
              .orElseThrow(() -> new InvitationNotFoundException("Invitation not found with id: " + inviteId));

        if (!invitation.isRecipient(user)) {
            throw new UserNotRecipientException("User is not the recipient for invitation with id: " + inviteId);
        }

        deckInvitationRepository.delete(invitation);
    }

    @Override
    @Transactional
    public void removeUser(Long deckId, Long userId) {
        User user = userUtil.getUser();
        Deck deck = deckUtil.getDeckById(deckId);

        if (!deck.isOwner(user)) {
            throw new UnauthorizedDeckAccessException("User is not authorized to remove user from deck with id: " + deckId);
        }

        User recipient = userUtil.getUserById(userId);

        if (!sharedDeckRepository.existsByUserAndDeck(recipient, deck)) {
            throw new UserAlreadyMemberException("User is not a member of the deck with id: " + deckId);
        }

        sharedDeckRepository.deleteByUserAndDeck(recipient, deck);
    }
}
