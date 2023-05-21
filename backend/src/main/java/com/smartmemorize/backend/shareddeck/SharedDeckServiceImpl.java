package com.smartmemorize.backend.shareddeck;

import com.smartmemorize.backend.deck.Deck;
import com.smartmemorize.backend.deck.DeckRepository;
import com.smartmemorize.backend.deck.exceptions.DeckNotFoundException;
import com.smartmemorize.backend.deck.exceptions.UnauthorizedDeckAccessException;
import com.smartmemorize.backend.user.User;
import com.smartmemorize.backend.user.UserRepository;
import com.smartmemorize.backend.user.exceptions.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SharedDeckServiceImpl implements SharedDeckService {
    private final SharedDeckRepository sharedDeckRepository;
    private final UserRepository userRepository;
    private final DeckRepository deckRepository;

    public SharedDeckServiceImpl(SharedDeckRepository sharedDeckRepository,
                                 UserRepository userRepository,
                                 DeckRepository deckRepository) {
        this.sharedDeckRepository = sharedDeckRepository;
        this.userRepository = userRepository;
        this.deckRepository = deckRepository;
    }

    @Override
    @Transactional
    public void shareDeck(Long deckId, Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: {}" + authentication.getName()));

        User recipient = userRepository.findById(userId)
              .orElseThrow(() -> new UserNotFoundException("User not found with id: {}" + userId));

        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new DeckNotFoundException("Deck not found with id: {}" + deckId));

        // Only owner can share deck
        if (!deck.getOwner().equals(user)) {
            throw new UnauthorizedDeckAccessException("User is not authorized to share deck with id: " + deckId);
        }

        SharedDeck sharedDeck = new SharedDeck(recipient, deck);

        recipient.addSharedDeck(sharedDeck);
        deck.addSharedDeck(sharedDeck);

        sharedDeckRepository.save(sharedDeck);
    }
}
