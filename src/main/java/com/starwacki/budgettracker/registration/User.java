package com.starwacki.budgettracker.registration;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "users")
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

}
