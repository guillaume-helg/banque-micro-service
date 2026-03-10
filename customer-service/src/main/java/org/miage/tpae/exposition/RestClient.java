package org.miage.tpae.exposition;

import org.miage.tpae.entities.Client;
import org.miage.tpae.metier.ServiceClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class RestClient {
    private final ServiceClient serviceClient;

    public RestClient(ServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @GetMapping("{id}")
    public Client getClient(@PathVariable("id") long idClient) {
        return serviceClient.recupererClient(idClient);
    }

    @PostMapping
    public Client creerClient(@RequestBody Client client) {
        return serviceClient.creerClient(client.getPrenom(), client.getNom());
    }
}
