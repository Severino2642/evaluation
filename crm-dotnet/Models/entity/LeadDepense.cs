namespace crm_dotnet.Models.entity
{
    public class LeadDepense
    {

        int id;
        int lead_id;
        double montant;
        DateTime date_ajout;
        Lead lead;

        public LeadDepense()
        {
        }

        public LeadDepense(int id, int lead_id, double montant, DateTime date_ajout, Lead lead)
        {
            this.id = id;
            this.lead_id = lead_id;
            this.montant = montant;
            this.date_ajout = date_ajout;
            this.Lead = lead;
        }

        public int Id { get => id; set => id = value; }
        public int Lead_id { get => lead_id; set => lead_id = value; }
        public double Montant { get => montant; set => montant = value; }
        public DateTime Date_ajout { get => date_ajout; set => date_ajout = value; }
        public Lead Lead { get => lead; set => lead = value; }
    }
}
