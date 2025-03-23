package site.easy.to.build.crm.entity.depense;

import jakarta.persistence.*;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "depense_lead")
public class LeadDepense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "montant")
    private BigDecimal montant;

    @Column(name = "date_ajout")
    private LocalDateTime date_ajout;

    @OneToOne
    @JoinColumn(name = "lead_id")
    private Lead lead;

    public LeadDepense() {
    }

    public LeadDepense(Integer id, BigDecimal montant, LocalDateTime date_ajout, Lead lead) {
        this.id = id;
        this.montant = montant;
        this.date_ajout = date_ajout;
        this.lead = lead;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDateTime getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(LocalDateTime date_ajout) {
        this.date_ajout = date_ajout;
    }

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }
}
