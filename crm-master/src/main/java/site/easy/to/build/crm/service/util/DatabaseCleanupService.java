package site.easy.to.build.crm.service.util;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.repository.util.DatabaseCleanupRepository;

@Service
public class DatabaseCleanupService {
    @Autowired
    private DatabaseCleanupRepository tableCleanupRepository;

    @Transactional
    public void clearTable(String tableName) {
        System.out.println("Cleaning up table " + tableName);
        tableCleanupRepository.clearTable(tableName);
    }

    @Transactional
    public void disableContrainte(int step){
        tableCleanupRepository.disableContrainte(step);
    }
}
