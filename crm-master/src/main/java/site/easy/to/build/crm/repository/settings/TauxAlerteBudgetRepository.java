package site.easy.to.build.crm.repository.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.budget.BudgetCustomer;
import site.easy.to.build.crm.entity.settings.TauxAlerteBudget;

import java.util.List;

@Repository
public interface TauxAlerteBudgetRepository extends JpaRepository<TauxAlerteBudget, Integer> {
    @Query(value = "SELECT * FROM taux_alerte_budget ORDER BY date_ajout DESC LIMIT 1",nativeQuery = true)
    public TauxAlerteBudget findCurrentTaux();

    @Query(value = "SELECT * FROM taux_alerte_budget ORDER BY date_ajout DESC",nativeQuery = true)
    public List<TauxAlerteBudget> findAllOrderByDateAjout();

}
