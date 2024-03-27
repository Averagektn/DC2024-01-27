using lab1.Models;
using Microsoft.EntityFrameworkCore;

namespace lab1
{
    public class LabDbContext : DbContext
    {
        public LabDbContext()
        {
            Database.EnsureCreated();
        }

        public LabDbContext(DbContextOptions<LabDbContext> options) : base(options) 
        {
            Database.EnsureCreated();
        }

        public virtual DbSet<User> Users { get; set; }

        public virtual DbSet<News> News { get; set; }

        public virtual DbSet<Note> Notes { get; set; }

        public virtual DbSet<Label> Labels { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlite("DataSource=file::memory:?cache=shared");
        }
    }
}
