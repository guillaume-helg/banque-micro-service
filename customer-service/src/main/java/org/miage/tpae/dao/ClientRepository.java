package org.miage.tpae.dao;

import org.miage.tpae.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByPrenomAndNom(String prenom, String nom);
}
