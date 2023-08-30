-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 29 août 2023 à 00:19
-- Version du serveur : 5.7.36
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `cmu`
--

-- --------------------------------------------------------

--
-- Structure de la table `consultation`
--

DROP TABLE IF EXISTS `consultation`;
CREATE TABLE IF NOT EXISTS `consultation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `examenPhysique` text,
  `DiscussionSymptômes` text,
  `diagnostic` text,
  `ordonnance` text,
  `tauxReduction` int(11) DEFAULT NULL,
  `code` varchar(250) DEFAULT NULL,
  `isn_dossierPatient` int(11) DEFAULT NULL,
  `numeroCmu` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `isn_dossierPatient` (`isn_dossierPatient`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `consultation`
--

INSERT INTO `consultation` (`id`, `examenPhysique`, `DiscussionSymptômes`, `diagnostic`, `ordonnance`, `tauxReduction`, `code`, `isn_dossierPatient`, `numeroCmu`) VALUES
(6, 'Examen complet', 'Fièvre et fatigue', 'Grippe saisonnière', 'Paracétamol et repos', 70, 'YjwoH0oQ2h', 54123, 543214),
(7, 'Examen cardiaque', 'Douleurs thoraciques', 'Angine de poitrine', 'Nitroglycérine et repos', 70, 'dCj5U4oRIZ', 43564, 123458),
(8, 'Examen ophtalmologique', 'Vision floue', 'Myopie', 'Lunettes de correction', 70, 'hdX8xun7c5', 65213, 678909);

-- --------------------------------------------------------

--
-- Structure de la table `dossierpatient`
--

DROP TABLE IF EXISTS `dossierpatient`;
CREATE TABLE IF NOT EXISTS `dossierpatient` (
  `isn` int(11) NOT NULL,
  `nom` varchar(250) DEFAULT NULL,
  `prenom` varchar(250) DEFAULT NULL,
  `numeroCmu` int(11) DEFAULT NULL,
  `ville` varchar(250) DEFAULT NULL,
  `antecedentsMedicaux` text,
  `historiqueVaccination` text,
  `resumesMedicaux` text,
  `age` int(11) DEFAULT NULL,
  `masculin` tinyint(1) DEFAULT NULL,
  `feminin` tinyint(1) DEFAULT NULL,
  `enceinte` tinyint(1) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`isn`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `dossierpatient`
--

INSERT INTO `dossierpatient` (`isn`, `nom`, `prenom`, `numeroCmu`, `ville`, `antecedentsMedicaux`, `historiqueVaccination`, `resumesMedicaux`, `age`, `masculin`, `feminin`, `enceinte`, `id`) VALUES
(43564, 'Doe', 'John', 123458, 'Abidjan', 'Asthme', 'Vaccin contre la grippe 2020', 'Aucun', 30, 1, 0, 0, 12),
(54123, 'Johnson', 'Michael', 543214, 'Abidjan', 'Diabète de type 2', 'Vaccination COVID-19', 'Aucun', 55, 1, 0, 0, 14),
(65213, 'Smith', 'Jane', 678909, 'Abidjan', 'Hypertension', 'Aucun', 'Opération en 2015', 45, 0, 1, 0, 13);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prenom` varchar(250) DEFAULT NULL,
  `nom` varchar(250) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  `medecin` tinyint(1) DEFAULT NULL,
  `employerCmu` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `prenom`, `nom`, `code`, `medecin`, `employerCmu`) VALUES
(2, 'Alice', 'Johnson', 84321, 1, 0),
(3, 'Bob', 'Williams', 98765, 0, 1);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `consultation`
--
ALTER TABLE `consultation`
  ADD CONSTRAINT `consultation_ibfk_1` FOREIGN KEY (`isn_dossierPatient`) REFERENCES `dossierpatient` (`isn`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
