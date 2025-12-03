package org.codeup.parknexus.security;

import org.codeup.parknexus.domain.User;

public class JwtTokenProvider implements TokenProvider {
    @Override
    public String generateToken(User user) {
        return "";
    }
}

