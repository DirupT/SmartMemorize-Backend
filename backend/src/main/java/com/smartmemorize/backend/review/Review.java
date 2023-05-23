package com.smartmemorize.backend.review;

import com.smartmemorize.backend.card.Card;
import com.smartmemorize.backend.user.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "review_cards")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    private User user;

    @JoinColumn(name = "card_id")
    private Card card;

    public Review() {
    }

    public Review(User user, Card card) {
        this.user = user;
        this.card = card;
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

    public void setUser(User user) {
        this.user = user;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", card=" + card +
                '}';
    }
}
