package org.codeup.parknexus.service.impl;

import org.codeup.parknexus.service.IAuthService;
import org.codeup.parknexus.web.dto.auth.AuthResponse;
import org.codeup.parknexus.web.dto.auth.LoginRequest;
import org.codeup.parknexus.web.dto.auth.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {
    @Override
    public AuthResponse register(RegisterRequest request) {
        return null;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}

