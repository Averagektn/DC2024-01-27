using lab1.DTO;
using lab1.DTO.Interface;
using lab1.Services.Interface;
using Microsoft.AspNetCore.Mvc;

namespace lab1.Controllers
{
    [ApiController]
    [Route("/api/v1.0/users")]
    public class UserController(IUserService UserService) : Controller
    {
        [HttpGet]
        public JsonResult GetUsers()
        {
            try
            {
                var Users = UserService.GetAllEntity();
                return Json(Users);
            }
            catch
            {
                return Json(BadRequest());

            }
        }

        [HttpGet]
        [Route("{UserId:int}")]
        public async Task<JsonResult> GetUserById(int UserId)
        {
            try
            {
                var User = await UserService.GetEntityById(UserId);
                return Json(User);
            }
            catch
            {
                return Json(BadRequest());
            }
        }

        [HttpPost]
        public async Task<JsonResult> CreateUser(UserRequestTo UserTo)
        {
            try
            {
                Response.StatusCode = 201;
                var User = await UserService.CreateEntity(UserTo);
                return Json(User);
            }
            catch
            {
                return Json(BadRequest());
            }

        }

        [HttpPut]
        public async Task<JsonResult> UpdateUser(UserRequestTo UserTo)
        {
            IResponseTo newUser;
            try
            {
                newUser = await UserService.UpdateEntity(UserTo);
                Response.StatusCode = 200;
                return Json(newUser);
            }
            catch
            {
                Response.StatusCode = 400;
                return Json(BadRequest());
            }
        }

        [HttpDelete("{UserId}")]
        public async Task<IActionResult> DeleteUser(int UserId)
        {
            try
            {
                Response.StatusCode = 204;
                await UserService.DeleteEntity(UserId);
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
