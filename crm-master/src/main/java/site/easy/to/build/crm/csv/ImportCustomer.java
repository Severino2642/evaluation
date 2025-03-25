package site.easy.to.build.crm.csv;

import org.springframework.security.crypto.password.PasswordEncoder;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.util.EmailTokenUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImportCustomer {
    private final PasswordEncoder passwordEncoder;
    List<Customer> customers = new ArrayList<>();

    public ImportCustomer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<String> readCustomers(String filename,User manager, List<HashMap<String,Object>> data) throws Exception {
        List<String> result = new ArrayList<>();
        int count = 1;
        for (HashMap<String,Object> map : data) {
            Customer customer = new Customer();
            customer.setName((String) map.get("customer_name"));
            customer.setEmail((String) map.get("customer_email"));
            customer.setCountry(Generateur.getRandomCountry());
            customer.setState(Generateur.getRandomState(customer.getCountry()));
            customer.setCity(Generateur.getRandomCity(customer.getCountry(), customer.getState()));
            customer.setAddress(Generateur.getRandomAddress(customer.getCountry(), customer.getState(), customer.getCity()));
            customer.setDescription(Generateur.getRandomDescription(8));
            customer.setPhone(Generateur.getRandomPhoneNumber());
            customer.setFacebook("facebook.com");
            customer.setTwitter("twitter.com");
            customer.setYoutube("youtube.com");
            customer.setCreatedAt(LocalDateTime.now());
            customer.setUser(manager);

            // Auth-Customer-info
            CustomerLoginInfo customerLoginInfo = new CustomerLoginInfo();
            String hashPassword = passwordEncoder.encode(customer.getEmail());
            customerLoginInfo.setPassword(hashPassword);
            customerLoginInfo.setPasswordSet(true);
            customerLoginInfo.setEmail(customer.getEmail());
            customerLoginInfo.setToken(EmailTokenUtils.generateToken());
            customer.setCustomerLoginInfo(customerLoginInfo);

            // Verif customer
            Customer existingCustomer = this.isExistCustomer(customer.getEmail());
            if (existingCustomer == null) {
                customers.add(customer);
            }
            else {
                result.add(filename+" lignes "+count+" : Le customer "+customer.getEmail()+" existe deja");
            }
            count++;
        }
        return result;
    }

    public Customer isExistCustomer(String email) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return customer;
            }
        }
        return null;
    }

    public void save(CustomerService customerService, CustomerLoginInfoService customerLoginInfoService,ImportLead importLead,ImportTicket importTicket,ImportBudget importBudget) throws Exception {
        for (Customer customer : customers) {
            if (customer.getCustomerId()==null){
                Customer newCustomer = new Customer();
                newCustomer.setName(customer.getName());
                newCustomer.setEmail(customer.getEmail());
                newCustomer.setAddress(customer.getAddress());
                newCustomer.setCity(customer.getCity());
                newCustomer.setState(customer.getState());
                newCustomer.setCountry(customer.getCountry());
                newCustomer.setDescription(customer.getDescription());
                newCustomer.setPhone(customer.getPhone());
                newCustomer.setFacebook(customer.getFacebook());
                newCustomer.setTwitter(customer.getTwitter());
                newCustomer.setYoutube(customer.getYoutube());
                newCustomer.setCreatedAt(customer.getCreatedAt());
                newCustomer.setUser(customer.getUser());

                Customer createdCustomer = customerService.save(newCustomer);
                customer.getCustomerLoginInfo().setCustomer(createdCustomer);

                CustomerLoginInfo createdCustomerLoginInfo = customerLoginInfoService.save(customer.getCustomerLoginInfo());
                createdCustomer.setCustomerLoginInfo(createdCustomerLoginInfo);
                customerService.save(createdCustomer);

                importLead.updateCustomerForLead(createdCustomer);
                importTicket.updateCustomerForTicket(createdCustomer);
                importBudget.updateCustomerForBudget(createdCustomer);
            }
        }
    }
}
