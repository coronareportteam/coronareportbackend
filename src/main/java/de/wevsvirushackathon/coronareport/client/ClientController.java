package de.wevsvirushackathon.coronareport.client;

import de.wevsvirushackathon.coronareport.diary.DiaryEntryDtoOut;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientRepository clientRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ClientController(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody ClientDto clientDto) {
        Client client = this.registerClientAndCreateExternalId(clientDto);
        		
        return ResponseEntity.ok(client.getClientCode());
    }

    @GetMapping("/{clientCode}")
    public ResponseEntity<ClientDto> getClient(@PathVariable String clientCode) {
        Client client = this.clientRepository.findByClientCode(clientCode);
        if (client == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(modelMapper.map(client, ClientDto.class));
    }

    public Client registerClientAndCreateExternalId(ClientDto clientDto) {
        Client newClient = modelMapper.map(clientDto, Client.class);
        newClient.setClientCode(createNewClientId());
        this.clientRepository.save(newClient);
        return newClient;
    }

    private String createNewClientId() {
        Client possiblyExistingClient;
        String newClientCode;
        do {
            newClientCode = UUID.randomUUID().toString();
            possiblyExistingClient = this.clientRepository.findByClientCode(newClientCode);
        } while (possiblyExistingClient != null);
        return newClientCode;
    }
}
