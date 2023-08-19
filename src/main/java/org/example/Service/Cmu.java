package org.example.Service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebService(serviceName = "Cmu")
public class Cmu {

    @WebMethod(operationName = "ajouterDossier")
    public void addDossierPatient(@WebParam(name = "a")int isn, @WebParam(name = "b")String nom,@WebParam(name = "c")String prenom,
                                  @WebParam(name = "d")int numeroCmu,@WebParam(name = "e")String ville,@WebParam(name = "i")int age,
                                  @WebParam(name = "j")boolean masculin,@WebParam(name = "k")boolean feminin) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO dossierpatient (isn,nom,prenom,numeroCmu,ville," +
                     "age,masculin,feminin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            statement.setInt(1, isn);
            statement.setString(2, nom);
            statement.setString(3, prenom);
            statement.setInt(4, numeroCmu);
            statement.setString(5, ville);
            statement.setInt(6, age);
            statement.setBoolean(7, masculin);
            statement.setBoolean(8, feminin);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @WebMethod(operationName = "consultation")
    public void consultation(@WebParam(name = "b")String examenPhysique,@WebParam(name = "c")String DiscussionSymptomes,
                                  @WebParam(name = "d")String diagnostic,@WebParam(name = "e")String ordonnance,@WebParam(name = "i")int tauxReduction,
                                  @WebParam(name = "j")int code,@WebParam(name = "k")int isn_dossierPatient) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO consultation (examenPhysique,DiscussionSymptômes,diagnostic,numeroCmu,ordonnance," +
                     "tauxReduction,code,isn_dossierPatient) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, examenPhysique);
            statement.setString(2, DiscussionSymptomes);
            statement.setString(3, diagnostic);
            statement.setString(4, ordonnance);
            statement.setInt(5, tauxReduction);
            statement.setInt(6, code);
            statement.setInt(7, isn_dossierPatient);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @WebMethod(operationName = "modifierdossier")
    public void modifierDossier(@WebParam(name = "d")int isn,@WebParam(name = "e")String antecedentsMedicaux,@WebParam(name = "i")String historiqueVaccination,
                                @WebParam(name = "j")String resumesMedicaux) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
             PreparedStatement statement = connection.prepareStatement("UPDATE dossierpatient SET antecedentsMedicaux = ?, historiqueVaccination = ?, resumesMedicaux = ? WHERE isn = ?")) {

            statement.setString(1, antecedentsMedicaux);
            statement.setString(2, historiqueVaccination);
            statement.setString(3, resumesMedicaux);
            statement.setInt(4, isn);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
