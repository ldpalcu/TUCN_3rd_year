package data_access.connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniela Palcu, created on 18.03.2018
 * This class contains methods which creates the connection to database.
 * It is modeled by using Singleton pattern.
 * This class is inspired from my Programming Techniques project which I have done last year.
 */
public class ConnectionDatabase {

    private static final Logger LOGGER = Logger.getLogger(ConnectionDatabase.class.getName());
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/assignment1?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC&sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
    private static final String USER = "root";
    private static final String PASS = "daniela";

    private static ConnectionDatabase instance = new ConnectionDatabase();

    private ConnectionDatabase() {
        try{
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private Connection createConnection(){

        Connection connectionDatabase=null;
        try{
            connectionDatabase= DriverManager.getConnection(DBURL,USER,PASS);
        }catch (SQLException e){
            LOGGER.log(Level.WARNING,"Error!It can connect to database!");
            e.printStackTrace();
        }
        return connectionDatabase;

    }

    public static Connection getConnection() {
        return (Connection) instance.createConnection();
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error when you try to close the connection!");
            }
        }
    }

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error when you try to close the query!");
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error when you try to close a result of one query!");
            }
        }
    }

    public static void main(String[] args) {
        Connection connectionDatabase = ConnectionDatabase.getConnection();

    }
}
