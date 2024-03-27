namespace lab1.Models
{
    public class Note
    {
        public int Id { get; set; }

        public int? NewsId { get; set; }

        public string? Content { get; set; }

        public virtual News? News { get; set; }
    }
}
