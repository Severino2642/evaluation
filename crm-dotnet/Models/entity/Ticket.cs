namespace crm_dotnet.Models.entity
{
    public class Ticket
    {
        int id;
        String subject;
        DateTime createdAt;
        User employee;
        Customer customer;

        public Ticket()
        {
        }

        public Ticket(int id, string subject, DateTime createdAt, User employee, Customer customer)
        {
            this.Id = id;
            this.Subject = subject;
            this.CreatedAt = createdAt;
            this.Employee = employee;
            this.Customer = customer;
        }

        public int Id { get => id; set => id = value; }
        public string Subject { get => subject; set => subject = value; }
        public DateTime CreatedAt { get => createdAt; set => createdAt = value; }
        public User Employee { get => employee; set => employee = value; }
        public Customer Customer { get => customer; set => customer = value; }
    }
}
