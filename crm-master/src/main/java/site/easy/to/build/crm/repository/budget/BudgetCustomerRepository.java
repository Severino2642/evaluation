package site.easy.to.build.crm.repository.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.budget.BudgetCustomer;

import java.util.List;

@Repository
public interface BudgetCustomerRepository extends JpaRepository<BudgetCustomer, Integer> {
    public BudgetCustomer findById(int id);

    public List<BudgetCustomer> findByCustomerCustomerId(int customerId);
    public List<BudgetCustomer> findAll();

    @Query(value = "SELECT SUM(montant) FROM budget b WHERE b.customer_id = :customerId",nativeQuery = true)
    public Double getBudgetTotalByCustomerId(@Param("customerId") int customerId);


}
