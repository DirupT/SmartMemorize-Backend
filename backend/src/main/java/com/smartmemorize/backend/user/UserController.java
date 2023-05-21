package com.smartmemorize.backend.user;

import com.smartmemorize.backend.user.dto.CreateUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Creates a new user", description = "Creates a new user with the given email, username and password")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO user) {
        logger.info("/api/users POST request received: {}", user);
        userService.createUser(user);
        logger.info("/api/users created user: {}", user);
        return ResponseEntity.noContent().build();
    }
}
