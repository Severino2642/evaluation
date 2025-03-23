package site.easy.to.build.crm.repository.depense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.budget.BudgetCustomer;
import site.easy.to.build.crm.entity.depense.LeadDepense;

import java.util.List;

@Repository
public interface LeadDepenseRepository extends JpaRepository<LeadDepense, Integer> {
    public LeadDepense findById(int id);

    public LeadDepense findByLeadLeadId(int leadId);
    public List<LeadDepense> findAll();

    @Query(value = "SELECT SUM(montant) FROM depense_lead",nativeQuery = true)
    public Double getDepenseTotal();
}
