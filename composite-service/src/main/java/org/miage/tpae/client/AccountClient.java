package org.miage.tpae.client;

import org.miage.tpae.dto.CompteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/api/comptes")
    List<CompteDTO> getComptesByClientId(@RequestParam("clientId") long clientId);

    @PostMapping("/api/comptes/ouvrir/{clientId}")
    CompteDTO ouvrirCompte(@PathVariable("clientId") long clientId, @RequestBody CompteDTO compte);
}
