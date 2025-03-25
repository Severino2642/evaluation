package site.easy.to.build.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.easy.to.build.crm.service.util.DatabaseCleanupService;

import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_MANAGER')")
@RequestMapping("/data")
public class DatabaseCleanupController {
    @Autowired
    private DatabaseCleanupService databaseCleanupService;

    public List<String> getListTables() {
        List<String> listTables = new ArrayList<String>();
        listTables.add("trigger_contract");
        listTables.add("trigger_ticket");
        listTables.add("contract_settings");
        listTables.add("email_template");
        listTables.add("ticket_settings");
        listTables.add("lead_settings");
        listTables.add("trigger_lead");
        listTables.add("file");
        listTables.add("google_drive_file");
        listTables.add("lead_action");
        listTables.add("depense_ticket");
        listTables.add("depense_lead");
        listTables.add("budget");
        listTables.add("taux_alerte_budget");
        listTables.add("customer");
        listTables.add("customer_login_info");

        return listTables;
    }
    @PostMapping("/delete")
    public String clearTable(Model model) {

        databaseCleanupService.disableContrainte(0);
        System.out.println("Database cleanup ........");
        for (String tableName : this.getListTables()){
            System.out.println(tableName);
            databaseCleanupService.clearTable(tableName);
        }
        databaseCleanupService.disableContrainte(1);
        model.addAttribute("tables", this.getListTables());
        model.addAttribute("message", "Database cleanup complete.");

        return "data/data";
    }

    @GetMapping("/")
    public String aff_page(Model model) {

        model.addAttribute("tables", this.getListTables());
        return "data/data";
    }
}
