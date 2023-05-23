package com.smartmemorize.backend.deck;

import com.smartmemorize.backend.card.CardService;
import com.smartmemorize.backend.deck.dto.CreateDeckDTO;
import com.smartmemorize.backend.deck.dto.DeckResponseDTO;
import com.smartmemorize.backend.deck.exceptions.UnauthorizedDeckAccessException;
import com.smartmemorize.backend.deck.util.DeckUtil;
import com.smartmemorize.backend.user.User;
import com.smartmemorize.backend.user.util.UserUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class DeckServiceImpl implements DeckService {
    private final UserUtil userUtil;
    private final DeckUtil deckUtil;
    private final DeckRepository deckRepository;
    private final CardService cardService;
    private final ModelMapper modelMapper;

    public DeckServiceImpl(UserUtil userUtil,
                           DeckUtil deckUtil,
                           DeckRepository deckRepository,
                           CardService cardService,
                           ModelMapper modelMapper) {
        this.userUtil = userUtil;
        this.deckUtil = deckUtil;
        this.deckRepository = deckRepository;
        this.cardService = cardService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void createDeck(CreateDeckDTO deck) {
        User user = userUtil.getUser();

        Deck newDeck = modelMapper.map(deck, Deck.class);
        newDeck.setOwner(user);

        deckRepository.save(newDeck);
    }

    @Override
    public Set<DeckResponseDTO> getAllDecks() {
        User user = userUtil.getUser();

        Set<Deck> decks = deckRepository.findByOwner(user);
        Set<DeckResponseDTO> deckResponseDTOs = new HashSet<>();

        for (Deck deck : decks) {
            DeckResponseDTO deckResponseDTO = modelMapper.map(deck, DeckResponseDTO.class);
            deckResponseDTO.setCards(cardService.getCardsByDeckId(deck.getId()));
            deckResponseDTOs.add(deckResponseDTO);
        }

        return deckResponseDTOs;
    }

    @Override
    public void deleteDeck(Long deckId) {
        User user = userUtil.getUser();
        Deck deck = deckUtil.getDeckById(deckId);

        if (!deck.isOwner(user)) {
            throw new UnauthorizedDeckAccessException("User is not authorized to delete deck with id: " + deckId);
        }

        deckRepository.delete(deck);
    }

    @Override
    public void updateDeck(Long deckId, CreateDeckDTO deck) {
        User user = userUtil.getUser();
        Deck deckToUpdate = deckUtil.getDeckById(deckId);

        if (!deckToUpdate.isOwner(user)) {
            throw new UnauthorizedDeckAccessException("User is not authorized to update deck with id: " + deckId);
        }

        deckRepository.save(modelMapper.map(deck, Deck.class));
    }
}
