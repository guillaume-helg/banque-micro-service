package org.miage.tpae.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.List;

@Document(collection = "comptes")
@Data
@NoArgsConstructor
public class Compte {
    @Id
    private String id;
    private double solde;
    private boolean actif = true;
    private Calendar dateInterrogation;
    private Long clientId;
    private List<OperationCompte> operations;

    @Override
    public String toString() {
        return "Compte{" +
                "id='" + id + '\'' +
                ", solde=" + solde +
                ", actif=" + actif +
                ", dateInterrogation=" + dateInterrogation +
                ", clientId=" + clientId +
                '}';
    }
}
