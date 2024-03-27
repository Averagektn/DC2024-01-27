using AutoMapper;
using lab1.DTO;
using lab1.DTO.Interface;
using lab1.Models;
using lab1.Services.Interface;

namespace lab1.Services
{
    public class NoteService(IMapper _mapper, LabDbContext dbContext) : INoteService
    {
        public async Task<IResponseTo> CreateEntity(IRequestTo RequestDTO)
        {
            var NoteDTO = (NoteRequestTo)RequestDTO;

            if (!Validate(NoteDTO))
            {
                throw new InvalidDataException("Incorrect data for CREATE note");
            }

            var Note = _mapper.Map<Note>(NoteDTO);
            dbContext.Add(Note);
            await dbContext.SaveChangesAsync();
            var response = _mapper.Map<NoteResponseTo>(Note);
            return response;
        }

        public async Task DeleteEntity(int id)
        {
            try
            {
                var Note = await dbContext.Notes.FindAsync(id);
                dbContext.Notes.Remove(Note!);
                await dbContext.SaveChangesAsync();
                return;
            }
            catch
            {
                throw new Exception("Deleting note exception");
            }
        }

        public IEnumerable<IResponseTo> GetAllEntity()
        {
            try
            {
                return dbContext.Notes.Select(_mapper.Map<NoteResponseTo>);
            }
            catch
            {
                throw new Exception("Getting all notes exception");
            }
        }

        public async Task<IResponseTo> GetEntityById(int id)
        {
            var Note = await dbContext.Notes.FindAsync(id);
            return (Note is not null ? _mapper.Map<NoteResponseTo>(Note) : throw new ArgumentNullException($"Not found note: {id}"));
        }

        public async Task<IResponseTo> UpdateEntity(IRequestTo RequestDTO)
        {
            var NoteDTO = (NoteRequestTo)RequestDTO;

            if (!Validate(NoteDTO))
            {
                throw new InvalidDataException("Incorrect data for UPDATE");

            }
            var newNote = _mapper.Map<Note>(NoteDTO);
            dbContext.Notes.Update(newNote);
            await dbContext.SaveChangesAsync();
            var Note = _mapper.Map<NoteResponseTo>(await dbContext.Notes.FindAsync(newNote.Id));
            return Note;
        }

        private bool Validate(NoteRequestTo NoteDTO)
        {
            if (NoteDTO?.Content?.Length < 2 || NoteDTO?.Content?.Length > 2048)
                return false;

            return true;
        }
    }
}
