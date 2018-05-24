package managers;

import utilities.CountryMultipliers;
import utilities.DBConnector;

import javax.ejb.Stateless;
import javax.json.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

@Stateless
public class BoxManager {
    /**
     * Method to post a box to the db
     * @param name string, name om the reciever
     * @param weight double, weight in kilograms
     * @param color string, hexadecimal value of the color
     * @param destination string, destination, either Sweden, China, Australia or Brazil
     * @return status code
     */
    public int postBox(String name, double weight, String color, String destination){
        Connection connection = null;
        try {
            // make connection and prepare statement
            connection = DBConnector.connect();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO boxes VALUES (NULL, ?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setDouble(2, weight);
            stmt.setString(3, destination);
            stmt.setString(4, color);

            //post
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // server error
            return 500;
        }
        //created
        return 201;
    }

    /**
     * Method to get an array with all the boxes in JSON format
     * @return A JSON array with all the boxes
     */
    public JsonArray listBoxes(){
        JsonArrayBuilder boxList = Json.createArrayBuilder();
        try {
            //make connection to db and prepare statement
            Connection connection = DBConnector.connect();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM boxes");
            //execute query
            ResultSet result = stmt.executeQuery();

            //build object from result and add to array
            while(result.next()){
                String country = result.getString("destination");
                double weight = result.getDouble("weight");
                double cost = calculateCost(country, weight);

                //formatter to output with 2 decimals
                DecimalFormat formatter = new DecimalFormat("0.00");

                JsonObject box = Json.createObjectBuilder()
                        .add("id", result.getInt("id"))
                        .add("reciever", result.getString("reciever"))
                        .add("weight", formatter.format(weight))
                        .add("destination", result.getString("destination"))
                        .add("color", result.getString("color"))
                        .add("cost", formatter.format(cost)).build();
                boxList.add(box);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return boxList.build();
    }

    /**
     * Calculates the cost of a box depending on the country and the weight
     * @param country Sweden, China, Australia or Brazil
     * @param weight weight in kilograms
     * @return the cost in Swedish crowns
     */
    private double calculateCost(String country, double weight) {
        switch (country){
            case "Sweden":
                return weight * CountryMultipliers.SWEDEN_MULTIPLIER;
            case "China":
                return weight * CountryMultipliers.CHINA_MULTIPLIER;
            case "Australia":
                return weight * CountryMultipliers.AUSTRALIA_MULTIPLIER;
            case "Brazil":
                return weight * CountryMultipliers.BRAZIL_MULTIPLIER;
        }
        return 0;
    }
}
