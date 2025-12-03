package org.codeup.parknexus.service;

import org.codeup.parknexus.web.dto.auth.AuthResponse;
import org.codeup.parknexus.web.dto.auth.LoginRequest;
import org.codeup.parknexus.web.dto.auth.RegisterRequest;

public interface IAuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}

