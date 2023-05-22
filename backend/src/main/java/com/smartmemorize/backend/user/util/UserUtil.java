package com.smartmemorize.backend.user.util;

import com.smartmemorize.backend.user.User;
import com.smartmemorize.backend.user.UserRepository;
import com.smartmemorize.backend.user.exceptions.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {
    private final UserRepository userRepository;

    public UserUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName())
              .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
               .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
}
