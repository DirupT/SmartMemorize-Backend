package com.smartmemorize.backend.shareddeck;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shared-decks")
public class SharedDeckController {
    private final SharedDeckService service;

    public SharedDeckController(SharedDeckService service) {
        this.service = service;
    }

    @PostMapping("/{deckId}/share/{userId}")
    @Operation(summary = "Share a deck with a user", description = "Share the given deck with the given user.")
    public ResponseEntity<Void> shareDeck(@PathVariable Long deckId, @PathVariable Long userId) {
        service.shareDeck(deckId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{deckId}/invite/{userId}")
    @Operation(summary = "Invite a user to a deck", description = "Invite the given user to the given deck.")
    public ResponseEntity<Void> inviteUser(@PathVariable Long deckId, @PathVariable Long userId) {
        service.inviteUser(deckId, userId);
        return ResponseEntity.noContent().build();
    }
}
