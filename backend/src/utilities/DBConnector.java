package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    public static Connection connect() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost/kodtest-fortnox";
        String user = "root";
        String pw = "";
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, user, pw);
    }
}
