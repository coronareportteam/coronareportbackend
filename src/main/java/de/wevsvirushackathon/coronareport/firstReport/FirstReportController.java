package de.wevsvirushackathon.coronareport.firstReport;

import de.wevsvirushackathon.coronareport.user.Client;
import de.wevsvirushackathon.coronareport.user.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firstReport")
public class FirstReportController {

    private ClientService clientService;
    private FirstReportService firstReportService;

    @Autowired
    public FirstReportController(ClientService clientService, FirstReportService firstReportService) {
        this.clientService = clientService;
        this.firstReportService = firstReportService;
    }

    @PostMapping("/{clientCode}")
    public ResponseEntity<Void> addFirstReport(@PathVariable String clientCode, @RequestBody FirstReport firstReportDto) {
        Client client = this.clientService.getClient(clientCode);
        if (client == null) {
            return ResponseEntity.badRequest().build();
        }
        firstReportDto.setClient(client);
        this.firstReportService.addFirstReport(firstReportDto);
        return ResponseEntity.ok().build();
    }


}
