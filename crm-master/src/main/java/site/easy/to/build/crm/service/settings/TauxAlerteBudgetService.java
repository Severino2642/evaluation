package site.easy.to.build.crm.service.settings;

import org.springframework.data.jpa.repository.Query;
import site.easy.to.build.crm.entity.settings.TauxAlerteBudget;

import java.util.List;

public interface TauxAlerteBudgetService {
    public TauxAlerteBudget findCurrentTaux();

    public List<TauxAlerteBudget> findAllOrderByDateAjout();

    public TauxAlerteBudget save(TauxAlerteBudget tauxAlerteBudget);

    public boolean isAtteint (double budget_total,double budget_restant);
}
