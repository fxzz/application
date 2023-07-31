package com.example.application.domain;

public class CommunityTag {

    private Long communityTagId;
    private Long communityId;
    private Long tagId;

}
/*

  CREATE TABLE communityTag (
  communityTagId INT PRIMARY KEY AUTO_INCREMENT,
  communityId INT,
  tagId INT,
  FOREIGN KEY (communityId) REFERENCES community (communityId),
  FOREIGN KEY (tagId) REFERENCES tag (tagId)
);

*/
