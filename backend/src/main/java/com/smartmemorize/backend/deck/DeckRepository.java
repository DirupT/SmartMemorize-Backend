package com.smartmemorize.backend.deck;

import com.smartmemorize.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    Set<Deck> findByOwner(User owner);
}
