using Microsoft.AspNetCore.Mvc;

namespace crm_dotnet.Controllers
{
    public class TauxAlerteController : Controller
    {
        private static readonly HttpClient client = new HttpClient();
        string project_url = "http://localhost:8084/";
        public IActionResult Index()
        {
            return View("Insert");
        }

        [HttpPost]
        public async Task<IActionResult> Insert(string taux)
        {
            string url = project_url + "api/taux-alerte/insert";

            var values = new Dictionary<string, string>
            {
                {"taux", taux }
            };

            var content = new FormUrlEncodedContent(values);
            var response = await client.PostAsync(url, content);

            return View("Insert");
        }
    }
}
