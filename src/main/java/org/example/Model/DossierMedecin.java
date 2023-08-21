package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@AllArgsConstructor
@Data
public class DossierMedecin implements Serializable {
    private int isn;
    private String nom;
    private String prenom;
    private int numeroCmu;
    private String ville;
    private String antecedentsMedicaux;
    private String historiqueVaccination;
    private String resumesMedicaux;
    private int age;
    private boolean masculin;
    private boolean feminin;
    private boolean enceinte;
}
