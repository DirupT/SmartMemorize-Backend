package com.smartmemorize.backend.deck.dto;

import com.smartmemorize.backend.card.dto.CardResponseDTO;

import java.util.List;

public class DeckResponseDTO {
    private String name;
    private List<CardResponseDTO> cards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CardResponseDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardResponseDTO> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "DeckResponseDTO{" +
                "name='" + name + '\'' +
                ", cards=" + cards.size() +
                '}';
    }
}
