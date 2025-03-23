package site.easy.to.build.crm.entity.settings;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "taux_alerte_budget")
public class TauxAlerteBudget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "taux")
    private BigDecimal taux;

    @Column(name = "date_ajout")
    private LocalDateTime date_ajout;

    public TauxAlerteBudget() {
    }

    public TauxAlerteBudget(Integer id, BigDecimal taux, LocalDateTime date_ajout) {
        this.id = id;
        this.taux = taux;
        this.date_ajout = date_ajout;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTaux() {
        return taux;
    }

    public void setTaux(BigDecimal taux) {
        this.taux = taux;
    }

    public LocalDateTime getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(LocalDateTime date_ajout) {
        this.date_ajout = date_ajout;
    }

    public double getValeurPourcentage() {
        return taux.doubleValue()/100;
    }
}
