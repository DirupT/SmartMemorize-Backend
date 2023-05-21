package com.smartmemorize.backend.card;

import com.smartmemorize.backend.card.dto.CardResponseDTO;
import com.smartmemorize.backend.card.dto.CreateCardDTO;

import java.util.List;

public interface CardService {
    void createCard(CreateCardDTO card);
    List<CardResponseDTO> getCardsByDeckId(Long deckId);
    void deleteCard(Long cardId);
    void updateCard(Long cardId, CreateCardDTO card);
}
