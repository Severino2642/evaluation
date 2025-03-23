using crm_dotnet.Models.entity;
using crm_dotnet.Models.outil;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;

namespace crm_dotnet.Controllers
{
    public class DashboardController : Controller
    {
        private static readonly HttpClient client = new HttpClient();
        string project_url = "http://localhost:8084/";
        public async Task<IActionResult> Index()
        {

            HttpResponseMessage response = await client.GetAsync(project_url + "api/get-allBudget");
            response.EnsureSuccessStatusCode();
            string allbudgetJson = await response.Content.ReadAsStringAsync();

            response = await client.GetAsync(project_url + "api/get-allTicketDepense");
            response.EnsureSuccessStatusCode();
            string allticketDepenseJson = await response.Content.ReadAsStringAsync();

            response = await client.GetAsync(project_url + "api/get-allLeadDepense");
            response.EnsureSuccessStatusCode();
            string allLeadDepenseJson = await response.Content.ReadAsStringAsync();

            DashboardManager dashboardManager = new DashboardManager();
            dashboardManager.Budgets = (Budget[])JsonConvert.DeserializeObject<Budget[]>(allbudgetJson);
            dashboardManager.TicketDepenses = (TicketDepense[])JsonConvert.DeserializeObject<TicketDepense[]>(allticketDepenseJson);
            dashboardManager.LeadDepenses = (LeadDepense[])JsonConvert.DeserializeObject<LeadDepense[]>(allLeadDepenseJson);
            
            ViewData["dashboardManager"] = dashboardManager;
            return View("Dashboard");
        }

        public async Task<IActionResult> AffDetailsBudget()
        {

            HttpResponseMessage response = await client.GetAsync(project_url + "api/get-allBudget");
            response.EnsureSuccessStatusCode();
            string allbudgetJson = await response.Content.ReadAsStringAsync();

         
            Budget [] budgets = (Budget[])JsonConvert.DeserializeObject<Budget[]>(allbudgetJson);

            ViewData["budgets"] = budgets;
            return View("DetailsBudget");
        }

        public async Task<IActionResult> AffDetailsTicketDepense()
        {


            HttpResponseMessage response = await client.GetAsync(project_url + "api/get-allTicketDepense");
            response.EnsureSuccessStatusCode();
            string allticketDepenseJson = await response.Content.ReadAsStringAsync();

            TicketDepense[] ticketDepenses = (TicketDepense[])JsonConvert.DeserializeObject<TicketDepense[]>(allticketDepenseJson);

            ViewData["ticketDepenses"] = ticketDepenses;
            return View("DetailsTicketDepense");
        }

        public async Task<IActionResult> AffDetailsLeadDepense()
        {

            HttpResponseMessage response = await client.GetAsync(project_url + "api/get-allLeadDepense");
            response.EnsureSuccessStatusCode();
            string allLeadDepenseJson = await response.Content.ReadAsStringAsync();

            LeadDepense[] leadDepenses = (LeadDepense[])JsonConvert.DeserializeObject<LeadDepense[]>(allLeadDepenseJson);

            ViewData["leadDepenses"] = leadDepenses;
            return View("DetailsLeadDepense");
        }

        public async Task<IActionResult> DeleteTicket(int id)
        {

            HttpResponseMessage response = await client.GetAsync(project_url + "api/delete/ticket/"+id);
            response.EnsureSuccessStatusCode();
            string alerte = await response.Content.ReadAsStringAsync();

            ViewData["alerte"] = alerte;
            return RedirectToAction("AffDetailsTicketDepense");
        }

        public async Task<IActionResult> DeleteLead(int id)
        {

            HttpResponseMessage response = await client.GetAsync(project_url + "api/delete/lead/" + id);
            response.EnsureSuccessStatusCode();
            string alerte = await response.Content.ReadAsStringAsync();

            ViewData["alerte"] = alerte;
            return RedirectToAction("AffDetailsLeadDepense");
        }

        public async Task<IActionResult> ShowFormUpdateLead(int id)
        {

            HttpResponseMessage response = await client.GetAsync(project_url + "api/lead-depense/"+id);
            response.EnsureSuccessStatusCode();
            string allLeadDepenseJson = await response.Content.ReadAsStringAsync();

            LeadDepense leadDepense = (LeadDepense)JsonConvert.DeserializeObject<LeadDepense>(allLeadDepenseJson);

            ViewData["leadDepense"] = leadDepense;
            return View("UpdateLead");
        }

        public async Task<IActionResult> ShowFormUpdateTicket(int id)
        {

            HttpResponseMessage response = await client.GetAsync(project_url + "api/ticket-depense/" + id);
            response.EnsureSuccessStatusCode();
            string allTicketDepenseJson = await response.Content.ReadAsStringAsync();

            TicketDepense ticketDepense = (TicketDepense)JsonConvert.DeserializeObject<TicketDepense>(allTicketDepenseJson);

            ViewData["ticketDepense"] = ticketDepense;
            return View("UpdateTicket");
        }

        [HttpPost]
        public async Task<IActionResult> UpdateTicket(string id, string montant)
        {
            string url = project_url + "api/update/ticket-depense";

            var values = new Dictionary<string, string>
            {
                {"id", id },
                {"montant",montant }
            };

            var content = new FormUrlEncodedContent(values);
            var response = await client.PostAsync(url, content);

            return RedirectToAction("AffDetailsTicketDepense");
        }

        [HttpPost]
        public async Task<IActionResult> UpdateLead(string id, string montant)
        {
            string url = project_url + "api/update/lead-depense";

            var values = new Dictionary<string, string>
            {
                {"id", id },
                {"montant",montant }
            };

            var content = new FormUrlEncodedContent(values);
            var response = await client.PostAsync(url, content);

            return RedirectToAction("AffDetailsLeadDepense");
        }

    }
}
