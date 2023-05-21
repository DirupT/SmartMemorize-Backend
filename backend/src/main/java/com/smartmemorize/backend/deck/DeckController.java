package com.smartmemorize.backend.deck;

import com.smartmemorize.backend.deck.dto.CreateDeckDTO;
import com.smartmemorize.backend.deck.dto.DeckResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/decks")
@Tag(name = "Decks")
public class DeckController {
    private final Logger logger = LoggerFactory.getLogger(DeckController.class);
    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @PostMapping
    @Operation(summary = "Creates a new deck", description = "Creates a new deck with the given name")
    public ResponseEntity<Void> createDeck(@RequestBody CreateDeckDTO deck) {
        logger.info("/api/decks POST request received: {}", deck);
        deckService.createDeck(deck);
        logger.info("/api/decks created deck: {}", deck);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Gets all decks", description = "Gets all decks for a given user")
    public ResponseEntity<Set<DeckResponseDTO>> getAllDecks() {
        logger.info("/api/decks GET request received");
        Set<DeckResponseDTO> decks = deckService.getAllDecks();
        logger.info("/api/decks got all decks: {}", decks);
        return ResponseEntity.ok(decks);
    }

    @DeleteMapping("/{deckId}")
    @Operation(summary = "Deletes a deck", description = "Deletes a deck with the given id")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long deckId) {
        logger.info("/api/decks DELETE request received: {}", deckId);
        deckService.deleteDeck(deckId);
        logger.info("/api/decks deleted deck: {}", deckId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{deckId}")
    @Operation(summary = "Updates a deck", description = "Updates a deck with the given id")
    public ResponseEntity<Void> updateDeck(@PathVariable Long deckId, @RequestBody CreateDeckDTO createDeckDTO) {
        logger.info("/api/decks PUT request received: {}", deckId);
        deckService.updateDeck(deckId, createDeckDTO);
        logger.info("/api/decks updated deck: {}", deckId);
        return ResponseEntity.noContent().build();
    }
}
