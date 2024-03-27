using lab1.DTO.Interface;

namespace lab1.DTO
{
    public class NewsRequestTo(int Id, int? UserId, string? Title, string? Content) : IRequestTo
    {
        public int Id { get; set; } = Id;

        public int? UserId { get; set;} = UserId;

        public string? Title { get; set; } = Title;

        public string? Content { get; set; } = Content;
    }
}
