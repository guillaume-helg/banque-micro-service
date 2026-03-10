package org.miage.tpae.metier;

import org.miage.tpae.dao.ClientRepository;
import org.miage.tpae.entities.Client;
import org.miage.tpae.utilities.ClientInexistant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceClient {
    private final ClientRepository clientRepository;

    public ServiceClient(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client creerClient(String prenom, String nom) {
        List<Client> clients = clientRepository.findByPrenomAndNom(prenom, nom);
        Client client;
        if (clients.isEmpty()) {
            client = new Client();
            client.setPrenom(prenom);
            client.setNom(nom);
            client = clientRepository.save(client);
        } else {
            client = clients.get(0);
        }
        return client;
    }

    public Client recupererClient(long idClient) throws ClientInexistant {
        final Optional<Client> optionalClient = clientRepository.findById(idClient);
        if(optionalClient.isEmpty())
            throw new ClientInexistant("Le client d'id "+idClient+" n'existe pas.");
        return optionalClient.get();
    }
}
