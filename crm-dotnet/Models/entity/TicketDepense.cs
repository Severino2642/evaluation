namespace crm_dotnet.Models.entity
{
    public class TicketDepense
    {
        int id;
        int ticket_id;
        double montant;
        DateTime date_ajout;
        Ticket ticket;

        public TicketDepense()
        {
        }

        public TicketDepense(int id, int ticket_id, double montant, DateTime date_ajout, Ticket ticket)
        {
            this.id = id;
            this.ticket_id = ticket_id;
            this.montant = montant;
            this.date_ajout = date_ajout;
            this.Ticket = ticket;
        }

        public int Id { get => id; set => id = value; }
        public int Ticket_id { get => ticket_id; set => ticket_id = value; }
        public double Montant { get => montant; set => montant = value; }
        public DateTime Date_ajout { get => date_ajout; set => date_ajout = value; }
        public Ticket Ticket { get => ticket; set => ticket = value; }
    }
}
