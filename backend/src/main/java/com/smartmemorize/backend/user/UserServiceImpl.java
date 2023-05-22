package com.smartmemorize.backend.user;

import com.smartmemorize.backend.user.dto.CreateUserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(CreateUserDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(modelMapper.map(user, User.class));
    }
}
