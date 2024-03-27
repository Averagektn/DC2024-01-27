using lab1;
using lab1.Services;
using lab1.Services.Interface;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllers();

builder.Services.AddDbContext<LabDbContext>();
builder.Services
    .AddScoped<IUserService, UserService>()
    .AddScoped<INewsService, NewsService>()
    .AddScoped<INoteService, NoteService>()
    .AddScoped<ILabelService, LabelService>();

builder.Services.AddAutoMapper(typeof(MappingProfile));
var app = builder.Build();

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();