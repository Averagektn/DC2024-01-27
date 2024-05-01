package by.bsuir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponseTo {
    private Long id;
    private String title;
    private String content;
    private Long creatorId;
    private Long labelId;
}
