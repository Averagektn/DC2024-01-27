using lab1.DTO.Interface;

namespace lab1.Services.Interface
{
    public interface IService
    {
        public IEnumerable<IResponseTo> GetAllEntity();

        public Task<IResponseTo> GetEntityById(int id);

        public Task<IResponseTo> CreateEntity(IRequestTo RequestDTO);

        public Task<IResponseTo> UpdateEntity(IRequestTo RequestDTO);

        public Task DeleteEntity(int id);
    }
}
