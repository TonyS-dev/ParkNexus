package org.codeup.parknexus.security;

import org.codeup.parknexus.domain.User;

public interface TokenProvider {
    String generateToken(User user);
}
