package org.miage.tpae.metier;

import org.miage.tpae.dao.CompteRepository;
import org.miage.tpae.entities.Compte;
import org.miage.tpae.entities.OperationCompte;
import org.miage.tpae.export.Position;
import org.miage.tpae.utilities.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Optional;

@Service
public class ServiceCompte {
    private final CompteRepository compteRepository;

    public ServiceCompte(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    public Compte ouvrir(long idClient, double soldeInitial) throws MontantInvalidException {
        if(soldeInitial < 0.) throw new MontantInvalidException("Le solde initial ne peut pas être négatif.");
        Compte compte = new Compte();
        compte.setClientId(idClient);
        compte.setSolde(soldeInitial);
        compte.setOperations(new ArrayList<>());
        compte.getOperations().add(new OperationCompte(OperationCompte.OperationType.OUVERTURE, soldeInitial));
        return compteRepository.save(compte);
    }

    public void fermer(String idCompte) throws CompteInconnuException {
        Compte compte = findCompte(idCompte);
        compte.setActif(false);
        compte.getOperations().add(new OperationCompte(OperationCompte.OperationType.CLOTURE, 0));
        compteRepository.save(compte);
    }

    public Position consulter(String idCompte) throws CompteInconnuException, CompteClotureException {
        Compte c = findCompte(idCompte);
        OperationCompte oc = new OperationCompte(OperationCompte.OperationType.CONSULTATION, c.getSolde());
        c.getOperations().add(oc);
        c.setDateInterrogation(oc.getDateOperation());
        compteRepository.save(c);
        return new Position(oc.getValeur(), oc.getDateOperation());
    }

    public void debiter(String idCompte, double montant) throws CompteInconnuException, MontantInvalidException, SoldeInsuffisantException, CompteClotureException {
        if (montant < 0.) throw new MontantInvalidException("Le montant à débiter ne peut pas être négatif.");
        Compte c = findCompte(idCompte);
        if (c.getSolde() - montant < 0) throw new SoldeInsuffisantException("Solde insuffisant.");
        c.setSolde(c.getSolde() - montant);
        c.getOperations().add(new OperationCompte(OperationCompte.OperationType.DEBIT, montant));
        compteRepository.save(c);
    }

    public void crediter(String idCompte, double montant) throws CompteInconnuException, MontantInvalidException, CompteClotureException {
        if (montant < 0.) throw new MontantInvalidException("Le montant à créditer ne peut pas être négatif.");
        Compte c = findCompte(idCompte);
        c.setSolde(c.getSolde() + montant);
        c.getOperations().add(new OperationCompte(OperationCompte.OperationType.CREDIT, montant));
        compteRepository.save(c);
    }

    public void virer(String idCompteDebiteur, String idCompteCrediteur, double montant) throws CompteInconnuException, MontantInvalidException, SoldeInsuffisantException, CompteClotureException {
        if (montant < 0.) throw new MontantInvalidException("Le montant à virer ne peut pas être négatif.");
        final Compte cDebiteur = this.findCompte(idCompteDebiteur);
        final Compte cCrediteur = this.findCompte(idCompteCrediteur);
        if (cDebiteur.getSolde() - montant < 0) throw new SoldeInsuffisantException("Solde insuffisant.");
        cCrediteur.setSolde(cCrediteur.getSolde() + montant);
        cDebiteur.setSolde(cDebiteur.getSolde() - montant);
        cCrediteur.getOperations().add(new OperationCompte(OperationCompte.OperationType.VIREMENT_CREDIT, montant));
        cDebiteur.getOperations().add(new OperationCompte(OperationCompte.OperationType.VIREMENT_DEBIT, montant));
        compteRepository.save(cCrediteur);
        compteRepository.save(cDebiteur);
    }

    public Collection<OperationCompte> recupererOperations(String idCompte) {
        Compte c = findCompte(idCompte);
        return c.getOperations();
    }

    public Collection<Compte> getComptesByClientId(long clientId) {
        return compteRepository.findByClientId(clientId);
    }

    private Compte findCompte(String idCompte) throws CompteInconnuException, CompteClotureException {
        final Optional<Compte> c = this.compteRepository.findById(idCompte);
        if (c.isEmpty()) throw new CompteInconnuException("Le compte d'id " + idCompte + " est inconnu.");
        if (!c.get().isActif()) throw new CompteClotureException("Compte " + idCompte + " clôturé.");
        return c.get();
    }
}
