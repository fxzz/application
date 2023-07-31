package com.example.application.domain;

import java.time.LocalDateTime;

public class Comment {

    private Long commentId;
    private Long communityId;
    private Long parentCommentId;
    private Long accountId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}


/*
  CREATE TABLE Comment (
    commentId INT AUTO_INCREMENT PRIMARY KEY,
    communityId INT,
    parentCommentId INT,
    accountId INT,
    content VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP now(),
    modifiedAt TIMESTAMP,
    isDeleted ENUM('0', '1') DEFAULT '0',
    FOREIGN KEY (communityId) REFERENCES Community(communityId),
    FOREIGN KEY (parentCommentId) REFERENCES Comment(commentId),
    FOREIGN KEY (accountId) REFERENCES Account(accountId)
);
 */
