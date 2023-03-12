package com.penguin.cuppingnote.pref.domain;

import com.penguin.cuppingnote.user.domain.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrefRepository extends JpaRepository<UserPreference, Long> {
}
