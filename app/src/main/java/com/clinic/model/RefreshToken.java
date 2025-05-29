package com.clinic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private User user;
}
