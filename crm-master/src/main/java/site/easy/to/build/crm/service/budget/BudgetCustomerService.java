package site.easy.to.build.crm.service.budget;

import org.springframework.data.jpa.repository.Query;
import site.easy.to.build.crm.entity.budget.BudgetCustomer;
import site.easy.to.build.crm.repository.budget.BudgetCustomerRepository;

import java.util.List;

public interface BudgetCustomerService {
    public BudgetCustomer findById(int id);

    public List<BudgetCustomer> findByCustomerId(int customerId);

    public List<BudgetCustomer> findAll();

    public double getBudgetTotalByCustomerId(int customerId);

    public BudgetCustomer save(BudgetCustomer budgetCustomer);

}
