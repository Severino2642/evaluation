package site.easy.to.build.crm.service.settings;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.settings.TauxAlerteBudget;
import site.easy.to.build.crm.repository.settings.TauxAlerteBudgetRepository;

import java.util.List;

@Service
public class TauxAlerteBudgetServiceImpl implements TauxAlerteBudgetService {
    private final TauxAlerteBudgetRepository tauxAlerteBudgetRepository;

    public TauxAlerteBudgetServiceImpl(TauxAlerteBudgetRepository tauxAlerteBudgetRepository) {
        this.tauxAlerteBudgetRepository = tauxAlerteBudgetRepository;
    }

    @Override
    public TauxAlerteBudget findCurrentTaux() {
        return tauxAlerteBudgetRepository.findCurrentTaux();
    }

    @Override
    public List<TauxAlerteBudget> findAllOrderByDateAjout() {
        return tauxAlerteBudgetRepository.findAllOrderByDateAjout();
    }

    @Override
    public TauxAlerteBudget save(TauxAlerteBudget tauxAlerteBudget) {
        return tauxAlerteBudgetRepository.save(tauxAlerteBudget);
    }

    @Override
    public boolean isAtteint(double budget_total, double budget_restant) {
        TauxAlerteBudget tauxAlerteBudget = findCurrentTaux();
        if(tauxAlerteBudget != null){
            double pourcentage_restant = budget_total*tauxAlerteBudget.getValeurPourcentage();
            if (pourcentage_restant>=budget_restant){
                return true;
            }
        }
        return false;
    }
}
