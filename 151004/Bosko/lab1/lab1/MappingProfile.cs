using AutoMapper;
using lab1.DTO;
using lab1.Models;

namespace lab1
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            CreateMap<UserRequestTo, User>();
            CreateMap<User, UserResponseTo>();
            CreateMap<NewsRequestTo, News>();
            CreateMap<News, NewsResponseTo>();
            CreateMap<NoteRequestTo, Note>();
            CreateMap<Note, NoteResponseTo>();
            CreateMap<Label, LabelResponseTo>();
            CreateMap<LabelRequestTo, Label>();
        }
    }
}
