package com.example.application.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

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
    private Map<String, Object> oAuth2Attributes;
    private boolean notificationEnabled;

    @Getter
    @RequiredArgsConstructor
    public enum Role {
        ADMIN("어드민"), USER("유저");

        private final String description;
    }
}

