package org.example.Server;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Consultation {

    private String examenPhysique;
    private String discussionSymptomes;
    private String diagnostic;
    private String ordonnance;
    private int tauxReduction;
    private String code;
    private int isnDossierPatient;
}
