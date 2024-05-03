package by.bsuir.dc.publisher.services.exceptions;

import lombok.Getter;

@Getter
public enum MessageSubCode {

    STUB(0, "Stub"),

    //422 - Unprocessable entity
    WRONG_CONTENT_LEN(1, "Label's name length must be from 2 to 32"),
    CONTENT_IS_NULL(2, "Content must be not null"),
    STORY_ID_IS_NULL(3, "Story id must be not null"),

    //403 - Forbidden
    CONSTRAINT_VIOLATION(4, ""),

    DISCUSSION_TIMEOUT(5, "Discussion server timeout");

    private final int subCode;

    private final String message;

    MessageSubCode(int subCode, String message) {
        this.subCode = subCode;
        this.message = message;
    }

}
