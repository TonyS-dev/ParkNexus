package org.codeup.parknexus.repository;

import org.codeup.parknexus.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}

