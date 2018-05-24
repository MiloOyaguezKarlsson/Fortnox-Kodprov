package managers;

import utilities.DBConnector;

import javax.ejb.Stateless;
import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Stateless
public class BoxManager {
    public int postBox(String name, double weight, String color, String destination){
        Connection connection = null;
        try {
            connection = DBConnector.connect();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO boxes VALUES (NULL, ?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setDouble(2, weight);
            stmt.setString(3, destination);
            stmt.setString(4, color);

            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return 500;
        }
        return 200;
    }
}
