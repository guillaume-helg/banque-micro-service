package org.miage.tpae.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationCompte {
    public enum OperationType {OUVERTURE, CLOTURE, DEBIT, CREDIT, VIREMENT_CREDIT, VIREMENT_DEBIT, CONSULTATION}

    private OperationType operationType;
    private double valeur;
    private Calendar dateOperation;

    public OperationCompte(OperationType operationType, double valeur) {
        this.operationType = operationType;
        this.valeur = valeur;
        this.dateOperation = GregorianCalendar.getInstance();
    }
}
