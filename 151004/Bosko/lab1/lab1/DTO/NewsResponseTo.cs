using lab1.DTO.Interface;
using lab1.Models;

namespace lab1.DTO
{
    public class NewsResponseTo(int Id, int? UserId, string? Title, string? Content, string? Created, string? Modified, User? User, ICollection<Note> Notes) : IResponseTo
    {
        public int Id { get; set; } = Id;

        public int? UserId { get; set; } = UserId;

        public string? Title { get; set; } = Title;

        public string? Content { get; set; } = Content;

        public string? Created { get; set; } = Created;

        public string? Modified { get; set;} = Modified;

        public User? User { get; set; } = User;

        public ICollection<Note> Notes { get; set; } = Notes;
    }
}
