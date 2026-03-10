package org.miage.tpae.dto;

import lombok.Data;
import java.util.List;

@Data
public class ClientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private List<CompteDTO> comptes;
}
