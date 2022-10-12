package com.CursorHomeWorks15.repository;

import com.CursorHomeWorks15.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<AppUser, Integer> {
    boolean existsByUsername(String username);

    AppUser findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);
}
