using crm_dotnet.Models.entity;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;

namespace crm_dotnet.Controllers
{
    public class LoginController : Controller
    {
        private static readonly HttpClient client = new HttpClient();
        string project_url = "http://localhost:8084/";
        public async Task<IActionResult> Index()
        {
            return View("Login");
        }

        [HttpPost]
        public async Task<IActionResult> Auth(string email)
        {
            string url = project_url + "api/auth-user";

            var values = new Dictionary<string, string>
            {
                {"email", email }
            };

            var content = new FormUrlEncodedContent(values);
            var response = await client.PostAsync(url, content);
            string jsonUser = await response.Content.ReadAsStringAsync();

            if (!jsonUser.Equals("null"))
            {
                User user = (User)JsonConvert.DeserializeObject<User>(jsonUser);
                HttpContext.Session.SetString("user", jsonUser);
                return RedirectToAction("Index", "Dashboard");
            }

            ViewData["alerte"] = "Compte inexistant";
            return View("Login");
        }

        public async Task<IActionResult> LogOut()
        {
            HttpContext.Session.Clear();
            return View("Login");
        }
    }
}
