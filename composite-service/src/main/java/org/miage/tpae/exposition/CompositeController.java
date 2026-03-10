package org.miage.tpae.exposition;

import org.miage.tpae.client.AccountClient;
import org.miage.tpae.client.CustomerClient;
import org.miage.tpae.dto.ClientDTO;
import org.miage.tpae.dto.CompteDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/composite")
public class CompositeController {
    private final CustomerClient customerClient;
    private final AccountClient accountClient;

    public CompositeController(CustomerClient customerClient, AccountClient accountClient) {
        this.customerClient = customerClient;
        this.accountClient = accountClient;
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClientWithComptes(@PathVariable("id") long id) {
        ClientDTO client = customerClient.getClient(id);
        List<CompteDTO> comptes = accountClient.getComptesByClientId(id);
        client.setComptes(comptes);
        return client;
    }

    @PostMapping("/clients/{id}/comptes")
    public CompteDTO ouvrirCompte(@PathVariable("id") long id, @RequestBody CompteDTO compte) {
        return accountClient.ouvrirCompte(id, compte);
    }
}
