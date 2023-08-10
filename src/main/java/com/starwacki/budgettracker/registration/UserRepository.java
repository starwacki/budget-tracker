package com.starwacki.budgettracker.registration;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
