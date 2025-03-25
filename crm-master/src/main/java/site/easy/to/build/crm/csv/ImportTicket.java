package site.easy.to.build.crm.csv;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.entity.depense.LeadDepense;
import site.easy.to.build.crm.entity.depense.TicketDepense;
import site.easy.to.build.crm.service.depense.LeadDepenseService;
import site.easy.to.build.crm.service.depense.TicketDepenseService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ImportTicket {
    List<String> status =  new ArrayList<>();
    List<String> priority = new ArrayList<>();
    List<Object[]> tickets = new ArrayList<>();

    public ImportTicket() {
        status.add("open");
        status.add("assigned");
        status.add("on-hold");
        status.add("in-progress");
        status.add("resolved");
        status.add("closed");
        status.add("reopened");
        status.add("pending-customer-response");
        status.add("escalated");
        status.add("archived");

        priority.add("low");
        priority.add("medium");
        priority.add("high");
        priority.add("closed");
        priority.add("urgent");
        priority.add("critical");
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getPriority() {
        return priority;
    }

    public void setPriority(List<String> priority) {
        this.priority = priority;
    }

    public List<Object[]> getTickets() {
        return tickets;
    }

    public void setTickets(List<Object[]> tickets) {
        this.tickets = tickets;
    }

    public Object [] addTicketInfo(Ticket ticket, TicketDepense ticketDepense) {
        Object [] tab = new Object[2];
        tab[0] = ticket;
        tab[1] = ticketDepense;
        tickets.add(tab);
        return tab;
    }

    public List<String> readTicket(String filename,User manager, User employee, List<HashMap<String, Object>> data, ImportCustomer importCustomer) throws Exception {
        List<String> result = new ArrayList<>();
        int count = 1;
        for (HashMap<String, Object> map : data) {
            if (map.get("type").toString().equals("ticket")) {
                Ticket ticket = new Ticket();
                ticket.setSubject(map.get("subject_or_name").toString());
                ticket.setDescription(Generateur.getRandomDescription(10));
                ticket.setCreatedAt(LocalDateTime.now());
                ticket.setManager(manager);
                ticket.setEmployee(employee);
                ticket.setPriority(this.getPriority().get(Generateur.getRandomIndex(this.getPriority().size())));

                String status = this.checkStatus(map.get("status").toString());
                if (status!=null){
                    ticket.setStatus(status);
                }
                else {
                    result.add(filename+" lignes "+count+" : le status "+map.get("status").toString()+" est invalide");
                }

                Customer customer = importCustomer.isExistCustomer(map.get("customer_email").toString());
                if (customer != null) {
                    ticket.setCustomer(customer);
                }
                else {
                    result.add(filename+" lignes "+count+" : Le customer "+map.get("customer_email").toString()+" lier au lead n'existe pas");
                }

                TicketDepense ticketDepense = new TicketDepense();
                double montant = Double.parseDouble(map.get("expense").toString());
                if (montant >= 0) {
                    ticketDepense.setMontant(BigDecimal.valueOf(montant));
                    ticketDepense.setDate_ajout(ticket.getCreatedAt());
                }
                else {
                    result.add(filename+" lignes "+count+" : le montant "+montant+" est invalide");
                }

                this.addTicketInfo(ticket,ticketDepense);

            }
            count++;
        }
        return result;

    }

    public String checkStatus(String status) {
        for (String st:this.getStatus()){
            if(st.equals(status)){
                return st;
            }
        }
        return null;
    }

    public void save(TicketService ticketService, TicketDepenseService ticketDepenseService){
        for (Object [] ticketInfo : tickets) {
            Ticket ticket = (Ticket) ticketInfo[0];
            TicketDepense ticketDepense = (TicketDepense) ticketInfo[1];
            Ticket createdTicket = ticketService.save(ticket);
            ticketDepense.setTicket(createdTicket);
            ticketDepenseService.save(ticketDepense);
        }
    }

    public void updateCustomerForTicket(Customer customer){
        for (Object [] ticketInfo : tickets) {
            Ticket ticket = (Ticket) ticketInfo[0];
            if (ticket.getCustomer().getEmail().equals(customer.getEmail())) {
                ticket.setCustomer(customer);
            }
        }
    }
}
