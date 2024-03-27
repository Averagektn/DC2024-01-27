namespace lab1.Models
{
    public class News
    {
        public int Id { get; set; }

        public int? UserId { get; set; }

        public string? Title { get; set; }

        public string? Content { get; set; }

        public string? Created {  get; set; }

        public string? Modified {  get; set; }

        public virtual User? User { get; set; }

        public virtual ICollection<Note> Notes { get; set; } = new List<Note>();
    }
}
