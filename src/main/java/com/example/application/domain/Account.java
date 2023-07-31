package com.example.application.domain;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@EqualsAndHashCode(of = "accountId")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long accountId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String nickname;
    private Role role;
    private LocalDateTime createdAt;
    private String profileImage;


}


/*
CREATE TABLE Account (
accountId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
email VARCHAR(255) UNIQUE,
fullName VARCHAR(255),
nickname VARCHAR(128) UNIQUE,
role VARCHAR(255),
createdAt TIMESTAMP
)
 */