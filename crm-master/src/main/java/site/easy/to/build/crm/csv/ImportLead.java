package site.easy.to.build.crm.csv;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.entity.depense.LeadDepense;
import site.easy.to.build.crm.service.depense.LeadDepenseService;
import site.easy.to.build.crm.service.lead.LeadService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ImportLead {
    List<String> status =  new ArrayList<>();
    List<Object[]> leads = new ArrayList<>();
    public ImportLead() {
        status.add("meeting-to-schedule");
        status.add("scheduled");
        status.add("archived");
        status.add("success");
        status.add("assign-to-sales");
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<Object[]> getLeads() {
        return leads;
    }

    public void setLeads(List<Object[]> leads) {
        this.leads = leads;
    }

    public Object [] addLeadInfo(Lead lead, LeadDepense leadDepense) {
        Object [] tab = new Object[2];
        tab[0] = lead;
        tab[1] = leadDepense;
        leads.add(tab);
        return tab;
    }

    public List<String> readLead(String filename,User manager, User employee, List<HashMap<String, Object>> data, ImportCustomer importCustomer) throws Exception {
        List<String> result = new ArrayList<>();
        int count = 1;
        for (HashMap<String, Object> map : data) {
            if (map.get("type").toString().equals("lead")) {
                Lead lead = new Lead();
                lead.setName(map.get("subject_or_name").toString());
                lead.setGoogleDrive(false);
                lead.setGoogleDriveFolderId("");
                lead.setCreatedAt(LocalDateTime.now());
                lead.setManager(manager);
                lead.setEmployee(employee);

                String status = this.checkStatus(map.get("status").toString());
                if (status!=null){
                    lead.setStatus(status);
                }
                else {
                    result.add(filename+" lignes "+count+" : le status "+map.get("status").toString()+" est invalide");
                }

                Customer customer = importCustomer.isExistCustomer(map.get("customer_email").toString());
                if (customer != null) {
                    lead.setCustomer(customer);
                    lead.setPhone(customer.getPhone());
                }
                else {
                    result.add(filename+" lignes "+count+" : Le customer "+map.get("customer_email").toString()+" lier au lead n'existe pas");
                }

                LeadDepense leadDepense = new LeadDepense();
                double montant = Double.parseDouble(map.get("expense").toString());
                if (montant >= 0) {
                    leadDepense.setMontant(BigDecimal.valueOf(montant));
                    leadDepense.setDate_ajout(lead.getCreatedAt());
                }
                else {
                    result.add(filename+" lignes "+count+" : le montant "+montant+" est invalide");
                }
                this.addLeadInfo(lead, leadDepense);
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

    public void save(LeadService leadService, LeadDepenseService leadDepenseService){
        for (Object [] leadInfo : leads) {
            Lead lead = (Lead) leadInfo[0];
            LeadDepense leadDepense = (LeadDepense) leadInfo[1];
            Lead createdLead = leadService.save(lead);
            leadDepense.setLead(createdLead);
            leadDepenseService.save(leadDepense);
        }
    }

    public void updateCustomerForLead(Customer customer){
        for (Object [] leadInfo : leads) {
            Lead lead = (Lead) leadInfo[0];
            if (lead.getCustomer().getEmail().equals(customer.getEmail())) {
                lead.setCustomer(customer);
            }
        }
    }
}
