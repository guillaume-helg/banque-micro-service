package org.miage.tpae.exposition;

import org.miage.tpae.entities.Compte;
import org.miage.tpae.entities.OperationCompte;
import org.miage.tpae.export.OperationImport;
import org.miage.tpae.export.Position;
import org.miage.tpae.export.VirementImport;
import org.miage.tpae.metier.ServiceCompte;
import org.miage.tpae.utilities.OperationNonConforme;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/comptes")
public class RestCompte {
    private final ServiceCompte serviceCompte;

    public RestCompte(ServiceCompte serviceCompte) {
        this.serviceCompte = serviceCompte;
    }

    @GetMapping("/{id}")
    public Position getCompte(@PathVariable("id") String idCompte) {
        return this.serviceCompte.consulter(idCompte);
    }

    @GetMapping("/{id}/operations")
    public Collection<OperationCompte> recupererOperations(@PathVariable("id") String idCompte) {
        return this.serviceCompte.recupererOperations(idCompte);
    }

    @PostMapping("/{id}/operations")
    public Position operationCompte(@PathVariable("id") String idCompte, @RequestBody OperationImport operationImport) throws OperationNonConforme {
        if (operationImport.getOperationType() == OperationCompte.OperationType.CREDIT)
            this.serviceCompte.crediter(idCompte, operationImport.getValeur());
        else if (operationImport.getOperationType() == OperationCompte.OperationType.DEBIT)
            this.serviceCompte.debiter(idCompte, operationImport.getValeur());
        else
            throw new OperationNonConforme("L'operation de type "+operationImport.getOperationType()+" n'est pas conforme");
        return this.serviceCompte.consulter(idCompte);
    }

    @PostMapping("/{id}/operations/virements")
    public Position virementCompte(@PathVariable("id") String idCompte, @RequestBody VirementImport virementImport) {
        this.serviceCompte.virer(idCompte, virementImport.getIdCompteDestinataire(), virementImport.getValeur());
        return this.serviceCompte.consulter(idCompte);
    }

    @DeleteMapping("/{id}")
    public void fermerCompte(@PathVariable("id") String idCompte) {
        this.serviceCompte.fermer(idCompte);
    }

    @PostMapping("/ouvrir/{clientId}")
    public Compte ouvrirCompte(@PathVariable("clientId") long clientId, @RequestBody Compte compte) {
        return serviceCompte.ouvrir(clientId, compte.getSolde());
    }

    @GetMapping
    public Collection<Compte> getComptesByClientId(@RequestParam("clientId") long clientId) {
        return serviceCompte.getComptesByClientId(clientId);
    }
}
