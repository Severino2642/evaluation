package site.easy.to.build.crm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.entity.budget.BudgetCustomer;
import site.easy.to.build.crm.entity.depense.LeadDepense;
import site.easy.to.build.crm.entity.depense.TicketDepense;
import site.easy.to.build.crm.service.budget.BudgetCustomerService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.depense.LeadDepenseService;
import site.easy.to.build.crm.service.depense.TicketDepenseService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.settings.TauxAlerteBudgetService;
import site.easy.to.build.crm.service.ticket.TicketService;
import site.easy.to.build.crm.util.AuthenticationUtils;
import site.easy.to.build.crm.util.AuthorizationUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/depense")
public class DepenseController {
    private final LeadDepenseService leadDepenseService;
    private final TicketDepenseService ticketDepenseService;
    private final BudgetCustomerService budgetCustomerService;
    private final CustomerService customerService;
    private final TicketService ticketService;
    private final LeadService leadService;
    private final AuthenticationUtils authenticationUtils;
    private final TauxAlerteBudgetService tauxAlerteBudgetService;

    public DepenseController(LeadDepenseService leadDepenseService, TicketDepenseService ticketDepenseService, BudgetCustomerService budgetCustomerService, CustomerService customerService, TicketService ticketService, LeadService leadService, AuthenticationUtils authenticationUtils, TauxAlerteBudgetService tauxAlerteBudgetService) {
        this.leadDepenseService = leadDepenseService;
        this.ticketDepenseService = ticketDepenseService;
        this.budgetCustomerService = budgetCustomerService;
        this.customerService = customerService;
        this.ticketService = ticketService;
        this.leadService = leadService;
        this.authenticationUtils = authenticationUtils;
        this.tauxAlerteBudgetService = tauxAlerteBudgetService;
    }

    @GetMapping("/customer/ticket")
    public String showAllCustomerTicketDepense(Model model, Authentication authentication) throws JsonProcessingException {
        int profileId = authenticationUtils.getLoggedInUserId(authentication);
        Customer customer = customerService.findByProfileId(profileId);
        model.addAttribute("ticketDepenses", ticketDepenseService.findByCustomerId(customer.getCustomerId()));
        return "customer-info/depense/list-ticket-depense";
    }

    @GetMapping("/customer/lead")
    public String showAllCustomerLeadDepense(Model model, Authentication authentication) throws JsonProcessingException {
        int profileId = authenticationUtils.getLoggedInUserId(authentication);
        Customer customer = customerService.findByProfileId(profileId);
        model.addAttribute("leadDepenses", leadDepenseService.findByCustomerId(customer.getCustomerId()));
        return "customer-info/depense/list-lead-depense";
    }

    @GetMapping("/ticket")
    public String showAllTicketDepense(Model model) throws JsonProcessingException {
        model.addAttribute("ticketDepenses", ticketDepenseService.findAll());
        return "depense/list-ticket-depense";
    }

    @GetMapping("/lead")
    public String showAllLeadDepense(Model model) throws JsonProcessingException {
        model.addAttribute("leadDepenses", leadDepenseService.findAll());
        return "depense/list-lead-depense";
    }

    @GetMapping("/form-ticket")
    public String showTicketDepenseForm(Model model) throws JsonProcessingException {
        model.addAttribute("tickets", Ticket.getTicketSansDepense(ticketService.findAll(),ticketDepenseService.findAll()));
        return "depense/add-ticket-depense";
    }

    @GetMapping("/form-lead")
    public String showLeadDepenseForm(Model model) throws JsonProcessingException {
        model.addAttribute("leads", Lead.getLeadSansDepense(leadService.findAll(),leadDepenseService.findAll()));
        return "depense/add-lead-depense";
    }

    @PostMapping("/check-ticket-depense")
    public String verifTicketDepense(HttpServletRequest request, Model model, @RequestParam("id") int id,@RequestParam("montant") double montant) throws JsonProcessingException {


        if (montant<0){
            model.addAttribute("tickets", ticketService.findAll());
            model.addAttribute("message","montant invalid");
            return "depense/add-ticket-depense";
        }

        Ticket ticket = ticketService.findByTicketId(id);

        double budget = BudgetCustomer.getBudgetRestant(ticket.getCustomer().getCustomerId(),budgetCustomerService,leadDepenseService,ticketDepenseService);
        double reste_budget = budget - montant;

        if (reste_budget<0){
            model.addAttribute("id",id);
            model.addAttribute("confirmation_path","depense/save-ticket-depense");
            model.addAttribute("annulation_path","depense/form-ticket");
            model.addAttribute("budget",budget);
            model.addAttribute("montant",montant);

            return "depense/confirmation-depassement-budget";
        }

        TicketDepense ticketDepense = new TicketDepense();
        ticketDepense.setMontant(BigDecimal.valueOf(montant));
        ticketDepense.setTicket(ticket);
        ticketDepense.setDate_ajout(LocalDateTime.now());
        ticketDepenseService.save(ticketDepense);

        if (tauxAlerteBudgetService.isAtteint(budgetCustomerService.getBudgetTotalByCustomerId(ticket.getCustomer().getCustomerId()),BudgetCustomer.getBudgetRestant(ticket.getCustomer().getCustomerId(),budgetCustomerService,leadDepenseService,ticketDepenseService))){
            model.addAttribute("alerte_taux","Le client a atteint "+tauxAlerteBudgetService.findCurrentTaux().getTaux()+"% de son budget");
        }
        model.addAttribute("tickets", Ticket.getTicketSansDepense(ticketService.findAll(),ticketDepenseService.findAll()));
        model.addAttribute("alerte", "insertion success");
        return "depense/add-ticket-depense";
    }

