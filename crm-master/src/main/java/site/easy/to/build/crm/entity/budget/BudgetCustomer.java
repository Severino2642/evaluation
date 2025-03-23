package site.easy.to.build.crm.entity.budget;

import jakarta.persistence.*;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.repository.depense.LeadDepenseRepository;
import site.easy.to.build.crm.repository.depense.TicketDepenseRepository;
import site.easy.to.build.crm.service.budget.BudgetCustomerService;
import site.easy.to.build.crm.service.depense.LeadDepenseService;
import site.easy.to.build.crm.service.depense.TicketDepenseService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "budget")
public class BudgetCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "montant")
    private BigDecimal montant;

    @Column(name = "date_ajout")
    private LocalDateTime date_ajout;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public BudgetCustomer() {
    }

    public BudgetCustomer(Integer id, BigDecimal montant, LocalDateTime date_ajout, Customer customer) {
        this.id = id;
        this.montant = montant;
        this.date_ajout = date_ajout;
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDateTime getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(LocalDateTime date_ajout) {
        this.date_ajout = date_ajout;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public static double getBudgetRestant(int customerId,BudgetCustomerService budgetCustomerService, LeadDepenseService leadDepenseService, TicketDepenseService ticketDepenseService) {
        double total_depense = leadDepenseService.getDepenseTotalByCustomerId(customerId) + ticketDepenseService.getDepenseTotalByCustomerId(customerId);
        double reste_budget = budgetCustomerService.getBudgetTotalByCustomerId(customerId) - total_depense;
        return reste_budget;
    }
}
