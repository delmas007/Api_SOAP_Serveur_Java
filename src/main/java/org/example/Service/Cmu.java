package org.example.Service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.example.Server.Consultation;
import org.example.Server.DossierMedecin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "Cmu")
public class Cmu {

    @WebMethod(operationName = "ajouterDossier")
    public void addDossierPatient(@WebParam(name = "a")int isn, @WebParam(name = "b")String nom,@WebParam(name = "c")String prenom,
                                  @WebParam(name = "d")int numeroCmu,@WebParam(name = "e")String ville,@WebParam(name = "i")int age,
                                  @WebParam(name = "j")boolean masculin,@WebParam(name = "k")boolean feminin,@WebParam(name = "k")boolean enceinte) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO dossierpatient (isn,nom,prenom,numeroCmu,ville," +
                     "age,masculin,feminin,enceinte) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)")) {

            statement.setInt(1, isn);
            statement.setString(2, nom);
            statement.setString(3, prenom);
            statement.setInt(4, numeroCmu);
            statement.setString(5, ville);
            statement.setInt(6, age);
            statement.setBoolean(7, masculin);
            statement.setBoolean(8, feminin);
            statement.setBoolean(9, enceinte);

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

        @WebMethod(operationName = "afficherDossier")
        public List<DossierMedecin> AfficherDossier() {
            List<DossierMedecin> dossierMedecins = new ArrayList<>();
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM dossierpatient")) {

                while (resultSet.next()) {
                    Integer isn = resultSet.getInt("isn");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    Integer numeroCmu = resultSet.getInt("numeroCmu");
                    String ville = resultSet.getString("ville");
                    String antecedentsMedicaux = resultSet.getString("antecedentsMedicaux");
                    String historiqueVaccination = resultSet.getString("historiqueVaccination");
                    String resumesMedicaux = resultSet.getString("resumesMedicaux");
                    Integer age = resultSet.getInt("age");
                    Boolean masculin = resultSet.getBoolean("getBoolean");
                    Boolean feminin = resultSet.getBoolean("feminin");
                    Boolean enceinte = resultSet.getBoolean("enceinte");

                    DossierMedecin dossierMedecin = new DossierMedecin(isn,nom,prenom,numeroCmu,ville,antecedentsMedicaux,historiqueVaccination,
                            resumesMedicaux,age,masculin,feminin,enceinte);
                    dossierMedecins.add(dossierMedecin);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return dossierMedecins;
        }

    @WebMethod(operationName = "afficherConsultation")
    public List<Consultation> AfficherConsultation() {
        List<Consultation> consultations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM consultation")) {

            while (resultSet.next()) {
                String examenPhysique = resultSet.getString("examenPhysique");
                String DiscussionSymptomes = resultSet.getString("DiscussionSymptômes");
                String diagnostic = resultSet.getString("diagnostic");
                String antecedentsMedicaux = resultSet.getString("antecedentsMedicaux");
                String ordonnance = resultSet.getString("ordonnance");
                Integer tauxReduction = resultSet.getInt("tauxReduction");
                String code = resultSet.getString("code");
                Integer isnDossierPatient = resultSet.getInt("isn_dossierPatient");

                Consultation consultation = new Consultation(examenPhysique,DiscussionSymptomes,diagnostic,ordonnance
                        ,tauxReduction,code,isnDossierPatient);
                consultations.add(consultation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultations;
    }

    @WebMethod(operationName = "supprimerDossier")
    public void supprimerDossier(@WebParam(name = "a") int isn) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM dossierpatient WHERE isn = ?")) {

            statement.setInt(1, isn);

            // Utilisation de executeUpdate() pour exécuter la requête DELETE
            int rowsAffected = statement.executeUpdate();
            supprimerConsultation(isn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerConsultation(int isndossierPatient) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM consultation WHERE isn_dossierPatient = ?")) {

            statement.setInt(1, isndossierPatient);

            // Utilisation de executeUpdate() pour exécuter la requête DELETE
            int rowsAffected = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
