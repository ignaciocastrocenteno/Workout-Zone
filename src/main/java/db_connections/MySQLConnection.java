package db_connections;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnection {
    /*
        This static method 'getConnection()' establishes the connection with the MySQL database instance for the
        project. We will need to import the package 'java.sql.Connection' which will allow us to generate the
        connection between the Java program and the database engine. If the package seems to be unavailable or
        isn't recognized by the IDE, we'll need to use the 'Invalide Cachés' feature on the 'File' panel.
     */
    public static Connection getDBConnection() {
        Connection connection = null;

         /*
            Here the information needed to establish a connection to a database engine. This information is found in
            'Session' panel in MySQL Workbench.
         */
            // The name of the database we are going to use.
            String dbName = "workout_zone_db";

            /*
               Other relevant information within the 'Session' tab, that could be useful:
                - Name: Name of the server instance, that's serving the database/s available. If
                        unchanged, the server's default name will be shown in this field.
                - Host: The name of the computer we're connecting to, which can be a personal computer or a server.
                        The host can be referenced with a name or with an IP address (e.g. the IP address for the
                        localhost is 127.0.0.1 and would be the same to say 'localhost' than this IP address).
                - Port: The specific port where the server is exposing the database/s to work on. It functions as
                        external gateway to connect to the server and then accessing the visible databases.
                - Login user: The user that's currently logged in working on the schemas. The privileges for each
                              user are going to be different, so they could access different levels of depth or
                              content that are available inside the database but aren't visible for all users.
                 */
            String serverURL = "jdbc:mysql://localhost:3306/" + dbName;
            String user = "root";
            String password = "admin";

            // The connection process to the database could potentially throw exceptions, so we wrap the code in a try/catch block.
            try {
                /*
                    To start the connection process to the database through the server, we have to load in memory
                    the corresponding MySQL dependency class to begin the execution. In this case, for connections to
                    MySQL databases, we need to load the class 'Driver' to the memory. Each database vendor has its
                    own and specific class to initialize the connection.
                 */
                Class.forName("com.mysql.cj.jdbc.Driver"); // The class to load in memory to start the connection
                connection = DriverManager.getConnection(serverURL, user, password);

            } catch (Exception DBConnectionException) {
                System.out.println("There was an exception while trying to connect to the database!: " +
                        DBConnectionException.getMessage());
            }

        return connection;
    }

    public static void main(String[] args) {
        Connection dbConn = MySQLConnection.getDBConnection();

        if(dbConn != null) {
            System.out.println("The database connection was established successfully!");
        } else {
            System.out.println("The database connection failed!");
        }
        System.out.println("Database connection object in memory: " + dbConn);
    }
}
