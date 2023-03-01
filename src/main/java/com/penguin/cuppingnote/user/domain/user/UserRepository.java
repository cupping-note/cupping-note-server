package com.penguin.cuppingnote.user.domain.user;

import com.penguin.cuppingnote.oauth.domain.OAuthPlatforms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndOauthPlatform(String email, OAuthPlatforms oAuthPlatforms);
}
