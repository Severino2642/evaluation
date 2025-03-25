package site.easy.to.build.crm.csv;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.entity.budget.BudgetCustomer;
import site.easy.to.build.crm.entity.depense.LeadDepense;
import site.easy.to.build.crm.service.budget.BudgetCustomerService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImportBudget {
    List<BudgetCustomer> budgets = new ArrayList<>();

    public ImportBudget() {
    }

    public ImportBudget(List<BudgetCustomer> budgets) {
        this.budgets = budgets;
    }

    public List<BudgetCustomer> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<BudgetCustomer> budgets) {
        this.budgets = budgets;
    }

    public List<String> readBudget(String filename,List<HashMap<String, Object>> data, ImportCustomer importCustomer) throws Exception {
        List<String> result = new ArrayList<>();
        int count = 1;
        for (HashMap<String, Object> map : data) {
            BudgetCustomer budgetCustomer = new BudgetCustomer();
            budgetCustomer.setDate_ajout(LocalDateTime.now());

            Customer customer = importCustomer.isExistCustomer(map.get("customer_email").toString());
            if (customer != null) {
                budgetCustomer.setCustomer(customer);
            }
            else {
                result.add(filename+" lignes "+count+" : Le customer "+map.get("customer_email").toString()+" lier au lead n'existe pas");
            }

            double montant = Double.parseDouble(map.get("Budget").toString());
            if (montant >= 0) {
                budgetCustomer.setMontant(BigDecimal.valueOf(montant));
            }
            else {
                result.add(filename+" lignes "+count+" : le montant "+montant+" est invalide");
            }

            budgets.add(budgetCustomer);
            count++;
        }
        return result;

    }

    public void save(BudgetCustomerService budgetCustomerService) throws Exception {
        for (BudgetCustomer budgetCustomer : budgets) {
            budgetCustomerService.save(budgetCustomer);
        }
    }

    public void updateCustomerForBudget(Customer customer){
        for (BudgetCustomer budget : budgets) {
            if (budget.getCustomer().getEmail().equals(customer.getEmail())) {
                budget.setCustomer(customer);
            }
        }
    }
}
