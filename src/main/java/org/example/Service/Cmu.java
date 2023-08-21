package org.example.Service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.example.Model.Consultationn;
import org.example.Model.DossierMedecin;

import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "Cmu")
public class Cmu {

    @WebMethod(operationName = "ajouterDossier")
    public void addDossierPatient(@WebParam(name = "a")int isn, @WebParam(name = "b")String nom,@WebParam(name = "c")String prenom,
                                  @WebParam(name = "d")int numeroCmu,@WebParam(name = "e")String ville,@WebParam(name = "i")int age,
                                  @WebParam(name = "j")boolean masculin,@WebParam(name = "k")boolean feminin,@WebParam(name = "l")boolean enceinte) {
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
    public int consultatio(@WebParam(name = "b")String examenPhysique, @WebParam(name = "c")String DiscussionSymptomes,
                           @WebParam(name = "d")String diagnostic, @WebParam(name = "e")String ordonnance, @WebParam(name = "k")int isn_dossierPatient, @WebParam(name = "l")int numeroCmu) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO consultation (examenPhysique,DiscussionSymptômes,diagnostic,ordonnance," +
                     "tauxReduction,code,isn_dossierPatient,numeroCmu) VALUES (?, ?, ?, ?, ?, ?, ?,?)")) {
            int tauxReduction = 70;
            String code = genererAlphanumerique(10);
            try(PreparedStatement statemente = connection.prepareStatement("SELECT * FROM dossierpatient WHERE isn = ?");) {
                statemente.setInt(1, isn_dossierPatient);
                ResultSet resultSet = statemente.executeQuery();
                if (resultSet.next()) {
                    Integer age = resultSet.getInt("age");
                    Boolean enceinte = resultSet.getBoolean("enceinte");
                    Integer numeroCm = resultSet.getInt("numeroCmu");

                    if (age <= 5 || enceinte) {
                        tauxReduction = 100;
                    }
                    if(numeroCm != numeroCmu){
                        return 1;
                    }
                }


            }catch (SQLException e) {
                e.printStackTrace();
            }

            statement.setString(1, examenPhysique);
            statement.setString(2, DiscussionSymptomes);
            statement.setString(3, diagnostic);
            statement.setString(4, ordonnance);
            statement.setInt(5, tauxReduction);
            statement.setString(6, code);
            statement.setInt(7, isn_dossierPatient);
            statement.setInt(8, numeroCmu);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @WebMethod(operationName = "modifierdossier")
    public void modifierDossie(@WebParam(name = "d")int isn,@WebParam(name = "e")String antecedentsMedicaux,@WebParam(name = "i")String historiqueVaccination,
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

//        @WebMethod(operationName = "afficherDossier")
//        public List<DossierMedecin> AfficherDossie() {
//            List<DossierMedecin> dossierMedecins = new ArrayList<>();
//            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
//                 Statement statement = connection.createStatement();
//                 ResultSet resultSet = statement.executeQuery("SELECT * FROM dossierpatient ")) {
//
//                while (resultSet.next()) {
//                    Integer isn = resultSet.getInt("isn");
//                    String nom = resultSet.getString("nom");
//                    String prenom = resultSet.getString("prenom");
//                    Integer numeroCmu = resultSet.getInt("numeroCmu");
//                    String ville = resultSet.getString("ville");
//                    String antecedentsMedicaux = resultSet.getString("antecedentsMedicaux");
//                    String historiqueVaccination = resultSet.getString("historiqueVaccination");
//                    String resumesMedicaux = resultSet.getString("resumesMedicaux");
//                    Integer age = resultSet.getInt("age");
//                    Boolean masculin = resultSet.getBoolean("masculin");
//                    Boolean feminin = resultSet.getBoolean("feminin");
//                    Boolean enceinte = resultSet.getBoolean("enceinte");
//
//                    DossierMedecin dossierMedecin = new DossierMedecin(isn,nom,prenom,numeroCmu,ville,antecedentsMedicaux,historiqueVaccination,
//                            resumesMedicaux,age,masculin,feminin,enceinte);
//                    dossierMedecins.add(dossierMedecin);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//            return dossierMedecins;
//        }

    @WebMethod(operationName = "afficherDossier")
    public List<DossierMedecin> AfficherDossieAPartirIsn(int isn) {
        List<DossierMedecin> dossierMedecins = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM dossierpatient WHERE isn = ?");
        ) {
            statement.setInt(1, isn);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer isnValue = resultSet.getInt("isn");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                Integer numeroCmu = resultSet.getInt("numeroCmu");
                String ville = resultSet.getString("ville");
                String antecedentsMedicaux = resultSet.getString("antecedentsMedicaux");
                String historiqueVaccination = resultSet.getString("historiqueVaccination");
                String resumesMedicaux = resultSet.getString("resumesMedicaux");
                Integer age = resultSet.getInt("age");
                Boolean masculin = resultSet.getBoolean("masculin");
                Boolean feminin = resultSet.getBoolean("feminin");
                Boolean enceinte = resultSet.getBoolean("enceinte");

                DossierMedecin dossierMedecin = new DossierMedecin(isnValue, nom, prenom, numeroCmu, ville, antecedentsMedicaux, historiqueVaccination,
                        resumesMedicaux, age, masculin, feminin, enceinte);
                dossierMedecins.add(dossierMedecin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dossierMedecins;
    }


//    @WebMethod(operationName = "afficherConsultation")
//    public List<Consultationn> AfficherConsultatio() {
//        List<Consultationn> consultations = new ArrayList<>();
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery("SELECT * FROM consultation")) {
//
//            while (resultSet.next()) {
//                String examenPhysique = resultSet.getString("examenPhysique");
//                String DiscussionSymptomes = resultSet.getString("DiscussionSymptômes");
//                String diagnostic = resultSet.getString("diagnostic");
//                String antecedentsMedicaux = resultSet.getString("antecedentsMedicaux");
//                String ordonnance = resultSet.getString("ordonnance");
//                Integer tauxReduction = resultSet.getInt("tauxReduction");
//                String code = resultSet.getString("code");
//                Integer isnDossierPatient = resultSet.getInt("isn_dossierPatient");
//
//                Consultationn consultation = new Consultationn(examenPhysique,DiscussionSymptomes,diagnostic,ordonnance
//                        ,tauxReduction,code,isnDossierPatient);
//                consultations.add(consultation);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return consultations;
//    }

    @WebMethod(operationName = "afficherConsultation")
    public List<Consultationn> AfficherConsultatio(@WebParam(name = "isn_dossierPatient") int isnDossierPatient) {
        List<Consultationn> consultations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cmu", "root", "");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM consultation WHERE isn_dossierPatient = ?")) {

            statement.setInt(1, isnDossierPatient);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String examenPhysique = resultSet.getString("examenPhysique");
                    String DiscussionSymptomes = resultSet.getString("DiscussionSymptômes");
                    String diagnostic = resultSet.getString("diagnostic");
                    String antecedentsMedicaux = resultSet.getString("antecedentsMedicaux");
                    String ordonnance = resultSet.getString("ordonnance");
                    Integer tauxReduction = resultSet.getInt("tauxReduction");
                    String code = resultSet.getString("code");
                    Integer isnDossierPatientFromDB = resultSet.getInt("isn_dossierPatient");

                    Consultationn consultation = new Consultationn(examenPhysique, DiscussionSymptomes, diagnostic, ordonnance
                            , tauxReduction, code, isnDossierPatientFromDB);
                    consultations.add(consultation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultations;
    }


    @WebMethod(operationName = "supprimerDossier")
    public void supprimerDossie(@WebParam(name = "a") int isn) {
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

    public String genererAlphanumerique(int longueur) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(longueur);
        String CARACTERES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < longueur; i++) {
            int index = random.nextInt(CARACTERES.length());
            sb.append(CARACTERES.charAt(index));
        }

        return sb.toString();
    }
}
