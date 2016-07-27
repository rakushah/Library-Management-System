package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	Connection c = null;
	// raj location // c =
	// DriverManager.getConnection("jdbc:sqlite:C:/Users/Owner/workspace/MppProject/lib/LMS.db");

	// static DatabaseConnection connectionObject;

	//static DatabaseConnection connectionObject;
	public DatabaseConnection(){
		 try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Owner/Desktop/Nabin/lms/lib/LMS.db");
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
	}

	public Connection getC() {
		return c;
	}

	/*
	 * public static Connection getconnection(){ connectionObject= new
	 * DatabaseConnection(); return connectionObject.c; }
	 */

}
