package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DataSource is a class that contains the app database connection object
 * It provides the access to connection through static singleton instance
 * Provides connection open and close methods
 */
public class DataSource {

    /**
     * Database location info
     */
    public static final String  DB_NAME = "music.db";
    public static final String  CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\xxxxxxxxx\\Desktop\\" + DB_NAME;

    private Connection connection;

    private volatile static DataSource instance;

    private DataSource(){}

    // Lazy initialization Singleton instance
    // Thread safe and to be called once and only once -> at initialization
    public static DataSource getInstance(){
        if(instance == null){
            synchronized (DataSource.class){
                if(instance == null){
                    instance = new DataSource();
                }
            }
        }
        return instance;
    }

    // Open connection
    public Connection getConnection() {
        try{
            connection = DriverManager.getConnection(CONNECTION_STRING);
        }catch (SQLException e){
            System.out.println("Could not open connection: " + e.getMessage());
        }
        return connection;
    }

    // Close connection
    public void close(){
        try{
            if(connection != null){
                connection.close();     // idempotent
            }
        }catch (SQLException e){
            System.out.println("Could not close connection: " + e.getMessage());
        }finally {
            connection = null;
        }
    }
}
