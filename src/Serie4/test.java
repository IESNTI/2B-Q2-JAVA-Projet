/*package Serie4;

import accessBD.AccessBDGen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class test {
    try {
        // Etablir la connexion ("le câble qui relie le programme Java à la BD")
        Connection connection = AccessBDGen.connecter("DbInstallations", "java", "b88iBowEv5te");
        // Créer l'instruction SQL
        String sqlInstruction = "insert into FamilleSoftware (IdFamSoft, Libelle) values( ?,?)";
        // Créer le PreparedStatement à partir de cette instruction SQL ("chariot sur câble ")
        PreparedStatement myPrepStat = connection.prepareStatement(sqlInstruction);
        // remplacer les ? par valeurs introduites par user (pour éviter les injections SQL)
        myPrepStat.setInt(1, 202);
        myPrepStat.setString(2, "Ma famille Software ");
        // Exécuter ("envoyer le chariot à la BD et demander d'exécuter l 'instruction")
        // Récupérer le nombre de lignes modifiées et l'afficher
        int nbUpdatedLines = myPrepStat.executeUpdate();
        System.out.println("Nombre de lignes modifiées: " + nbUpdatedLines);
    } catch (
    SQLException e) {
        System.out.println(e.getMessage());
    }
}*/
