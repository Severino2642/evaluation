namespace crm_dotnet.Models.entity
{
    public class Customer
    {
        int id;
        String name;
        String email;
        DateTime createdAt;

        public Customer()
        {
        }

        public Customer(int id, string name, string email, DateTime createdAt)
        {
            this.id = id;
            this.name = name;
            this.email = email;
            this.createdAt = createdAt;
        }

        public int Id { get => id; set => id = value; }
        public string Name { get => name; set => name = value; }
        public string Email { get => email; set => email = value; }
        public DateTime CreatedAt { get => createdAt; set => createdAt = value; }
    }
}
