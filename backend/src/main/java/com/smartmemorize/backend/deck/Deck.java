package com.smartmemorize.backend.deck;

import com.smartmemorize.backend.card.Card;
import com.smartmemorize.backend.deckinvitation.DeckInvitation;
import com.smartmemorize.backend.shareddeck.SharedDeck;
import com.smartmemorize.backend.user.User;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "decks")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SharedDeck> sharedDecks = new HashSet<>();

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeckInvitation> deckInvitations = new ArrayList<>();

    public Deck() {}

    public Deck(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner(User user) {
        return owner.equals(user);
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<SharedDeck> getSharedDecks() {
        return sharedDecks;
    }

    public void setSharedDecks(Set<SharedDeck> sharedDecks) {
        this.sharedDecks = sharedDecks;
    }

    public void addSharedDeck(SharedDeck sharedDeck) {
        sharedDecks.add(sharedDeck);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<DeckInvitation> getDeckInvitations() {
        return deckInvitations;
    }

    public void addDeckInvitation(DeckInvitation newDeckInvitation) {
        deckInvitations.add(newDeckInvitation);
    }

    public void setDeckInvitations(List<DeckInvitation> deckInvitations) {
        this.deckInvitations = deckInvitations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Objects.equals(id, deck.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Deck{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner.getUsername() +
                ", sharedDecks=" + sharedDecks.size() +
                ", cards=" + cards.size() +
                '}';
    }
}
