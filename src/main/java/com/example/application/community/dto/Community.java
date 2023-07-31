package com.example.application.community.dto;

import com.example.application.account.dto.Account;
import lombok.*;

import java.time.LocalDateTime;

//@Getter
//@Setter
//@EqualsAndHashCode(of = "communityId")
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class Community {
//
//    private Long communityId;
//    private String title;
//    private String content;
//    private Account accountId;
//    private int views ;
//    private int commentCount ;
//    private LocalDateTime createdAt;
//    private int likes ;
//
//}

/*

CREATE TABLE community (
  communityId INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  accountId INT NOT NULL,
  views INT DEFAULT 0,
  commentCount INT DEFAULT 0,
  createdAt timestamp NOT NULL now(),
  likes INT DEFAULT 0,                //안씀
  FOREIGN KEY (accountId) REFERENCES account (accountId)
);

CREATE INDEX idx_communityTag_communityId ON communityTag (communityId);

*/
