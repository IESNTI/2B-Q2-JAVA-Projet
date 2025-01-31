package accessBD;

import java.sql.*;
import java.util.*;


public class AccessBDGen
{
    /* Crée une connexion à une base de données à partir du nom logique de la BD,
     * du nom de l'utilisateur et du mot de passe.
     */
    public static Connection connecter(String bd, String user, String pass)
            throws SQLException
    {Connection connexion = null;
        try { Class.forName("com.mysql.jdbc.Driver");
            connexion = DriverManager.getConnection("jdbc:mysql://unixfox.cg8i3hgecpfl.eu-central-1.rds.amazonaws.com:3306/"+bd+"?useSSL=false",user,pass);
        }
        catch (ClassNotFoundException ex)
        {ex.printStackTrace();
        }
        return connexion;
    }




    /* Exécute une requête d'accès en lecture à la BD (select)
     * et récupère le modéle des données résultant de la requête (ensemble de lignes et de colonnes)
     */
    public static TableModelGen creerTableModel (PreparedStatement prepStat)
            throws SQLException
    {ResultSet donnees = prepStat.executeQuery();
        ArrayList <String> nomColonnes = creerNomColonnes(donnees);
        ArrayList <Object> lignes = creerLignes(donnees);
        ArrayList <Object> objetTypes = creerObjetTypes(donnees);
        TableModelGen model = new TableModelGen(nomColonnes,lignes, objetTypes);
        return model;
    }

    // Méthode appelée par la méthode creerTableModel pour récupérer les noms des colonnes
    private static ArrayList <String> creerNomColonnes(ResultSet donnees)
            throws SQLException
    {ResultSetMetaData meta = donnees.getMetaData();
        ArrayList <String> nomColonnes = new ArrayList <String>();

        for (int i = 1; i <= meta.getColumnCount();i++)
        { nomColonnes.add(meta.getColumnName(i));}

        return nomColonnes;
    }

    // Méthode appelée par la méthode creerTableModel pour récupérer les lignes des données
    private static ArrayList <Object> creerLignes(ResultSet donnees)
            throws SQLException
    {ResultSetMetaData meta = donnees.getMetaData();
        ArrayList <Object> lignes = new ArrayList <Object>();

        while (donnees.next())
        {lignes.add(ligneSuivante(donnees,meta)); }

        return lignes ;
    }

    // Méthode appelée par la méthode creerLignes pour récupérer une ligne de données
    private static ArrayList <Object> ligneSuivante(ResultSet donnees, ResultSetMetaData meta)
            throws SQLException
    {ArrayList <Object> ligneCourante = new ArrayList <Object>();
        String stringLu;
        int entierLu;
        double doubleLu;
        float floatLu;
        boolean booleenLu;
        java.util.Date dateLue;

        for (int i = 1; i <= meta.getColumnCount();i++)
        {switch (meta.getColumnType(i))
        {case Types.VARCHAR:
            stringLu = donnees.getString(i);
            ligneCourante.add(donnees.wasNull()?null:stringLu);
            break;
            case Types.CHAR:
                stringLu = donnees.getString(i);
                ligneCourante.add(donnees.wasNull()?null:stringLu);
                break;
            case Types.INTEGER:
                entierLu = donnees.getInt(i);
                ligneCourante.add(donnees.wasNull()?null:new Integer(entierLu));
                break;
            case  Types.SMALLINT :
                entierLu = donnees.getInt(i);
                ligneCourante.add(donnees.wasNull()?null:new Integer(entierLu));
                break;
            case  Types.TINYINT:
                entierLu = donnees.getInt(i);
                ligneCourante.add(donnees.wasNull()?null:new Integer(entierLu));
                break;
            case Types.REAL:
                floatLu = donnees.getFloat(i);
                ligneCourante.add(donnees.wasNull()?null:new Float(floatLu));
                break;
            case Types.DOUBLE:
                doubleLu = donnees.getDouble(i);
                ligneCourante.add(donnees.wasNull()?null:new Double(doubleLu));
                break;
            case Types.DATE:
            case Types.TIMESTAMP:
                dateLue = donnees.getDate(i);
                ligneCourante.add(donnees.wasNull()?null:dateLue);
                break;
            case Types.BIT:
                booleenLu = donnees.getBoolean(i);
                ligneCourante.add(donnees.wasNull()?null:new Boolean(booleenLu));
                break;
        }
        }
        return ligneCourante;
    }

