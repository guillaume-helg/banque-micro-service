package org.miage.tpae.dao;

import org.miage.tpae.entities.Compte;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CompteRepository extends MongoRepository<Compte, String> {
    List<Compte> findByClientId(Long clientId);
}
