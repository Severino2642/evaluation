package site.easy.to.build.crm.service.depense;

import org.springframework.data.jpa.repository.Query;
import site.easy.to.build.crm.entity.budget.BudgetCustomer;
import site.easy.to.build.crm.entity.depense.LeadDepense;
import site.easy.to.build.crm.entity.depense.TicketDepense;

import java.util.List;

public interface TicketDepenseService {
    public TicketDepense findById(int id);

    public TicketDepense findByTicketTicketId(int leadId);
    public List<TicketDepense> findAll();

    public double getDepenseTotal();

    public TicketDepense save(TicketDepense ticketDepense);
    public List<TicketDepense> findByCustomerId(int customerId);

    public double getDepenseTotalByCustomerId(int customerId);
    public void delete(TicketDepense ticketDepense);
}
