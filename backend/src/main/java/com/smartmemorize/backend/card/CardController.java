package com.smartmemorize.backend.card;

import com.smartmemorize.backend.card.dto.CreateCardDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
@Tag(name = "Cards")
public class CardController {
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    @Operation(summary = "Creates a new card", description = "Creates a new card with the given front, back and deckId")
    public ResponseEntity<Void> createCard(@RequestBody CreateCardDTO card) {
        logger.info("/api/cards POST request received: {}", card);
        cardService.createCard(card);
        logger.info("/api/cards created card: {}", card);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cardId}")
    @Operation(summary = "Deletes a card", description = "Deletes a card with the given id")
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId) {
        logger.info("/api/cards DELETE request received: {}", cardId);
        cardService.deleteCard(cardId);
        logger.info("/api/cards deleted card: {}", cardId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cardId}")
    @Operation(summary = "Updates a card", description = "Updates a card with the given id")
    public ResponseEntity<Void> updateCard(@PathVariable Long cardId, @RequestBody CreateCardDTO card) {
        logger.info("/api/cards PUT request received: {}", cardId);
        cardService.updateCard(cardId, card);
        logger.info("/api/cards updated card: {}", cardId);
        return ResponseEntity.noContent().build();
    }
}
