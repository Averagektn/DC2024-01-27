package by.bsuir.discussion.model.request;

import org.hibernate.validator.constraints.Length;

public record CommentRequestTo(
        Long id,
        Long storyId,
        @Length(min = 2, max = 2048)
        String content) 
{}
