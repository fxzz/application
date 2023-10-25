
CREATE TABLE Account (
    accountId SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    fullName VARCHAR(255),
    nickname VARCHAR(128) UNIQUE,
    role VARCHAR(255),
    createdAt TIMESTAMP,
    profileImage varchar(255),
    notificationEnabled boolean DEFAULT TRUE
);

CREATE TABLE Community (
    communityId SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    accountId INT NOT NULL,
    views INT DEFAULT 0,
    commentCount INT DEFAULT 0,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    likes INT DEFAULT 0,
    deleted VARCHAR(45),
    modifiedAt TIMESTAMP,
    imageEnabled boolean,
    FOREIGN KEY (accountId) REFERENCES Account(accountId)
);

CREATE TABLE Comment (
    commentId SERIAL PRIMARY KEY,
    communityId INT,
    parentCommentId INT,
    accountId INT,
    content VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedAt TIMESTAMP,
    deleted VARCHAR(45),
    FOREIGN KEY (communityId) REFERENCES Community(communityId),
    FOREIGN KEY (parentCommentId) REFERENCES Comment(commentId),
    FOREIGN KEY (accountId) REFERENCES Account(accountId)
);

CREATE TABLE Likes (
    likesId SERIAL PRIMARY KEY,
    communityId INT NOT NULL,
    accountId INT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    likesCount INT NOT NULL DEFAULT 0,
    UNIQUE (communityId, accountId),
    FOREIGN KEY (communityId) REFERENCES Community(communityId),
    FOREIGN KEY (accountId) REFERENCES Account(accountId)
);

CREATE TABLE Tag (
    tagId SERIAL PRIMARY KEY,
    tagTitle VARCHAR(50) NOT NULL
);

CREATE TABLE CommunityTag (
    communityTagId SERIAL PRIMARY KEY,
    communityId INT,
    tagId INT,
    FOREIGN KEY (communityId) REFERENCES Community(communityId),
    FOREIGN KEY (tagId) REFERENCES Tag(tagId)
);

CREATE TABLE persistent_logins (
    series VARCHAR(64) PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL
);

CREATE TABLE comment_report_history (
    reportId SERIAL PRIMARY KEY,
    accountId INT NOT NULL,
    commentId INT NOT NULL,
    reportedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (accountId) REFERENCES Account(accountId),
    FOREIGN KEY (commentId) REFERENCES Comment(commentId),
    UNIQUE (accountId, commentId)
);

CREATE TABLE community_report_history (
    reportId SERIAL PRIMARY KEY,
    accountId INT NOT NULL,
    communityId INT NOT NULL,
    reportedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (accountId) REFERENCES Account(accountId),
    FOREIGN KEY (communityId) REFERENCES Community(communityId),
    UNIQUE (accountId, communityId)
);

CREATE INDEX idx_communityTag_communityId ON communityTag (communityId);


CREATE TABLE notification (
    id SERIAL PRIMARY KEY,
    message VARCHAR(255),
    link VARCHAR(255),
    checked BOOLEAN,
    accountId BIGINT,
    createdAt TIMESTAMP
);

CREATE TABLE image (
id SERIAL PRIMARY KEY,
    newFilename  VARCHAR(255) NOT NULL,
    filename VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP NOT NULL,
    communityId BIGINT
);

CREATE TABLE login_failure (
   username VARCHAR(255) NOT NULL
);