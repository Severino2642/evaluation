package site.easy.to.build.crm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.entity.*;
import site.easy.to.build.crm.entity.budget.BudgetCustomer;
import site.easy.to.build.crm.google.model.gmail.Attachment;
import site.easy.to.build.crm.service.budget.BudgetCustomerService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.util.AuthenticationUtils;
import site.easy.to.build.crm.util.AuthorizationUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/budget")
public class BudgetCustomerController{
    private final AuthenticationUtils authenticationUtils;
    private final CustomerService customerService;
    private final BudgetCustomerService budgetCustomerService;

    public BudgetCustomerController(AuthenticationUtils authenticationUtils, CustomerService customerService, BudgetCustomerService budgetCustomerService) {
        this.authenticationUtils = authenticationUtils;
        this.customerService = customerService;
        this.budgetCustomerService = budgetCustomerService;
    }

    @GetMapping("/insert")
    public String insert(Model model) throws JsonProcessingException {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "budget/add-budget";
    }

    @PostMapping("/insert")
    public String insert(Model model, @RequestParam("montant") double montant,@RequestParam("customerId") int customerId) throws JsonProcessingException {
        if (montant > 0) {
            Customer customer = customerService.findByCustomerId(customerId);
            if (customer != null) {
                BudgetCustomer budgetCustomer = new BudgetCustomer();
                budgetCustomer.setCustomer(customer);
                budgetCustomer.setMontant(BigDecimal.valueOf(montant));
                budgetCustomer.setDate_ajout(LocalDateTime.now());
                budgetCustomerService.save(budgetCustomer);
            }
            else {
                model.addAttribute("message","customer not found "+customerId);
            }
        }
        else {
            model.addAttribute("message","le montant invalide");
        }
        return "budget/add-budget";
    }

    @GetMapping("/")
    public String afflist(Model model,Authentication authentication) throws JsonProcessingException {
        int profileId = authenticationUtils.getLoggedInUserId(authentication);
        Customer customer = customerService.findByProfileId(profileId);
        List<BudgetCustomer> budgetCustomers = budgetCustomerService.findByCustomerId(customer.getCustomerId());
        double totalBudget = budgetCustomerService.getBudgetTotalByCustomerId(customer.getCustomerId());
        model.addAttribute("budgets", budgetCustomers);
        model.addAttribute("totalBudget", totalBudget);
        return "budget/list-budget";
    }


}
