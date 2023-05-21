package com.smartmemorize.backend.shareddeck;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedDeckRepository extends JpaRepository<SharedDeck, Long> {
}
