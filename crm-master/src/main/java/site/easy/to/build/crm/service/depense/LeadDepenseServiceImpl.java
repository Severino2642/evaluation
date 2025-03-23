package site.easy.to.build.crm.service.depense;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.depense.LeadDepense;
import site.easy.to.build.crm.repository.depense.LeadDepenseRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeadDepenseServiceImpl implements LeadDepenseService {
    private final LeadDepenseRepository leadDepenseRepository;

    public LeadDepenseServiceImpl(LeadDepenseRepository leadDepenseRepository) {
        this.leadDepenseRepository = leadDepenseRepository;
    }

    @Override
    public LeadDepense findById(int id) {
        return leadDepenseRepository.findById(id);
    }

    @Override
    public LeadDepense findByLeadLeadId(int leadId) {
        return leadDepenseRepository.findByLeadLeadId(leadId);
    }

    @Override
    public List<LeadDepense> findAll() {
        return leadDepenseRepository.findAll();
    }

    @Override
    public double getDepenseTotal() {
        Double total = leadDepenseRepository.getDepenseTotal();
        if (total == null) {
            return 0;
        }
        return total;
    }

    @Override
    public LeadDepense save(LeadDepense leadDepense) {
        return leadDepenseRepository.save(leadDepense);
    }

    @Override
    public List<LeadDepense> findByCustomerId(int customerId) {
        List<LeadDepense> leadDepenses = leadDepenseRepository.findAll();
        List<LeadDepense> filteredLeadDepenses = new ArrayList<>();
        for (LeadDepense leadDepense : leadDepenses) {
            if (leadDepense.getLead().getCustomer().getCustomerId() == customerId) {
                filteredLeadDepenses.add(leadDepense);
            }
        }
        return filteredLeadDepenses;
    }

    @Override
    public double getDepenseTotalByCustomerId(int customerId) {
        double total = 0;
        List<LeadDepense> leadDepenses = findByCustomerId(customerId);
        for (LeadDepense leadDepense : leadDepenses) {
            total += leadDepense.getMontant().doubleValue();
        }
        return total;
    }

    @Override
    public void delete(LeadDepense leadDepense) {
        leadDepenseRepository.delete(leadDepense);
    }

}
