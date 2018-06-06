/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ejb.embeddable.EJBContainer;
import javax.json.JsonArray;
import javax.naming.NamingException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
import utilities.DBConnector;

public class BoxManagerTest {

    private static BoxManager boxManager;
    private static EJBContainer ejbContainer;
    private static Connection connection;

    /**
     * Sets up the test enviroment with a test database and ejbcontainer
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     * @throws NamingException 
     */
    @BeforeClass
    public static void setUpClass() throws SQLException, ClassNotFoundException, FileNotFoundException, NamingException {
        //make connection to db server without specific db
        connection = DBConnector.connect("");
        //todo ändra filens sökväg (ligger under utilities paketet)
        String sqlPath = config.Config.TEST_SQL_PATH;
        Reader reader = new BufferedReader(new FileReader(sqlPath));
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.runScript(reader);
        
        //skapa container och ejb i container
        ejbContainer = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        boxManager = (BoxManager) ejbContainer.getContext().lookup("java:global/classes/BoxManager");
        //ställa om till test databasen
        boxManager.setDatabase_server("kodtest-fortnox-testdb");
    }

    /**
     * Drops the database after all tests are done and closes connection and ejbContainer
     * @throws SQLException 
     */
    @AfterClass
    public static void tearDownClass() throws SQLException {
        Statement stmt = connection.createStatement();
        //remove database after tests are done
        stmt.execute("DROP DATABASE `kodtest-fortnox-testdb`;");
        //close connection and container
        connection.close();
        ejbContainer.close();
    }
    
    /**
     * Empties the boxes table in preparation for the next test
     * @throws SQLException 
     */
    @After
    public void tearDown() throws SQLException{
        //clear table for next test
        Statement stmt = connection.createStatement();
        stmt.execute("TRUNCATE boxes");
    }

    /**
     * Test of postBox method, of class BoxManager.
     * Posts a box and asserts that the box is in the database
     */
    @Test
    public void testPostBox() throws Exception {
        String expectedName = "Test";
        double expectedWeight = 1.5;
        String expectedColor = "#000000";
        String expectedDestination = "Sweden";
        //post box
        boxManager.postBox(expectedName, expectedWeight, expectedColor, expectedDestination);
        //check if box was posted and values are correct
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM boxes");
        
        if(result.next()){
            Assert.assertEquals(expectedName, result.getString("reciever"));
            Assert.assertEquals(expectedWeight, result.getDouble("weight"), 0);
            Assert.assertEquals(expectedColor, result.getString("color"));
            Assert.assertEquals(expectedDestination, result.getString("destination"));
        }
    }

    /**
     * Test of listBoxes method, of class BoxManager.
     * Posts 4 boxes and asserts that 4 boxes were returned with boxManager.listBoxes()
     */
    @Test
    public void testListBoxes() throws Exception {
        // post some test boxes
        boxManager.postBox("Test", 1.2, "#000000", "Sweden");
        boxManager.postBox("Test", 1.6, "#000000", "Brazil");
        boxManager.postBox("Test", 2.5, "#000000", "Australia");
        boxManager.postBox("Test", 10, "#000000", "China");
        
        //get boxes
        JsonArray boxes = boxManager.listBoxes();
        
        //assert that there are 4 boxes in the array
        int expectedNumberOfBoxes = 4;
        Assert.assertEquals(expectedNumberOfBoxes, boxes.size());
    }

}
