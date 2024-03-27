using lab1.DTO.Interface;
using lab1.Models;

namespace lab1.DTO
{
    public class UserResponseTo(int Id, string? Login, string? Password, string? Firstname, string? Lastname, ICollection<News> News) : IResponseTo
    {
        public int Id { get; set; } = Id;

        public string? Login { get; set; } = Login;

        public string? Password { get; set; } = Password;

        public string? Firstname { get; set; } = Firstname;

        public string? Lastname { get; set; } = Lastname;

        public ICollection<News> News { get; set; } = News;
    }
}
