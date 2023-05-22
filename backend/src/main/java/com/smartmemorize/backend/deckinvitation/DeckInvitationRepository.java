package com.smartmemorize.backend.deckinvitation;

import com.smartmemorize.backend.deck.Deck;
import com.smartmemorize.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeckInvitationRepository extends JpaRepository<DeckInvitation, Long> {
    Optional<DeckInvitation> findByUserAndDeck(User user, Deck deck);
}
