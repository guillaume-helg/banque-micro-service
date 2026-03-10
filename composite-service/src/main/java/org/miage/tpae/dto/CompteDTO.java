package org.miage.tpae.dto;

import lombok.Data;
import java.util.Calendar;

@Data
public class CompteDTO {
    private String id;
    private double solde;
    private boolean actif;
    private Calendar dateInterrogation;
    private Long clientId;
}
