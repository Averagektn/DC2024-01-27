﻿namespace REST.Entity.DTO.RequestTO
{
    public record class TweetRequestTO(int AuthorId, string Title, string Content, DateTime Created, DateTime Modified);
}
