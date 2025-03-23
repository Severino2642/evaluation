using crm_dotnet.Models.entity;

namespace crm_dotnet.Models.outil
{
    public class DashboardManager
    {
        Budget[] budgets;
        TicketDepense[] ticketDepenses;
        LeadDepense[] leadDepenses;

        public DashboardManager()
        {
        }

        public DashboardManager(Budget[] budgets, TicketDepense[] ticketDepenses, LeadDepense[] leadDepenses)
        {
            this.budgets = budgets;
            this.TicketDepenses = ticketDepenses;
            this.leadDepenses = leadDepenses;
        }

        public Budget[] Budgets { get => budgets; set => budgets = value; }
        public LeadDepense[] LeadDepenses { get => leadDepenses; set => leadDepenses = value; }
        public TicketDepense[] TicketDepenses { get => ticketDepenses; set => ticketDepenses = value; }

        public double getSommeBudget()
        {
            double result = 0;
            for(int i=0;i<this.Budgets.Length;i++)
            {
                result += this.Budgets[i].Montant;
            }
            return result;
        }

        public double getSommeLeadDepense()
        {
            double result = 0;
            for (int i = 0; i < this.LeadDepenses.Length; i++)
            {
                result += this.LeadDepenses[i].Montant;
            }
            return result;
        }

        public double getSommeTicketDepense()
        {
            double result = 0;
            for (int i = 0; i < this.TicketDepenses.Length; i++)
            {
                result += this.TicketDepenses[i].Montant;
            }
            return result;
        }

        public String [] getListMonth()
        {
            string[] mois = new string[]
            {
                "janvier", "fevrier", "mars", "avril", "mai", "juin",
                "juillet", "aout", "septembre", "octobre", "novembre", "decembre"
            };
            return mois;
        }

        public List<double> GetSommeBudgetParMois(int annee)
        {
            List<double> result = new List<double>();

            for (int i = 0; i < 12; i++)
            {
                var moisCourant = i + 1; // 1 pour janvier, 2 pour février, etc.

                double sommeBudgets = this.Budgets.Where(b => b.Date_ajout.Year == annee && b.Date_ajout.Month == moisCourant).Sum(b => b.Montant);

                result.Add(sommeBudgets);
            }

            return result;
        }

        public List<double> GetSommeTicketDepenseParMois(int annee)
        {
            List<double> result = new List<double>();

            for (int i = 0; i < 12; i++)
            {
                var moisCourant = i + 1; // 1 pour janvier, 2 pour février, etc.

                double sommeBudgets = this.TicketDepenses.Where(b => b.Date_ajout.Year == annee && b.Date_ajout.Month == moisCourant).Sum(b => b.Montant);

                result.Add(sommeBudgets);
            }

            return result;
        }

        public List<double> GetSommeLeadDepenseParMois(int annee)
        {
            List<double> result = new List<double>();

            for (int i = 0; i < 12; i++)
            {
                var moisCourant = i + 1; // 1 pour janvier, 2 pour février, etc.

                double sommeBudgets = this.LeadDepenses.Where(b => b.Date_ajout.Year == annee && b.Date_ajout.Month == moisCourant).Sum(b => b.Montant);

                result.Add(sommeBudgets);
            }

            return result;
        }


    }
}
