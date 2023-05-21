package com.smartmemorize.backend.user;

import com.smartmemorize.backend.user.dto.CreateUserDTO;

public interface UserService {
    void createUser(CreateUserDTO user);
}
