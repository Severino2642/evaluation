using crm_dotnet.Models.entity;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;

namespace crm_dotnet.Controllers
{
    public class TauxAlerteController : Controller
    {
        private static readonly HttpClient client = new HttpClient();
        string project_url = "http://localhost:8084/";
        public async Task<IActionResult> Index()
        {
            HttpResponseMessage response = await client.GetAsync(project_url + "api/get-allTaux");
            response.EnsureSuccessStatusCode();
            string json = await response.Content.ReadAsStringAsync();


            TauxAlerteBudget[] listTaux = (TauxAlerteBudget[])JsonConvert.DeserializeObject<TauxAlerteBudget[]>(json);
            ViewData["listTaux"] = listTaux;
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

            return RedirectToAction("Index");
        }
    }
}
