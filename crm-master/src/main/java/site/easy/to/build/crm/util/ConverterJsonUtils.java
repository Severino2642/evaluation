package site.easy.to.build.crm.util;

import site.easy.to.build.crm.entity.*;
import site.easy.to.build.crm.entity.budget.BudgetCustomer;
import site.easy.to.build.crm.entity.depense.LeadDepense;
import site.easy.to.build.crm.entity.depense.TicketDepense;
import site.easy.to.build.crm.entity.settings.TauxAlerteBudget;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConverterJsonUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Map<String, Object> convertToBudgetMap(BudgetCustomer budgetCustomer) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", budgetCustomer.getId());
        map.put("customerId", budgetCustomer.getCustomer().getCustomerId());
        map.put("montant", budgetCustomer.getMontant().doubleValue());
        map.put("date_ajout", formatDate(budgetCustomer.getDate_ajout()));
        return map;
    }

    public static Map<String, Object> convertToCustomerMap(Customer customer) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", customer.getCustomerId());
        map.put("name", customer.getName());
        map.put("email", customer.getEmail());
        map.put("createdAt", formatDate(customer.getCreatedAt()));
        return map;
    }

    public static Map<String, Object> convertToUserMap(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        return map; // Ne pas inclure le mot de passe
    }

    public static Map<String, Object> convertToLeadMap(Lead lead) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", lead.getLeadId());
        map.put("name", lead.getName());
        map.put("createdAt", formatDate(lead.getCreatedAt()));
        map.put("customer", convertToCustomerMap(lead.getCustomer()));
        map.put("employee", convertToUserMap(lead.getEmployee()));
        return map;
    }

    public static Map<String, Object> convertToTicketMap(Ticket ticket) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", ticket.getTicketId());
        map.put("subject", ticket.getSubject());
        map.put("createdAt", formatDate(ticket.getCreatedAt()));
        map.put("customer", convertToCustomerMap(ticket.getCustomer()));
        map.put("employee", convertToUserMap(ticket.getEmployee()));
        return map;
    }

    public static Map<String, Object> convertToTicketDepenseMap(TicketDepense ticketDepense) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", ticketDepense.getId());
        map.put("ticket_id", ticketDepense.getTicket().getTicketId());
        map.put("montant", ticketDepense.getMontant().doubleValue());
        map.put("date_ajout", formatDate(ticketDepense.getDate_ajout()));
        return map;
    }

    public static Map<String, Object> convertToLeadDepenseMap(LeadDepense leadDepense) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", leadDepense.getId());
        map.put("lead_id", leadDepense.getLead().getLeadId());
        map.put("montant", leadDepense.getMontant().doubleValue());
        map.put("date_ajout", formatDate(leadDepense.getDate_ajout()));
        return map;
    }

    public static Map<String, Object> convertToTauxMap(TauxAlerteBudget tauxAlerteBudget) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", tauxAlerteBudget.getId());
        map.put("taux", tauxAlerteBudget.getTaux());
        map.put("date_ajout", formatDate(tauxAlerteBudget.getDate_ajout()));
        return map;
    }

    private static String formatDate(Object date) {
        return Optional.ofNullable(date).map(Object::toString).orElse("N/A");
    }
}
