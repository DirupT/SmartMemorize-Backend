package com.smartmemorize.backend.deck.util;

import com.smartmemorize.backend.deck.Deck;
import com.smartmemorize.backend.deck.DeckRepository;
import com.smartmemorize.backend.deck.exceptions.DeckNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DeckUtil {
    private final DeckRepository deckRepository;

    public DeckUtil(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public Deck getDeckById(Long deckId) {
        return deckRepository.findById(deckId)
                .orElseThrow(() -> new DeckNotFoundException("Deck not found with id: " + deckId));
    }
}
