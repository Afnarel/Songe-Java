package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLiteDB {

	protected Connection connection;
	protected Statement statement;

	/*
    this.statement.executeUpdate("CREATE TABLE " + table + tables.get(table) + ";");
    this.statement.executeUpdate("DROP TABLE IF EXISTS " + table + ";");
    this.statement.executeUpdate("DELETE FROM " + table + ";");
    this.statement.executeUpdate("INSERT INTO scores_joueurs(discipline, resultat, num_joueur) VALUES(" + 
                    "'" + s.getCompetence().toString() + "'" + "," +
                    "'" + s.getScore() + "'" + "," +
                    playerID
                    + ");");
    */
	
	public SQLiteDB() {

		try {
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager
					.getConnection("jdbc:sqlite:data.db3"); // jdbc:sqlite:sample.db
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			this.statement = this.connection.createStatement();
			// this.statement.setQueryTimeout(30); // set timeout to 30 sec
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			// connection close failed.
			System.err.println(e);
		}
	}
	
	public void executeQuery(String query) {
		if(query.startsWith("SELECT") || query.startsWith("select")) {
			executeSelectQuery(query);
		}
		else {
			executeUpdateQuery(query);
		}
	}
	
	/*
	private boolean stringInTab(String str, String[] tab) {
		for(String s : tab) {
			if(str.equals(s))
				return true;
		}
		return false;
	}
	*/

	/**
	 * La query doit probablement se finir par un point-virgule... A voir
	 * 
	 * @param query
	 */
	private void executeSelectQuery(String query) {
		ResultSet rs;
		try {
			rs = this.statement.executeQuery(query);
			while(rs.next()) {
				for(int i=1; i<=rs.getMetaData().getColumnCount(); i++) {
					System.out.println(rs.getMetaData().getColumnName(i) + ": " + rs.getString(i));
					//System.out.println(rs.getMetaData().getColumnName(i));
					//System.out.println(rs.getMetaData().getColumnLabel(i));
				}
			}
			/*
			while (rs.next()) {
                System.out.println(rs.getString("nom"));
                System.out.println(rs.getString("type"));
                System.out.println(rs.getString("duree"));
                System.out.println(rs.getInt("points"));
                System.out.println("======");
            }
            */
			rs.close();
			System.out.println("La requête (" + query + ") a été exécutée avec succès.");
		} catch (SQLException e) {
			System.err.println("Problème lors de l'exécution de la requête (" + query + ").");
		}
	}
	
	
	private void executeUpdateQuery(String query) {
	    try {
            this.statement.executeUpdate(query);
            System.out.println("La requête (" + query + ") a été exécutée avec succès.");
        } catch (SQLException e) {
            System.err.println("Problème lors de l'exécution de la requête (" + query + ").");
            e.printStackTrace();
        }
	}

}