    @PostMapping("/check-lead-depense")
    public String verifLeadDepense(HttpServletRequest request, Model model, @RequestParam("id") int id,@RequestParam("montant") double montant) throws JsonProcessingException {

        if (montant<0){
            model.addAttribute("leads", leadService.findAll());
            model.addAttribute("message","montant invalid");
            return "depense/add-lead-depense";
        }

        Lead lead = leadService.findByLeadId(id);

        double budget = BudgetCustomer.getBudgetRestant(lead.getCustomer().getCustomerId(),budgetCustomerService,leadDepenseService,ticketDepenseService);
        double reste_budget = budget - montant;

        if (reste_budget<0){
            model.addAttribute("id",id);
            model.addAttribute("confirmation_path","depense/save-lead-depense");
            model.addAttribute("annulation_path","depense/form-lead");
            model.addAttribute("budget",budget);
            model.addAttribute("montant",montant);

            return "depense/confirmation-depassement-budget";
        }

        LeadDepense leadDepense = new LeadDepense();
        leadDepense.setLead(lead);
        leadDepense.setMontant(BigDecimal.valueOf(montant));
        leadDepense.setDate_ajout(LocalDateTime.now());
        leadDepenseService.save(leadDepense);

        if (tauxAlerteBudgetService.isAtteint(budgetCustomerService.getBudgetTotalByCustomerId(lead.getCustomer().getCustomerId()),BudgetCustomer.getBudgetRestant(lead.getCustomer().getCustomerId(),budgetCustomerService,leadDepenseService,ticketDepenseService))){
            model.addAttribute("alerte_taux","Le client a atteint "+tauxAlerteBudgetService.findCurrentTaux().getTaux()+"% de son budget");
        }
        model.addAttribute("leads", Lead.getLeadSansDepense(leadService.findAll(),leadDepenseService.findAll()));
        model.addAttribute("alerte", "insertion success");
        return "depense/add-lead-depense";
    }


    @PostMapping("/save-ticket-depense")
    public String insertTicketDepense(Model model, @RequestParam("id") int id,@RequestParam("montant") double montant) throws JsonProcessingException {

        Ticket ticket = ticketService.findByTicketId(id);
        TicketDepense ticketDepense = new TicketDepense();
        ticketDepense.setMontant(BigDecimal.valueOf(montant));
        ticketDepense.setTicket(ticket);
        ticketDepense.setDate_ajout(LocalDateTime.now());
        ticketDepenseService.save(ticketDepense);

        if (tauxAlerteBudgetService.isAtteint(budgetCustomerService.getBudgetTotalByCustomerId(ticket.getCustomer().getCustomerId()),BudgetCustomer.getBudgetRestant(ticket.getCustomer().getCustomerId(),budgetCustomerService,leadDepenseService,ticketDepenseService))){
            model.addAttribute("alerte_taux","Le client a atteint "+tauxAlerteBudgetService.findCurrentTaux().getTaux()+"% de son budget");
        }
        model.addAttribute("tickets", Ticket.getTicketSansDepense(ticketService.findAll(),ticketDepenseService.findAll()));
        model.addAttribute("alerte", "insertion success");
        return "depense/add-ticket-depense";
    }

    @PostMapping("/save-lead-depense")
    public String insertLeadDepense(Model model, @RequestParam("id") int id,@RequestParam("montant") double montant) throws JsonProcessingException {

        Lead lead = leadService.findByLeadId(id);
        LeadDepense leadDepense = new LeadDepense();
        leadDepense.setLead(lead);
        leadDepense.setMontant(BigDecimal.valueOf(montant));
        leadDepense.setDate_ajout(LocalDateTime.now());
        leadDepenseService.save(leadDepense);

        if (tauxAlerteBudgetService.isAtteint(budgetCustomerService.getBudgetTotalByCustomerId(lead.getCustomer().getCustomerId()),BudgetCustomer.getBudgetRestant(lead.getCustomer().getCustomerId(),budgetCustomerService,leadDepenseService,ticketDepenseService))){
            model.addAttribute("alerte_taux","Le client a atteint "+tauxAlerteBudgetService.findCurrentTaux().getTaux()+"% de son budget");
        }
        model.addAttribute("leads", Lead.getLeadSansDepense(leadService.findAll(),leadDepenseService.findAll()));
        model.addAttribute("alerte", "insertion success");
        return "depense/add-lead-depense";
    }
}
