package com.he.services;

import com.he.dto.UserDto;

public interface SignInService {
    UserDto signIn(String login, String password);
}
