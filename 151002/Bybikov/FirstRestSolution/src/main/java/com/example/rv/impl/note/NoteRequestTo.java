package com.example.rv.impl.note;

public record NoteRequestTo(
        Long id,
        Long tweetId,
        String content
) {}
