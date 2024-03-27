using AutoMapper;
using lab1.DTO;
using lab1.Models;
using lab1.DTO.Interface;
using lab1.Services.Interface;

namespace lab1.Services
{
    public class UserService(IMapper _mapper, LabDbContext dbContext) : IUserService
    {
        public async Task<IResponseTo> CreateEntity(IRequestTo RequestDTO)
        {
            var UserDTO = (UserRequestTo)RequestDTO;

            if (!Validate(UserDTO))
            {
                throw new InvalidDataException("Incorrect data for CREATE user");
            }

            var User = _mapper.Map<User>(UserDTO);
            dbContext.Add(User);
            await dbContext.SaveChangesAsync();
            var response = _mapper.Map<UserResponseTo>(User);
            return response;
        }

        public async Task DeleteEntity(int id)
        {
            try
            {
                var User = await dbContext.Users.FindAsync(id);
                dbContext.Users.Remove(User!);
                await dbContext.SaveChangesAsync();
                return;
            }
            catch
            {
                throw new Exception("Deleting user exception");
            }
        }

        public IEnumerable<IResponseTo> GetAllEntity()
        {
            try
            {
                return dbContext.Users.Select(_mapper.Map<UserResponseTo>);
            }
            catch
            {
                throw new Exception("Getting all users exception");
            }
        }

        public async Task<IResponseTo> GetEntityById(int id) 
        {
            var User = await dbContext.Users.FindAsync(id);
            return (User is not null ? _mapper.Map<UserResponseTo>(User) : throw new ArgumentNullException($"Not found user: {id}"));
        }

        public async Task<IResponseTo> UpdateEntity(IRequestTo RequestDTO)
        {
            var UserDTO = (UserRequestTo)RequestDTO;

            if (!Validate(UserDTO))
            {
                throw new InvalidDataException("Incorrect data for UPDATE");

            }
            var newUser = _mapper.Map<User>(UserDTO);
            dbContext.Users.Update(newUser);
            await dbContext.SaveChangesAsync();
            var User = _mapper.Map<UserResponseTo>(await dbContext.Users.FindAsync(newUser.Id));
            return User;
        }

        private bool Validate(UserRequestTo UserDTO)
        {
            if (UserDTO?.Login?.Length < 2 || UserDTO?.Login?.Length > 64)
                return false;
            if (UserDTO?.Lastname?.Length < 2 && UserDTO?.Lastname?.Length > 64)
                return false;
            if (UserDTO?.Firstname?.Length < 2 && UserDTO?.Firstname?.Length > 64)
                return false;
            if (UserDTO?.Password?.Length < 8 && UserDTO?.Password?.Length > 128)
                return false;

            return true;
        }
    }
}
