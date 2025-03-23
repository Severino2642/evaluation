namespace crm_dotnet.Models.entity
{
    public class Budget
    {
        int id;
        int customer_id;
        double montant;
        DateTime date_ajout;
        Customer customer;

        public Budget() { }

        public Budget(int id, int customer_id, double montant, DateTime date_ajout, Customer customer)
        {
            this.id = id;
            this.customer_id = customer_id;
            this.montant = montant;
            this.date_ajout = date_ajout;
            this.Customer = customer;
        }

        public int Id { get => id; set => id = value; }
        public int Customer_id { get => customer_id; set => customer_id = value; }
        public double Montant { get => montant; set => montant = value; }
        public DateTime Date_ajout { get => date_ajout; set => date_ajout = value; }
        public Customer Customer { get => customer; set => customer = value; }

    }
}
