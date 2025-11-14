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
            // The name of the database we are going to use (have to be previously created)
            String dbName = "workout_zone_db";

            /*
               Other relevant information within the 'Session' tab, that could be useful:
                - Name: Name of the server instance, that's serving the database/s available. If
                        unchanged, the server's default name will be shown in this field.
                - Host: The name of the computer we're connecting to, which can be a personal computer or a server.
                        The host can be referenced with a name or with an IP address (e.g. the IP address for the
                        localhost is 127.0.0.1 and would be the same to say 'localhost' than this IP address. If
                        we need to connect to a web server hosted on the internet, we'll require its IP address or
                        a DNS).
                - Port: The specific port where the server is exposing the database/s to work with. It functions as
                        external gateway to connect to the server and then accessing the visible databases. If there're
                        other databases that aren't to be visible, the database engine has to be configured for that
                        purpose. Otherwise, whether we try to connect to a valid and existing DB that would be possible.
                - Login user: The user that's currently logged in working on the schemas, or any other user with valid
                              credentials. The privileges for each user are going to be different, so they could access
                              different levels of depth or content that are available inside the database but aren't
                              visible for all users (authentication/authorization concept & user roles)
                 */
            String serverURL = "jdbc:mysql://localhost:3306/" + dbName;
            String user = "root";
            String password = "admin";

            // The connection process to the database could potentially throw exceptions, so we wrap the code into a try/catch block.
            try {
                /*
                    To start the connection process to the database through the server, we have to load in memory
                    the corresponding MySQL dependency class to begin the execution. In this case, for connections to
                    MySQL databases, we need to load the class 'Driver' to the memory. Each database vendor has its
                    own and specific class to initialize the database connection. The class is always included within
                    the dependency we had previously defined onto the 'pom.xml' file.
                 */
                // The static method 'Class.forName()' allow us to load a specific class on memory
                Class.forName("com.mysql.cj.jdbc.Driver"); // We load in memory the class that allows the DB connection
                connection = DriverManager.getConnection(serverURL, user, password); // The DB connection intent itself

            } catch (Exception DBConnectionException) {
                System.out.println("There was an exception while trying to connect to the database!: " +
                        DBConnectionException.getMessage());
            }

        return connection;
    }

    public static void testingDB(String[] args) {
        Connection dbConn = MySQLConnection.getDBConnection();

        if(dbConn != null) {
            System.out.println("The database connection was established successfully!");
        } else {
            System.out.println("The database connection failed!");
        }
        System.out.println("Database connection object in memory: " + dbConn);
    }
}
