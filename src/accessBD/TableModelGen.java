package accessBD;

import javax.swing.table.*;
import java.util.*;



/* Tout objet de cette classe est un modèle de données,
 * à savoir un ensemble de lignes et de colonnes sous forme d'un tableau à deux dimensions.
 * Un modèle de données contient également le nom des colonnes.
 * Un modèle de données peut être affiché dans une JTable.
 */
public class TableModelGen extends AbstractTableModel
{// Liste des noms de colonnes
    ArrayList <String> nomCol = new ArrayList <String>();
    // Données sous forme d'une liste à deux dimensions qui est une liste d'Objects
    ArrayList <Object> contenu = new ArrayList<Object>();
    // Liste d'objets types (pour retrouver le type des colonnes)
    ArrayList <Object> objetTypes = new ArrayList <Object>();

    TableModelGen(ArrayList <String> col, ArrayList <Object> lig, ArrayList <Object> types)
    {nomCol = col;
        contenu = lig;
        objetTypes = types;
    }

    // Retourne le nombre de colonnes
    public int getColumnCount()
    {return nomCol.size();
    }

    // Retourne le nombre de lignes
    public int getRowCount()
    {return contenu.size();
    }

    // Retourne le nom de la colonne numéro "col"
    public String getColumnName(int col)
    {return  nomCol.get(col);
    }

    // Retourne la valeur de la cellule à l'intersection de la ligne numéro "row" et de la colonne numéro "col"
    public Object getValueAt(int row, int col)
    { ArrayList vect = (ArrayList)(contenu.get(row));
        return vect.get(col);
    }

    /* Retourne la classe correspondant aux valeurs de la colonne numéro "c".
     * Cette méthode est appelée entre autres lors de l'affichage des colonnes dans la JTable
     * afin d'afficher le bon type des colonnes (numériques alignés à droite,
     * chaînes de caractères alignées à gauche, booléens sous forme de cases à cocher, date avec format de date, ...)
     */
    public Class getColumnClass (int c)
    {return (objetTypes.get(c)).getClass();
    }

}