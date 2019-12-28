package com.he.server.services;

import com.he.server.dto.UserDto;

public interface SignInService {
    UserDto signIn(String login, String password);
}
