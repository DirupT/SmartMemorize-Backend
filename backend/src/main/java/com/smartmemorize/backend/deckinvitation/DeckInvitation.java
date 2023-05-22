package com.smartmemorize.backend.deckinvitation;

import com.smartmemorize.backend.deck.Deck;
import com.smartmemorize.backend.user.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "deck_invitation")
public class DeckInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @Enumerated(EnumType.STRING)
    private InviteStatus status = InviteStatus.PENDING;

    public DeckInvitation() {
    }

    public DeckInvitation(User user, Deck deck) {
        this.user = user;
        this.deck = deck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public boolean isRecipient(User user) {
        return this.user.equals(user);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public InviteStatus getStatus() {
        return status;
    }

    public boolean isPending() {
        return status == InviteStatus.PENDING;
    }

    public void setStatus(InviteStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeckInvitation that = (DeckInvitation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DeckInvitation{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", deck=" + deck.getName() +
                '}';
    }
}
