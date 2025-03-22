package site.easy.to.build.crm.repository.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseCleanupRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void clearTable(String tableName) {
        String sql = "TRUNCATE TABLE " + tableName;
        System.out.println(sql);
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public void disableContrainte(int step) {
        String disableConstraints = "SET FOREIGN_KEY_CHECKS = "+step;
        entityManager.createNativeQuery(disableConstraints).executeUpdate();
    }
}
