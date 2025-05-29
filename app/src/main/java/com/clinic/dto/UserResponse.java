package com.clinic.dto;

import com.clinic.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    UUID id;
    String name;
    String email;
    Role role;
}
