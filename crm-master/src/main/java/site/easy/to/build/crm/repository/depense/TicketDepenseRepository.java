package site.easy.to.build.crm.repository.depense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.depense.LeadDepense;
import site.easy.to.build.crm.entity.depense.TicketDepense;

import java.util.List;

@Repository
public interface TicketDepenseRepository extends JpaRepository<TicketDepense, Integer> {
    public TicketDepense findById(int id);

    public TicketDepense findByTicketTicketId(int leadId);
    public List<TicketDepense> findAll();

    @Query(value = "SELECT SUM(montant) FROM depense_ticket",nativeQuery = true)
    public Double getDepenseTotal();
}
