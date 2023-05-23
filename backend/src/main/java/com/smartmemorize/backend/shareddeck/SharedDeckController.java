package com.smartmemorize.backend.shareddeck;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shared-decks")
public class SharedDeckController {
    private final Logger logger = LoggerFactory.getLogger(SharedDeckController.class);
    private final SharedDeckService service;

    public SharedDeckController(SharedDeckService service) {
        this.service = service;
    }

    @PostMapping("/{deckId}/share/{userId}")
    @Operation(summary = "Share a deck with a user", description = "Share the given deck with the given user")
    public ResponseEntity<Void> shareDeck(@PathVariable Long deckId, @PathVariable Long userId) {
        logger.info("/api/shared-decks/{}/share/{} POST request received", deckId, userId);
        service.shareDeck(deckId, userId);
        logger.info("/api/shared-decks/{}/share/{} shared deck with user", deckId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{deckId}/invite/{userId}")
    @Operation(summary = "Invite a user to a deck", description = "Invite the given user to the given deck")
    public ResponseEntity<Void> inviteUser(@PathVariable Long deckId, @PathVariable Long userId) {
        logger.info("/api/shared-decks/{}/invite/{} POST request received", deckId, userId);
        service.inviteUser(deckId, userId);
        logger.info("/api/shared-decks/{}/invite/{} invited user to deck", deckId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{deckId}/remove/{userId}")
    @Operation(summary = "Remove a user from a deck", description = "Remove the given user from the given deck")
    public ResponseEntity<Void> removeUser(@PathVariable Long deckId, @PathVariable Long userId) {
        logger.info("/api/shared-decks/{}/remove/{} POST request received", deckId, userId);
        service.removeUser(deckId, userId);
        logger.info("/api/shared-decks/{}/remove/{} removed user from deck", deckId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{inviteId}/accept")
    @Operation(summary = "Accept an invitation", description = "Accept an invitation with the given id")
    public ResponseEntity<Void> acceptInvitation(@PathVariable Long inviteId) {
        logger.info("/api/shared-decks/{}/accept POST request received", inviteId);
        service.acceptInvite(inviteId);
        logger.info("/api/shared-decks/{}/accept accepted invitation", inviteId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{inviteId}/reject")
    @Operation(summary = "Reject an invitation", description = "Reject an invitation with the given id")
    public ResponseEntity<Void> rejectInvitation(@PathVariable Long inviteId) {
        logger.info("/api/shared-decks/{}/reject POST request received", inviteId);
        service.rejectInvite(inviteId);
        logger.info("/api/shared-decks/{}/reject rejected invitation", inviteId);
        return ResponseEntity.noContent().build();
    }
}
