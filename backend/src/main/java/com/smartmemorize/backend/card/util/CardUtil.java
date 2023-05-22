package com.smartmemorize.backend.card.util;

import com.smartmemorize.backend.card.Card;
import com.smartmemorize.backend.card.CardRepository;
import com.smartmemorize.backend.card.exceptions.CardNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CardUtil {
    private final CardRepository cardRepository;

    public CardUtil(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card getCardById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found with id: " + id));
    }
}
