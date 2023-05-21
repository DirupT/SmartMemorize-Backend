package com.smartmemorize.backend.config;

import com.smartmemorize.backend.card.Card;
import com.smartmemorize.backend.card.CardRepository;
import com.smartmemorize.backend.deck.Deck;
import com.smartmemorize.backend.deck.DeckRepository;
import com.smartmemorize.backend.shareddeck.SharedDeckRepository;
import com.smartmemorize.backend.user.User;
import com.smartmemorize.backend.user.UserRepository;
import com.smartmemorize.backend.review.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   DeckRepository deckRepository,
                                   CardRepository cardRepository,
                                   SharedDeckRepository sharedDeckRepository,
                                   ReviewRepository reviewRepository) {
        return args -> {
//            User user1 = new User();
//
//            Deck deck1 = new Deck();
//            deck1.setOwner(user1);
//
//            Card card1 = new Card();
//            card1.setDeck(deck1);
//
//            userRepository.save(user1);
//            deckRepository.save(deck1);
//            cardRepository.save(card1);
        };
    }
}
