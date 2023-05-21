package com.smartmemorize.backend.deck;

import com.smartmemorize.backend.deck.dto.CreateDeckDTO;
import com.smartmemorize.backend.deck.dto.DeckResponseDTO;

import java.util.Set;

public interface DeckService {
    void createDeck(CreateDeckDTO deck);
    Set<DeckResponseDTO> getAllDecks();
    void deleteDeck(Long deckId);
    void updateDeck(Long deckId, CreateDeckDTO deck);
}