    // Méthode appelée par la méthode creerTableModel pour récupérer les types des colonnes
    private static ArrayList <Object> creerObjetTypes(ResultSet donnees)
            throws SQLException
    {ResultSetMetaData meta = donnees.getMetaData();
        ArrayList <Object> objetTypes = new ArrayList <Object>();
        String stringLu = "bidon";
        int entierLu = 1;
        double doubleLu = 1.0;
        float floatLu = 1.0f;
        boolean booleenLu = true;
        java.util.Date dateLue = new java.util.Date();

        for (int i = 1; i <= meta.getColumnCount();i++)
        {switch (meta.getColumnType(i))
        {case Types.VARCHAR: objetTypes.add(stringLu);
            break;
            case Types.CHAR: objetTypes.add(stringLu);
                break;
            case Types.INTEGER: objetTypes.add(new Integer(entierLu));
                break;
            case Types.SMALLINT: objetTypes.add(new Integer(entierLu));
                break;
            case Types.TINYINT: objetTypes.add(new Integer(entierLu));
                break;
            case Types.REAL: objetTypes.add(new Float(floatLu));
                break;
            case Types.DOUBLE: objetTypes.add(new Double(doubleLu));
                break;
            case Types.DATE:
            case Types.TIMESTAMP: objetTypes.add(dateLue);
                break;
            case Types.BIT: objetTypes.add(new Boolean(booleenLu));
                break;
        }
        }
        return objetTypes;
    }

    /* Récupère sous forme d'un liste les données correspondant à une colonne dans une table de la BD.
     * La requete doit être un select portant sur une seule colonne
     */
    public static Object[] creerListe1Colonne(PreparedStatement prepStat)
            throws SQLException
    {
        int max;
        Object[] uneColonne ;
        int index = 0;

        String stringLu;
        int entierLu;
        float floatLu;
        double doubleLu;
        boolean booleenLu;
        java.util.Date dateLue;

        ResultSet donnees2 = prepStat.executeQuery();
        max=0;
        while (donnees2.next()){max ++;}

        uneColonne = new Object[max];

        ResultSet donnees = prepStat.executeQuery();
        ResultSetMetaData meta = donnees.getMetaData();

        while (donnees.next())
        {switch (meta.getColumnType(1))
        {case Types.VARCHAR:
            stringLu = donnees.getString(1);
            if (donnees.wasNull() == false)
            {
                uneColonne[index] = stringLu;
                index++;
            }
            break;
            case Types.CHAR:
                stringLu = donnees.getString(1);
                if (donnees.wasNull() == false)
                {
                    uneColonne[index] = stringLu;
                    index++;
                }
                break;
            case Types.INTEGER:
                entierLu = donnees.getInt(1);
                if (donnees.wasNull() == false)
                {
                    uneColonne[index] = new Integer(entierLu);
                    index++;
                }
                break;
            case Types.SMALLINT:
                entierLu = donnees.getInt(1);
                if (donnees.wasNull() == false)
                {
                    uneColonne[index] = new Integer(entierLu);
                    index++;
                }
                break;
            case Types.TINYINT:
                entierLu = donnees.getInt(1);
                if (donnees.wasNull() == false)
                {
                    uneColonne[index] = new Integer(entierLu);
                    index++;
                }
                break;
            case Types.REAL:
                floatLu = donnees.getFloat(1);
                if (donnees.wasNull() == false)
                {
                    uneColonne[index]=  new Float(floatLu);
                    index++;
                }
                break;
            case Types.DOUBLE:
                doubleLu = donnees.getDouble(1);
                if (donnees.wasNull() == false)
                {
                    uneColonne[index]=  new Double(doubleLu);
                    index++;
                }
                break;
            case Types.TIMESTAMP:
                dateLue = donnees.getDate(1);
                if (donnees.wasNull() == false)
                {
                    uneColonne[index]= dateLue;
                    index++;
                }
                break;
            case Types.BIT:
                booleenLu = donnees.getBoolean(1);
                if (donnees.wasNull() == false)
                {
                    uneColonne[index]=  new Boolean(booleenLu);
                    index++;
                }
                break;
        }
        }
        return uneColonne ;
    }

}