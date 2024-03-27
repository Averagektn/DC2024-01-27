using lab1.DTO.Interface;

namespace lab1.DTO
{
    public class LabelResponseTo(int Id, string? Name) : IResponseTo
    {
        public int Id { get; set; } = Id;

        public string? Name { get; set; } = Name;
    }
}
