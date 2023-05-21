package com.smartmemorize.backend.shareddeck;

import com.smartmemorize.backend.deck.Deck;
import com.smartmemorize.backend.user.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "shared_decks")
public class SharedDeck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;

    public SharedDeck() {
    }

    public SharedDeck(User user, Deck deck) {
        this.user = user;
        this.deck = deck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SharedDeck that = (SharedDeck) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SharedDeck{" +
                "id=" + id +
                ", user=" + user +
                ", deck=" + deck +
                '}';
    }
}
