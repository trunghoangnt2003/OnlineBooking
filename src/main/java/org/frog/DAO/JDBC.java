package org.frog.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBC {
    public static Connection getConnection() {
        String  connectionURL = "jdbc:sqlserver://localhost:1433;databaseName=Prog_DB;user=hyamyyou1;password=123;encrypt=true;trustServerCertificate=true";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection c = DriverManager.getConnection(connectionURL);
            return c;
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void closeConnection(Connection c) {
        if(c!=null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
