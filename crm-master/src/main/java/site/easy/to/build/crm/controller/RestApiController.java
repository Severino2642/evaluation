package site.easy.to.build.crm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.entity.Role;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.entity.budget.BudgetCustomer;
import site.easy.to.build.crm.entity.depense.LeadDepense;
import site.easy.to.build.crm.entity.depense.TicketDepense;
import site.easy.to.build.crm.entity.settings.TauxAlerteBudget;
import site.easy.to.build.crm.service.budget.BudgetCustomerService;
import site.easy.to.build.crm.service.depense.LeadDepenseService;
import site.easy.to.build.crm.service.depense.TicketDepenseService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.settings.TauxAlerteBudgetService;
import site.easy.to.build.crm.service.ticket.TicketService;
import site.easy.to.build.crm.service.user.UserService;
import site.easy.to.build.crm.util.ConverterJsonUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class RestApiController {
    private final BudgetCustomerService budgetCustomerService;
    private final LeadDepenseService leadDepenseService;
    private final TicketDepenseService ticketDepenseService;
    private final TicketService ticketService;
    private final LeadService leadService;
    private final TauxAlerteBudgetService tauxAlerteBudgetService;
    private final UserService userService;

    public RestApiController(BudgetCustomerService budgetCustomerService, LeadDepenseService leadDepenseService, TicketDepenseService ticketDepenseService, TicketService ticketService, LeadService leadService, TauxAlerteBudgetService tauxAlerteBudgetService, UserService userService) {
        this.budgetCustomerService = budgetCustomerService;
        this.leadDepenseService = leadDepenseService;
        this.ticketDepenseService = ticketDepenseService;
        this.ticketService = ticketService;
        this.leadService = leadService;
        this.tauxAlerteBudgetService = tauxAlerteBudgetService;
        this.userService = userService;
    }

    @GetMapping("/get-allBudget")
    public ResponseEntity<List<Map<String, Object>>> getAllBudget() {
        List<BudgetCustomer> budgetCustomers = budgetCustomerService.findAll();
        List<Map<String, Object>> response = new ArrayList<>();
        for (BudgetCustomer budgetCustomer : budgetCustomers) {
            Map<String, Object> map1 = new HashMap<>(ConverterJsonUtils.convertToBudgetMap(budgetCustomer));
            map1.put("customer", ConverterJsonUtils.convertToCustomerMap(budgetCustomer.getCustomer()));
            response.add(map1);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-allTicketDepense")
    public ResponseEntity<List<Map<String, Object>>> getAllTicketDepense() {
        List<TicketDepense> ticketDepenses = ticketDepenseService.findAll();
        List<Map<String, Object>> response = new ArrayList<>();
        for (TicketDepense ticketDepense : ticketDepenses) {
            Map<String, Object> map1 = new HashMap<>(ConverterJsonUtils.convertToTicketDepenseMap(ticketDepense));
            map1.put("ticket", ConverterJsonUtils.convertToTicketMap(ticketDepense.getTicket()));
            response.add(map1);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get-allLeadDepense")
    public ResponseEntity<List<Map<String, Object>>> getAllLeadDepense() {
        List<LeadDepense> leadDepenses = leadDepenseService.findAll();
        List<Map<String, Object>> response = new ArrayList<>();
        for (LeadDepense leadDepense : leadDepenses) {
            Map<String, Object> map1 = new HashMap<>(ConverterJsonUtils.convertToLeadDepenseMap(leadDepense));
            map1.put("lead", ConverterJsonUtils.convertToLeadMap(leadDepense.getLead()));
            response.add(map1);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/ticket/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable("id") int id) {
        TicketDepense ticketDepense = ticketDepenseService.findById(id);
        ticketDepenseService.delete(ticketDepense);
        ticketService.delete(ticketDepense.getTicket());

        return ResponseEntity.ok("ticket deleted");
    }

    @GetMapping("/delete/lead/{id}")
    public ResponseEntity<String> deleteLead(@PathVariable("id") int id) {
        LeadDepense leadDepense = leadDepenseService.findById(id);
        leadDepenseService.delete(leadDepense);
        leadService.delete(leadDepense.getLead());

        return ResponseEntity.ok("lead deleted");
    }

    @GetMapping("/ticket-depense/{id}")
    public ResponseEntity<Map<String, Object>> findTicketDepenseById(@PathVariable("id") int id) {
        TicketDepense ticketDepense = ticketDepenseService.findById(id);
        Map<String, Object> map = new HashMap<>(ConverterJsonUtils.convertToTicketDepenseMap(ticketDepense));
        map.put("ticket", ConverterJsonUtils.convertToTicketMap(ticketDepense.getTicket()));
        return ResponseEntity.ok(map);
    }
    @GetMapping("/lead-depense/{id}")
    public ResponseEntity<Map<String, Object>> findLeadDepenseById(@PathVariable("id") int id) {
        LeadDepense leadDepense = leadDepenseService.findById(id);
        Map<String, Object> map = new HashMap<>(ConverterJsonUtils.convertToLeadDepenseMap(leadDepense));
        map.put("lead", ConverterJsonUtils.convertToLeadMap(leadDepense.getLead()));
        return ResponseEntity.ok(map);
    }

    @PostMapping("/update/lead-depense")
    public ResponseEntity<String> updateLeadDepense(@RequestParam("id") int id,@RequestParam("montant") double montant) {
        LeadDepense leadDepense = leadDepenseService.findById(id);
        leadDepense.setMontant(BigDecimal.valueOf(montant));
        leadDepenseService.save(leadDepense);
        System.out.println("ticket depense updated");
        return ResponseEntity.ok("lead depense updated");
    }

    @PostMapping("/update/ticket-depense")
    public ResponseEntity<String> updateTicketDepense(@RequestParam("id") int id,@RequestParam("montant") double montant) {
        TicketDepense ticketDepense = ticketDepenseService.findById(id);
        ticketDepense.setMontant(BigDecimal.valueOf(montant));
        ticketDepenseService.save(ticketDepense);
        System.out.println("ticket depense updated");
        return ResponseEntity.ok("ticket depense updated");
    }

    @PostMapping("/taux-alerte/insert")
    public ResponseEntity<String> updateTicketDepense(@RequestParam("taux") double taux) {
        TauxAlerteBudget tauxAlerteBudget = new TauxAlerteBudget();
        tauxAlerteBudget.setTaux(BigDecimal.valueOf(taux));
        tauxAlerteBudget.setDate_ajout(LocalDateTime.now());
        tauxAlerteBudgetService.save(tauxAlerteBudget);
        System.out.println("taux alerte added");
        return ResponseEntity.ok("taux alerte added");
    }

    @PostMapping("/auth-user")
    public ResponseEntity<Object> updateTicketDepense(@RequestParam("email") String email,@RequestParam("username") String username) {
        User user = userService.findByEmailAndUsername(email,username);
        if (user != null) {
            for (Role role : user.getRoles()) {
                if(role.getName().equalsIgnoreCase("ROLE_MANAGER")){
                    Map<String, Object> map = new HashMap<>(ConverterJsonUtils.convertToUserMap(user));
                    return ResponseEntity.ok(map);
                }
            }
        }
        return ResponseEntity.ok("null");
    }

    @GetMapping("/get-allTaux")
    public ResponseEntity<List<Map<String, Object>>> getAllTaux() {
        List<TauxAlerteBudget> tauxAlerteBudgets = tauxAlerteBudgetService.findAllOrderByDateAjout();
        List<Map<String, Object>> response = new ArrayList<>();
        for (TauxAlerteBudget tauxAlerteBudget : tauxAlerteBudgets) {
            Map<String, Object> map1 = new HashMap<>(ConverterJsonUtils.convertToTauxMap(tauxAlerteBudget));
            response.add(map1);
        }
        return ResponseEntity.ok(response);
    }
}
