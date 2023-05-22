package com.smartmemorize.backend.user;

import com.smartmemorize.backend.deck.Deck;
import com.smartmemorize.backend.deckinvitation.DeckInvitation;
import com.smartmemorize.backend.review.Review;
import com.smartmemorize.backend.shareddeck.SharedDeck;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Deck> ownedDecks = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<SharedDeck> sharedDecks = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeckInvitation> deckInvitations = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Deck> getOwnedDecks() {
        return ownedDecks;
    }

    public void addOwnedDeck(Deck deck) {
        ownedDecks.add(deck);
    }

    public Set<SharedDeck> getSharedDecks() {
        return sharedDecks;
    }

    public void addSharedDeck(SharedDeck sharedDeck) {
        sharedDecks.add(sharedDeck);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
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
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ownedDecks=" + ownedDecks.size() +
                ", sharedDecks=" + sharedDecks.size() +
                '}';
    }
}
