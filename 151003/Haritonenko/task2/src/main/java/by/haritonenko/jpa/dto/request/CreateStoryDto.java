package by.haritonenko.jpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStoryDto {

    @NotNull(message = "authorId must not be empty")
    @PositiveOrZero(message = "authorId must be positive or zero")
    private Long authorId;

    @NotBlank(message = "Title must not be empty")
    @Size(max = 64, message = "Title must not exceed 64 characters")
    @Size(min = 2, message = "Title must be at least 2 characters long")
    private String title;

    @NotBlank(message = "Content must not be empty")
    @Size(max = 2048, message = "Content must not exceed 2048 characters")
    @Size(min = 4, message = "Content must be at least 4 characters long")
    private String content;
}
