namespace crm_dotnet.Models.entity
{
    public class Lead
    {
        int id;
        String name;
        DateTime createdAt;
        User employee;
        Customer customer;

        public Lead()
        {
        }

        public Lead(int id, string name, DateTime createdAt, User employee, Customer customer)
        {
            this.Id = id;
            this.Name = name;
            this.CreatedAt = createdAt;
            this.Employee = employee;
            this.Customer = customer;
        }

        public int Id { get => id; set => id = value; }
        public string Name { get => name; set => name = value; }
        public DateTime CreatedAt { get => createdAt; set => createdAt = value; }
        public User Employee { get => employee; set => employee = value; }
        public Customer Customer { get => customer; set => customer = value; }
    }
}
