package com.example.application.account.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@EqualsAndHashCode(of = "accountId")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

    private Long accountId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String nickname;
    private Role role;
    private LocalDateTime createdAt;
    private String profileImage;

    @Getter
    @RequiredArgsConstructor
    public enum Role {
        ADMIN("어드민"), USER("유저");

        private final String description;
    }
}

