package com.smartmemorize.backend.shareddeck;

public interface SharedDeckService {
    void shareDeck(Long deckId, Long userId);
    void inviteUser(Long deckId, Long userId);
    void acceptInvite(Long sharedDeckId);
    void rejectInvite(Long sharedDeckId);
    void removeUser(Long deckId, Long userId);
}
