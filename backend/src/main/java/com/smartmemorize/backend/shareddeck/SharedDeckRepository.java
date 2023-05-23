package com.smartmemorize.backend.shareddeck;

import com.smartmemorize.backend.deck.Deck;
import com.smartmemorize.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SharedDeckRepository extends JpaRepository<SharedDeck, Long> {
    boolean existsByUserAndDeck(User user, Deck deck);
    void deleteByUserAndDeck(User user, Deck deck);
}
