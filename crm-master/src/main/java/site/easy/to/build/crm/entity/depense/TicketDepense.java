package site.easy.to.build.crm.entity.depense;

import jakarta.persistence.*;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "depense_ticket")
public class TicketDepense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "montant")
    private BigDecimal montant;

    @Column(name = "date_ajout")
    private LocalDateTime date_ajout;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public TicketDepense() {
    }

    public TicketDepense(Integer id, BigDecimal montant, LocalDateTime date_ajout, Ticket ticket) {
        this.id = id;
        this.montant = montant;
        this.date_ajout = date_ajout;
        this.ticket = ticket;
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

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
