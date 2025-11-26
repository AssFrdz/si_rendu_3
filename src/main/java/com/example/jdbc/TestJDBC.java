package main.java.com.example.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TestJDBC
{
    Connection       db=null;
    Statement        sql=null;
    DatabaseMetaData dbmd;

    public TestJDBC() throws Exception
    {
        try {
            // Load connection from database.properties in resources folder
        System.out.println("Loading connection from /database.properties\n");
        db = ConfigConnection.getConnection();

        db.setAutoCommit(false);

        dbmd = db.getMetaData();

        System.out.println("Connection to SGBD "+ dbmd.getDatabaseProductName()+ " version "+
                    dbmd.getDatabaseProductVersion()+ " database " +
                    dbmd.getURL()+ " \nusing " + dbmd.getDriverName() + " version "+
                    dbmd.getDriverVersion()+ " " + "successful.\n");

            sql = db.createStatement();

            String sqlText;

            // Creation of a table Note (MySQL syntax)
           sqlText = "CREATE TABLE Note ( " +
                   "Note_ID INT AUTO_INCREMENT, " +
                   "Inscription_ID INT NOT NULL," +
               "Note FLOAT," +
            "CONSTRAINT PK_Notes PRIMARY KEY (Note_ID)," +
                "CONSTRAINT FK_Notes_Inscription " +
                    "FOREIGN KEY (Inscription_ID) " +
                "REFERENCES Inscription (Inscription_ID) " +
                  ") ENGINE=InnoDB;";

           System.out.println("Executing this command: "+sqlText+"\n");           sql.executeUpdate(sqlText);
           db.commit();

            // Insertion into table Inscription (MySQL syntax - no nextval)
            sqlText = "INSERT INTO Inscription (Etudiant_ID, Enseignement_ID, Master_ID) " +
                    "SELECT " +
                    "(SELECT Etudiant_ID FROM Etudiant WHERE Nom='SUFFIT'), " +
                    "(SELECT Enseignement_ID FROM Enseignement WHERE Intitule LIKE '%HIBERNATE%'), " +
                    "(SELECT Master_ID FROM Enseignement WHERE Intitule LIKE '%HIBERNATE%');";
            System.out.println("Executing this command: "+sqlText+"\n");
            sql.executeUpdate(sqlText);

            // Insertion into table Note (MySQL syntax - no nextval)
            sqlText = "INSERT INTO Note (Inscription_ID, Note) " +
                    "SELECT i.Inscription_ID, 14 " +
                    "FROM Inscription i " +
                    "INNER JOIN Enseignement e ON e.Enseignement_ID = i.Enseignement_ID " +
                    "INNER JOIN Etudiant et ON et.Etudiant_ID = i.Etudiant_ID " +
                    "WHERE e.Intitule LIKE '%HIBERNATE%' " +
                    "AND et.Nom = 'SUFFIT' " +
                    "AND e.Master_ID = i.Master_ID;";
            System.out.println("Executing this command : "+sqlText+"\n");
            sql.executeUpdate(sqlText);
            sql.executeUpdate(sqlText);
            db.commit();

            //Update of table Salle (MySQL uses different locking syntax)
            sqlText="SELECT Capacite FROM Salle " +
                    "WHERE Batiment = 'B' " +
                    "AND Numero_Salle = '020' FOR UPDATE";
            System.out.println("Executing this command : "+sqlText+"\n");
            ResultSet rset = sql.executeQuery(sqlText);

            if (rset.next()) {
                System.out.println("Capacité de la salle B020 avant MàJ : "
                        + rset.getInt(1) + "\n");

                sqlText = "UPDATE Salle SET Capacite = 30 WHERE Batiment = 'B' AND Numero_Salle = '020'";
                System.out.println("Executing this command: "+sqlText+"\n");
                sql.executeUpdate(sqlText);
                System.out.println (sql.getUpdateCount()+
                        " rows were updated by this statement\n");
                db.commit();

                rset = sql.executeQuery("SELECT Capacite FROM Salle " +
                        "WHERE Batiment = 'B' " +
                        "AND Numero_Salle = '020'");
                while (rset.next()) {
                    System.out.println("Capacité de la salle B020 après MàJ : "
                            + rset.getInt(1) + "\n");
                }
            }
            else {
                db.rollback();
                System.out.println("Pas de salle B020 : " +
                        "Libération du verrou posé lors du SELECT ...FOR UPDATE. \n" );
            }

            rset = sql.executeQuery("SELECT Date_Resa FROM Reservation " +
                    "WHERE Batiment = 'B' " +
                    "AND Numero_Salle = '022'");
            while (rset.next()) {
                System.out.println("Nom de la table : (généralement vide bug du driver) "
                        + rset.getMetaData().getTableName(1));
                System.out.println("Type de la colonne  : "
                        + rset.getMetaData().getColumnTypeName(1));
                System.out.println("Nom de la colonne : "
                        + rset.getMetaData().getColumnName(1) + "\n");

                System.out.println("Date de reservation du nuplet résultat: "
                        + rset.getDate(1) + "\n");

                java.sql.Date dateResa = rset.getDate(1);
                if (! rset.wasNull()) {
                    String dateResaF =
                            DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.FRANCE).format(dateResa);
                    SimpleDateFormat formateur =
                            (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.FRANCE);
                    formateur.applyPattern("MMMM");
                    String mois = formateur.format(dateResa);
                    System.out.println("Affichage de la date de manière formaté:");
                    System.out.println("Réservation pour le : " + dateResaF);
                    System.out.println("Au mois de " + mois);
                }
                else
                    System.out.println();
            }

            System.out.println("\n\nNow demonstrating a prepared statement...");
            sqlText = "INSERT INTO Salle VALUES (?,?,?)";
            System.out.println("The Statement looks like this: "+sqlText+"\n");
            System.out.println("Looping several times filling in the fields...\n");
            PreparedStatement ps = db.prepareStatement(sqlText);

            String [] NumBatiment = {"A", "B", "C", "P", "D"};
            String [] NumSalle = {"208", "026", "405", "340", "120"};
            int lenNB = NumBatiment.length;
            for (int i=0, c=30 ; (i<lenNB) && (c<35) ;c++,i++)
            {
                System.out.println(i+" " + NumBatiment[i]+ " " + NumSalle[i]+ "...\n");
                ps.setString(1,NumBatiment[i]);
                ps.setString(2,NumSalle[i]);
                ps.setInt(3,c);
                ps.executeUpdate();
            }
            db.commit();
            ps.close();

            System.out.println("Now executing the command: "+
                    "SELECT * FROM Salle");
            ResultSet results = sql.executeQuery("SELECT * FROM Salle");
            System.out.println("Affichage des nuplets de la relation Salle après insertion :");
            if (results != null)
            {
                while (results.next())
                {
                    System.out.println("Batiment = " +results.getString(1)+
                            "; Numero de salle = "+results.getString("Numero_Salle")+
                            "; Capacité = "+results.getInt("Capacite")+"\n");
                }
            }
            results.close();

        }
        catch (SQLException ex)
        {
            System.out.println("***Exception:\n"+ex);
            ex.printStackTrace();
            System.out.println("\nROLLBACK!\n"+ex);
            if (db != null) db.rollback();
        }
        finally {
            System.out.println("Deconnexion");
            if (sql != null) sql.close();
            if (db != null) db.close();
        }
    }

    public static void main (String args[])
    {
        try
        {
            new TestJDBC();
        }
        catch (Exception ex)
        {
            System.out.println("***Exception:\n"+ex);
            ex.printStackTrace();
        }
    }
}