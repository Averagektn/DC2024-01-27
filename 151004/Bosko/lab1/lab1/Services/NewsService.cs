using AutoMapper;
using lab1.Models;
using lab1.DTO.Interface;
using lab1.Services.Interface;
using lab1.DTO;

namespace lab1.Services
{
    public class NewsService(IMapper _mapper, LabDbContext dbContext) : INewsService
    {
        public async Task<IResponseTo> CreateEntity(IRequestTo RequestDTO)
        {
            var NewsDTO = (NewsRequestTo)RequestDTO;

            if (!Validate(NewsDTO))
            {
                throw new InvalidDataException("Incorrect data for CREATE news");
            }

            var News = _mapper.Map<News>(NewsDTO);
            dbContext.Add(News);
            await dbContext.SaveChangesAsync();
            var response = _mapper.Map<NewsResponseTo>(News);
            return response;
        }

        public async Task DeleteEntity(int id)
        {
            try
            {
                var News = await dbContext.News.FindAsync(id);
                dbContext.News.Remove(News!);
                await dbContext.SaveChangesAsync();
                return;
            }
            catch
            {
                throw new Exception("Deleting news exception");
            }
        }

        public IEnumerable<IResponseTo> GetAllEntity()
        {
            try
            {
                return dbContext.News.Select(_mapper.Map<NewsResponseTo>);
            }
            catch
            {
                throw new Exception("Getting all news exception");
            }
        }

        public async Task<IResponseTo> GetEntityById(int id)
        {
            var News = await dbContext.News.FindAsync(id);
            return (News is not null ? _mapper.Map<NewsResponseTo>(News) : throw new ArgumentNullException($"Not found news: {id}"));
        }

        public async Task<IResponseTo> UpdateEntity(IRequestTo RequestDTO)
        {
            var NewsDTO = (NewsRequestTo)RequestDTO;

            if (!Validate(NewsDTO))
            {
                throw new InvalidDataException("Incorrect data for UPDATE");

            }
            var newNews = _mapper.Map<News>(NewsDTO);
            dbContext.News.Update(newNews);
            await dbContext.SaveChangesAsync();
            var News = _mapper.Map<NewsResponseTo>(await dbContext.News.FindAsync(newNews.Id));
            return News;
        }

        private bool Validate(NewsRequestTo NewsDTO)
        {
            if (NewsDTO?.Title?.Length < 2 || NewsDTO?.Title?.Length > 64)
                return false;
            if (NewsDTO?.Content?.Length < 8 && NewsDTO?.Content?.Length > 2048)
                return false;

            return true;
        }
    }
}
