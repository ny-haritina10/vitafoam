package mg.itu.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private static String driver = "oracle.jdbc.driver.OracleDriver";
    private static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    
    private static String username = "vitafoam";
    private static String password = "vitafoam";

    public static Connection getConnection() {
        Connection con = null;
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        } 

        catch(Exception e)
        { e.printStackTrace(); }
        
        return con;
    }
}