namespace crm_dotnet.Models.entity
{
    public class User
    {
        int id;
        String username;
        String email;
        String password;

        public User() { }
        public User(int id, string username, string email, string password)
        {
            this.Id = id;
            this.Username = username;
            this.Email = email;
            this.Password = password;
        }

        public int Id { get => id; set => id = value; }
        public string Username { get => username; set => username = value; }
        public string Email { get => email; set => email = value; }
        public string Password { get => password; set => password = value; }
    }
}
