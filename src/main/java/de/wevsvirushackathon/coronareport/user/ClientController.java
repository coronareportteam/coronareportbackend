package de.wevsvirushackathon.coronareport.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody Client clientDto) {
        Client client = this.clientService.registerClientAndCreateExternalId(
                clientDto.getSurename(),
                clientDto.getFirstname(),
                clientDto.getPhone(),
                clientDto.getZipCode(),
                clientDto.isInfected(),
                clientDto.getHealthDepartmentId());
        return ResponseEntity.ok(client.getClientCode());
    }

    @GetMapping("/{clientCode}")
    public ResponseEntity<Client> getClient(@PathVariable String clientCode) {
        Client client = this.clientService.getClient(clientCode);
        if (client == null) {
            return ResponseEntity.badRequest().build();
        }
        client.setClientId(null);
        return ResponseEntity.ok(client);
    }

}
