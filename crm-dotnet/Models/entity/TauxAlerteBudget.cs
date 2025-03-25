namespace crm_dotnet.Models.entity
{
    public class TauxAlerteBudget
    {
        int id;
        double taux;
        DateTime date_ajout;

        public TauxAlerteBudget(int id, double taux, DateTime date_ajout)
        {
            this.Id = id;
            this.Taux = taux;
            this.Date_ajout = date_ajout;
        }

        public int Id { get => id; set => id = value; }
        public double Taux { get => taux; set => taux = value; }
        public DateTime Date_ajout { get => date_ajout; set => date_ajout = value; }

    }
}
