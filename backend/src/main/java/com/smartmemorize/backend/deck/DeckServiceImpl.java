package com.smartmemorize.backend.deck;

import com.smartmemorize.backend.card.CardService;
import com.smartmemorize.backend.deck.dto.CreateDeckDTO;
import com.smartmemorize.backend.deck.dto.DeckResponseDTO;
import com.smartmemorize.backend.deck.exceptions.DeckNotFoundException;
import com.smartmemorize.backend.deck.exceptions.UnauthorizedDeckAccessException;
import com.smartmemorize.backend.user.User;
import com.smartmemorize.backend.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class DeckServiceImpl implements DeckService {
    private final UserRepository userRepository;
    private final DeckRepository deckRepository;
    private final CardService cardService;
    private final ModelMapper modelMapper;

    public DeckServiceImpl(UserRepository userRepository,
                           DeckRepository deckRepository,
                           CardService cardService,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.deckRepository = deckRepository;
        this.cardService = cardService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void createDeck(CreateDeckDTO deck) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));

        Deck newDeck = modelMapper.map(deck, Deck.class);
        newDeck.setOwner(user);
        user.addOwnedDeck(newDeck);

        deckRepository.save(newDeck);
    }

    @Override
    public Set<DeckResponseDTO> getAllDecks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));

        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new DeckNotFoundException("Deck not found with id: " + deckId));

        if (!deck.getOwner().equals(user)) {
            throw new UnauthorizedDeckAccessException("User is not authorized to delete deck with id: " + deckId);
        }

        deckRepository.delete(deck);
    }

    @Override
    public void updateDeck(Long deckId, CreateDeckDTO deck) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
               .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));

        Deck deckToUpdate = deckRepository.findById(deckId)
               .orElseThrow(() -> new DeckNotFoundException("Deck not found with id: " + deckId));

        if (!deckToUpdate.getOwner().equals(user)) {
            throw new UnauthorizedDeckAccessException("User is not authorized to update deck with id: " + deckId);
        }

        deckRepository.save(modelMapper.map(deck, Deck.class));
    }
}
