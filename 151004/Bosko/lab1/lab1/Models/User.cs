namespace lab1.Models
{
    public class User
    {
        public int Id { get; set; }

        public string? Login { get; set; }

        public string? Password { get; set; }

        public string? Firstname { get; set; }

        public string? Lastname { get; set;}

        public virtual ICollection<News> News { get; set;} = new List<News>();
    }
}
