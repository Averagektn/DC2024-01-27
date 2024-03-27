using lab1.DTO;
using lab1.DTO.Interface;
using lab1.Models;
using lab1.Services.Interface;
using Microsoft.AspNetCore.Mvc;

namespace lab1.Controllers
{
    [ApiController]
    [Route("/api/v1.0/notes")]
    public class NoteController(INoteService NoteService) : Controller
    {
        [HttpGet]
        public JsonResult GetNote()
        {
            try
            {
                var Notes = NoteService.GetAllEntity();
                return Json(Notes);
            }
            catch
            {
                return Json(BadRequest());

            }
        }

        [HttpGet]
        [Route("{NoteId:int}")]
        public async Task<JsonResult> GetNoteById(int NoteId)
        {
            try
            {
                var Note = await NoteService.GetEntityById(NoteId);
                return Json(Note);
            }
            catch
            {
                return Json(BadRequest());
            }
        }

        [HttpPost]
        public async Task<JsonResult> CreateNote(NoteRequestTo NoteTo)
        {
            try
            {
                Response.StatusCode = 201;
                var Note = await NoteService.CreateEntity(NoteTo);
                return Json(Note);
            }
            catch
            {
                return Json(BadRequest());
            }

        }

        [HttpPut]
        public async Task<JsonResult> UpdateNote(NoteRequestTo NoteTo)
        {
            IResponseTo newNote;
            try
            {
                newNote = await NoteService.UpdateEntity(NoteTo);
                Response.StatusCode = 200;
                return Json(newNote);
            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpDelete("{NoteId}")]
        public async Task<IActionResult> DeleteNote(int NoteId)
        {
            try
            {
                Response.StatusCode = 204;
                await NoteService.DeleteEntity(NoteId);
            }
            catch
            {
                Response.StatusCode = 401;
                return BadRequest();
            }

            return NoContent();
        }
    }
}
