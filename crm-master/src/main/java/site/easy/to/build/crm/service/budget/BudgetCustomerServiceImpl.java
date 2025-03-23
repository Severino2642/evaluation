package site.easy.to.build.crm.service.budget;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.budget.BudgetCustomer;
import site.easy.to.build.crm.repository.budget.BudgetCustomerRepository;

import java.util.List;

@Service
public class BudgetCustomerServiceImpl implements BudgetCustomerService {

    private final BudgetCustomerRepository budgetCustomerRepository;

    public BudgetCustomerServiceImpl(BudgetCustomerRepository budgetCustomerRepository) {
        this.budgetCustomerRepository = budgetCustomerRepository;
    }

    @Override
    public BudgetCustomer findById(int id) {
        return budgetCustomerRepository.findById(id);
    }

    @Override
    public List<BudgetCustomer> findByCustomerId(int customerId) {
        return budgetCustomerRepository.findByCustomerCustomerId(customerId);
    }

    @Override
    public List<BudgetCustomer> findAll() {
        return budgetCustomerRepository.findAll();
    }

    @Override
    public double getBudgetTotalByCustomerId(int customerId) {
        return budgetCustomerRepository.getBudgetTotalByCustomerId(customerId);
    }

    @Override
    public BudgetCustomer save(BudgetCustomer budgetCustomer) {
        return budgetCustomerRepository.save(budgetCustomer);
    }
}
