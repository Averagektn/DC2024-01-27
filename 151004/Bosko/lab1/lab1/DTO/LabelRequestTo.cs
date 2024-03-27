using lab1.DTO.Interface;

namespace lab1.DTO
{
    public class LabelRequestTo(int Id, string? Name) : IRequestTo
    {
        public int Id { get; set; } = Id;

        public string? Name { get; set; } = Name;
    }
}
