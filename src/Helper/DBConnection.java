package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection conn = null;

    public DBConnection() {

    }

    public Connection connDb() {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital?user=root&password=rootroot");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null; 
        }
    }
}