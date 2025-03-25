package site.easy.to.build.crm.csv;

import org.springframework.security.crypto.password.PasswordEncoder;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.budget.BudgetCustomerService;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.depense.LeadDepenseService;
import site.easy.to.build.crm.service.depense.TicketDepenseService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImportCSV {
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final CustomerLoginInfoService customerLoginInfoService;
    private final TicketService ticketService;
    private final TicketDepenseService ticketDepenseService;
    private final LeadService leadService;
    private final LeadDepenseService leadDepenseService;
    private final BudgetCustomerService budgetCustomerService;
    List<CsvFile> listFiles = new ArrayList<CsvFile>();

    public ImportCSV(PasswordEncoder passwordEncoder, CustomerService customerService, CustomerLoginInfoService customerLoginInfoService, TicketService ticketService, TicketDepenseService ticketDepenseService, LeadService leadService, LeadDepenseService leadDepenseService, BudgetCustomerService budgetCustomerService) {
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
        this.customerLoginInfoService = customerLoginInfoService;
        this.ticketService = ticketService;
        this.ticketDepenseService = ticketDepenseService;
        this.leadService = leadService;
        this.leadDepenseService = leadDepenseService;
        this.budgetCustomerService = budgetCustomerService;
    }


    public List<CsvFile> getListFiles() {
        return listFiles;
    }

    public void setListFiles(List<CsvFile> listFiles) {
        this.listFiles = listFiles;
    }

    public void addListFile(CsvFile file) {
        this.listFiles.add(file);
    }

    public List<String> importData (User manager, User employee, String separateur) throws Exception {
        CsvUtil csvUtil = new CsvUtil();
        ImportCustomer importCustomer = new ImportCustomer(passwordEncoder);
        importCustomer.setCustomers(customerService.findAll());
        ImportTicket importTicket = new ImportTicket();
        ImportLead importLead = new ImportLead();
        ImportBudget importBudget = new ImportBudget();
        List<String> allError = new ArrayList<>();
        for (int i = 0; i < listFiles.size(); i++) {
            CsvFile file = listFiles.get(i);
            List<HashMap<String,Object>> data = csvUtil.getDataFromCSV(file.getPath(), separateur, file.getTypes());
            if(i==0){
                allError.addAll(importCustomer.readCustomers(file.getName(),manager,data));
            }
            if (i==1){
                allError.addAll(importLead.readLead(file.getName(),manager,employee,data,importCustomer));
                allError.addAll(importTicket.readTicket(file.getName(),manager,employee,data,importCustomer));
            }
            if (i==2){
                allError.addAll(importBudget.readBudget(file.getName(),data,importCustomer));
            }
        }

        if (allError.isEmpty()) {
            importCustomer.save(customerService,customerLoginInfoService,importLead,importTicket,importBudget);
            importLead.save(leadService,leadDepenseService);
            importTicket.save(ticketService,ticketDepenseService);
            importBudget.save(budgetCustomerService);
        }

        return allError;
    }
}
