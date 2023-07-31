package com.example.application.domain;

import java.time.LocalDateTime;

public class Likes {
   private Long likeId;
   private Long communityId;
   private Long accountId;
   private LocalDateTime createdAt;
   private Long likesCount;

}

/*
CREATE TABLE likes (
  likesId INT AUTO_INCREMENT NOT NULL,
  communityId INT NOT NULL,
  accountId INT NOT NULL,
  createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  likesCount INT NOT NULL DEFAULT 0,
  PRIMARY KEY (likesId),
  UNIQUE (communityId, accountId),
  FOREIGN KEY (communityId) REFERENCES community (communityId),
  FOREIGN KEY (accountId) REFERENCES account (accountId)
);
*/