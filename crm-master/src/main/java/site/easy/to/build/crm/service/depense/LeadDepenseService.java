package site.easy.to.build.crm.service.depense;

import org.springframework.data.jpa.repository.Query;
import site.easy.to.build.crm.entity.budget.BudgetCustomer;
import site.easy.to.build.crm.entity.depense.LeadDepense;

import java.util.List;

public interface LeadDepenseService {
    public LeadDepense findById(int id);

    public LeadDepense findByLeadLeadId(int leadId);
    public List<LeadDepense> findAll();

    public double getDepenseTotal();

    public LeadDepense save(LeadDepense leadDepense);

    public List<LeadDepense> findByCustomerId(int customerId);

    public double getDepenseTotalByCustomerId(int customerId);
    public void delete(LeadDepense leadDepense);
}
