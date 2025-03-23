package site.easy.to.build.crm.service.depense;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.depense.LeadDepense;
import site.easy.to.build.crm.entity.depense.TicketDepense;
import site.easy.to.build.crm.repository.depense.TicketDepenseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketDepenseServiceImpl implements TicketDepenseService {
    private final TicketDepenseRepository ticketDepenseRepository;

    public TicketDepenseServiceImpl(TicketDepenseRepository ticketDepenseRepository) {
        this.ticketDepenseRepository = ticketDepenseRepository;
    }

    @Override
    public TicketDepense findById(int id) {
        return ticketDepenseRepository.findById(id);
    }

    @Override
    public TicketDepense findByTicketTicketId(int leadId) {
        return ticketDepenseRepository.findByTicketTicketId(leadId);
    }

    @Override
    public List<TicketDepense> findAll() {
        return ticketDepenseRepository.findAll();
    }

    @Override
    public double getDepenseTotal() {
        Double total = ticketDepenseRepository.getDepenseTotal();
        if (total == null) {
            return 0;
        }
        return total;
    }

    @Override
    public TicketDepense save(TicketDepense ticketDepense) {
        return ticketDepenseRepository.save(ticketDepense);
    }

    @Override
    public List<TicketDepense> findByCustomerId(int customerId) {
        List<TicketDepense> ticketDepenses = ticketDepenseRepository.findAll();
        List<TicketDepense> filteredTicketDepenses = new ArrayList<>();
        for (TicketDepense ticketDepense : ticketDepenses) {
            if (ticketDepense.getTicket().getCustomer().getCustomerId() == customerId) {
                filteredTicketDepenses.add(ticketDepense);
            }
        }
        return filteredTicketDepenses;
    }

    @Override
    public double getDepenseTotalByCustomerId(int customerId) {
        double total = 0;
        List<TicketDepense> ticketDepenses = findByCustomerId(customerId);
        for (TicketDepense ticketDepense : ticketDepenses) {
            total += ticketDepense.getMontant().doubleValue();
        }
        return total;
    }

    @Override
    public void delete(TicketDepense ticketDepense) {
        ticketDepenseRepository.delete(ticketDepense);
    }
}
