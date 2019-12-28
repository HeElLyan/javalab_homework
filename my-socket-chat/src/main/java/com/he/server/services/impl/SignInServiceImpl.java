package com.he.server.services.impl;

import com.he.server.dto.UserDto;
import com.he.server.models.User;
import com.he.server.repositories.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.he.context.Component;
import com.he.server.services.SignInService;

import java.util.Optional;

public class SignInServiceImpl implements SignInService, Component {
    private UsersRepository usersRepository;

    @Override
    public UserDto signIn(String login, String password) {
        Optional<User> userCandidate = usersRepository.findByLogin(login);
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = userCandidate.get().getPassword();
            if (encoder.matches(password, hash)) {
                return UserDto.from(user);
            }
        }
        throw new IllegalArgumentException("Incorrect login or password");
    }

    @Override
    public String getName() {
        return "signInService";
    }

    @Override
    public String toString() {
        return "SignInServiceImpl{" +
                "usersRepository=" + usersRepository +
                '}';
    }
}
