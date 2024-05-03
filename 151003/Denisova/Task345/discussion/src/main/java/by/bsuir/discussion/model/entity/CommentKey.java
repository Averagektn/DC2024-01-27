package by.bsuir.discussion.model.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.Locale;

@Getter
@Setter
@EqualsAndHashCode
@PrimaryKeyClass
public class CommentKey {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private Locale country;

    @PrimaryKeyColumn(
            name = "story_id",
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 0
    )
    private Long storyId;

    @PrimaryKeyColumn(
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 1
    )
    private Long id;
}