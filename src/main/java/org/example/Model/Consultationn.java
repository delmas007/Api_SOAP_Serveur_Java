package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Consultationn implements Serializable {

    private String examenPhysique;
    private String discussionSymptomes;
    private String diagnostic;
    private String ordonnance;
    private int tauxReduction;
    private String code;
    private int isnDossierPatient;
}
