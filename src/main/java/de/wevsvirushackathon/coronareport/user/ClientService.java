package de.wevsvirushackathon.coronareport.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client registerClientAndCreateExternalId(String surename, String firstname, String phone, String zipCode, boolean infected, String healthDepartmentId) {
        Client newClient = new Client();
        newClient.setSurename(surename);
        newClient.setFirstname(firstname);
        newClient.setPhone(phone);
        newClient.setZipCode(zipCode);
        newClient.setInfected(infected);
        newClient.setHealthDepartmentId(healthDepartmentId);
        String clientCode = createNewClientId();
        newClient.setClientCode(clientCode);
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

    public Client getClient(String clientCode) {
        return this.clientRepository.findByClientCode(clientCode);
    }

}
