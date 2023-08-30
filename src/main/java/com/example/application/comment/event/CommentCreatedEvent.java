package com.example.application.comment.event;

import com.example.application.comment.dto.InsertCommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
public class CommentCreatedEvent {

    private final InsertCommentDto comment;

    public CommentCreatedEvent(InsertCommentDto comment) {
        this.comment = comment;
    }
}