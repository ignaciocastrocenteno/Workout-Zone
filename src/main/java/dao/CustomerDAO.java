package dao;

/*
    Here we're using an 'import static', which only allows imports that are related to static members from
    a given class. This makes possible the invocation of the method as it would a local static method to the current
    class, avoiding the usage of the fully qualified name for that class, keeping the code cleaner.
 */
import static db_connections.MySQLConnection.getDBConnection;
import domain.Customer;
import domain.MEMBERSHIP_TYPE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements IDAO<Customer>{

    @Override
    public boolean createRegister(Customer registerToAdd) {
        /*
            As we're creating a new register onto the database, we won't be working with a ResultSet object since
            it's only used while retrieving information from the database, but not for an insertion.
         */
        boolean result = false;
        PreparedStatement ps;
        Connection conn = getDBConnection();

        // As the ID column is usually defined as auto-increment field, we don't have to specify that column name in the query.
        String sql = "INSERT INTO customers(firstName, lastName, membershipType) "
                + "VALUES(?, ?, ?)";    // The values to be replaced instead of the question marks are known as 'Positional Values'.

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, registerToAdd.getFirstName());
            ps.setString(2, registerToAdd.getLastName());
            /*
                To perform the creation of the new register, we'll load the membershipType field not as an enum value
                but with its corresponding integer code.
             */
            ps.setInt(3, registerToAdd.getMembershipType().getProductCode());

            ps.execute();   // Here, we're using the method 'execute()' not 'executeQuery()', because we're not expecting a ResultSet value

            result = true;
        } catch (Exception createRegisterException) {
            System.err.println("[ERROR] Failed to create a new register within the database, using the specified " +
                    "parameters");
            System.err.println(createRegisterException.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception failedToCloseDBException) {
                System.err.println("[ERROR] Failed to close the database connection properly "
                        + failedToCloseDBException.getMessage());
            }
        }

        return result;
    }

    @Override
    public List<Customer> searchAll() {
        List<Customer> customers = new ArrayList<>();

        // We have to create different objects, before establishing any successful communication with the database
        PreparedStatement ps;   // This instruction prepares the SQL statement that's going to be executed on the database
        ResultSet rs;   // This instruction allows us to receive the result of the SQL query from the database
        Connection conn = getDBConnection();    // Invoking the previously created DB connection to the database engine
        String sql = "SELECT * FROM customers ORDER BY id";

        /*
            The process of connecting and querying a SQL database could always throw an exception.
            That's why we wrap the code in a try/catch block.
         */
        try {
            // We are expecting a 'PrepareStatement' object, as a result of passing in the SQL statement to the connection
            ps = conn.prepareStatement(sql);
            /*
                Once the SQL query is prepared to be executed, then we'll get the result of the operation, and it's going
                to be stored in the 'ResultSet' object.
             */
            rs = ps.executeQuery();

            // Based on the results of the executed query, we ask if there are results to iterate over
            while(rs.next()) { // The .next() method return a boolean, if and only if there are results to be read
                /*
                    For each result, we have to create an object to re-assemble the information we've just recovered
                    from the database. The object data type to be instantiated has to match a domain/entity class from
                    the Java project, with respect to its counterpart within the database (e.g. the domain class
                    'Customer' matches with counterpart: SQL table 'customers')
                 */
                var customer = new Customer();
                /*
                    While recovering each one of the columns from the database we access the 'ResultSet' object. Depending
                    on the data type corresponding to the SQL table's column we're referring to, we'll use different
                    methods to get those pieces of information from the database, to later pass them in the proper setters.
                 */
                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setLastName(rs.getString("lastName"));

                /*
                    Because the 'membershipType' attribute is being handled as an enum in Java, depending on the
                    integer we get from the database, we set an enum value in consequence using a switch statement.

                    If the 'membershipType' had been handled as an integer type, we could have set the integer
                    directly to setter method, once that piece of information is brought.
                 */
                MEMBERSHIP_TYPE membershipTypeRecovered = null;
                switch (rs.getInt("membershipType")) {
                    case 100 -> membershipTypeRecovered = MEMBERSHIP_TYPE.GOLD;
                    case 200 -> membershipTypeRecovered = MEMBERSHIP_TYPE.PLATINUM;
                    case 300 -> membershipTypeRecovered = MEMBERSHIP_TYPE.DIAMOND;
                    case 400 -> membershipTypeRecovered = MEMBERSHIP_TYPE.GLOBAL;
                }
                customer.setMembershipType(membershipTypeRecovered);

                customers.add(customer);
            }
        } catch (Exception searchAllException) {
            // System.err.println() best suits for exception messages shown through the terminal (red text for the output)
            System.err.println("[ERROR] There was an exception while trying to recover all the customer registers " +
                    "from the database");
            System.err.println(searchAllException.getMessage());
        } finally {
            /*
                Once we finish interacting with the database, it's understood as a good practice to close or
                finish both internal or external resources that won't be necessary any longer. One example of that is
                to close any database resource that's still available after the execution of the method.
             */
            try {
                /*
                    Closing any database connection could potentially throw an exception, that's why we use a try/catch
                    block within the 'finally' statement.
                 */
                conn.close();
            } catch (Exception failedToCloseDBException) {
                System.err.println("[ERROR] Failed to close the database connection properly "
                        + failedToCloseDBException.getMessage()); // Here we can even create custom exceptions if we need
            }
        }

        return customers;
    }

    @Override
    public boolean searchByID(Customer registerToSearch) {
        // First, we have to get the ID needed for the search (expected as an argument)
        int registerID = registerToSearch.getId();

        boolean result = false;
        // Again, we have to create the necessary objects to establish a successful connection to the database.
        PreparedStatement ps;
        ResultSet rs;
        Connection conn = getDBConnection();
        // In a SQL query, a question mark indicates that a parameter is pending to be pass in to complete the instruction.
        String sql = "SELECT * FROM customers WHERE id = ?";

        // Applying a try/catch block again, for any eventual exceptions that might be thrown during runtime.
        try {
            ps = conn.prepareStatement(sql);
            /*
                Before executing the SQL query that's already prepared, we have to add the missing parameters. In this
                case, the missing parameters is an integer type, so we need to use the .setInt() method.
             */
            /*
                The first parameter of the method refers to the index corresponding to the specific parameter, and
                the second parameter refers to the value that has to used for that missing parameter.
             */
            ps.setInt(1, registerID);
            rs = ps.executeQuery();

            /*
                As there should be only one register related to the specified ID, if exists, it's not necessary to use
                a while loop, instead we directly use an if statement.
             */
            if(rs.next()){  // Is there a register recovered from the DB (if any)?
                registerToSearch.setFirstName(rs.getString("firstName"));
                registerToSearch.setLastName(rs.getString("lastName"));

                // Newer and simplified switch statement syntax.
                switch (rs.getInt("membershipType")) {
                    case 100 -> registerToSearch.setMembershipType(MEMBERSHIP_TYPE.GOLD);
                    case 200 -> registerToSearch.setMembershipType(MEMBERSHIP_TYPE.PLATINUM);
                    case 300 -> registerToSearch.setMembershipType(MEMBERSHIP_TYPE.DIAMOND);
                    case 400 -> registerToSearch.setMembershipType(MEMBERSHIP_TYPE.GLOBAL);
                    /*
                        Just as an example, if there wouldn't be a defined MEMBERSHIP_TYPE attribute value, the value
                        to be stored it's going to be defaulted to 'null'. It would remain as if the user wouldn't have
                        an active membership status.
                     */
                    default -> registerToSearch.setMembershipType(null);
                }

                result = true;
            } else {
                System.out.println("There are no registers that matched the specified ID number!");
            }

        } catch (Exception searchByIDException) {
            System.err.println("[ERROR] An exception occurred while trying to search a customer by ID");
            System.err.println(searchByIDException.getMessage());
        }

        return result;
    }

    @Override
    public boolean updateFullRegister(Customer registerToUpdate) {
        boolean result = false;
        PreparedStatement ps;
        Connection conn = getDBConnection();
        // In this case, as we're updating all the information from a register on the database, we've to assure 'Positional Values' are completed
        String sql = "UPDATE customers SET firstName=?, lastName=?, membershipType=? WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, registerToUpdate.getFirstName());
            ps.setString(2, registerToUpdate.getLastName());
            ps.setInt(3, registerToUpdate.getMembershipType().getProductCode());
            ps.setInt(4, registerToUpdate.getId());

            ps.execute();
            result = true;
        } catch(Exception updateRegisterException) {
            System.err.println("[ERROR] Failed to update the specified register on the database!");
        } finally {
            try {
                conn.close();
            } catch (Exception failedToCloseDBException) {
                System.err.println("[ERROR] Failed to close the database connection properly "
                        + failedToCloseDBException.getMessage()); // Here we can even create custom exceptions if we need
            }
        }

        return result;
    }

    @Override
    public boolean updatePartialRegister(Customer registerToUpdate) {
        return false;
    }

    @Override
    public boolean deleteRegister(Customer registerToDelete) {
        boolean result = false;
        PreparedStatement ps;
        Connection conn = getDBConnection();
        String sql = "DELETE FROM customers WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, registerToDelete.getId());
            ps.execute();
            result = true;
        } catch (Exception deleteRegisterException) {
            System.err.println("[ERROR] The deletion operation failed!");
            System.err.println(deleteRegisterException.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception failedToCloseDBException) {
                System.err.println("[ERROR] Failed to close the database connection properly "
                        + failedToCloseDBException.getMessage()); // Here we can even create custom exceptions if we need
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // Testing the CRUD methods for the 'Customer' entity
            // We can either use the concrete class or its abstraction throw the interface (which is always the recommend approach)
            IDAO<Customer> customerCRUD = new CustomerDAO();  // Whether the 'CustomerDAO' class specifics change, we still respect the interface.

//            // LISTING ALL AVAILABLE REGISTERS
//            System.out.println("[TESTING] Listing all the customer registers from the MySQL database:\n");
//            var recoveredCustomers = customerCRUD.searchAll();
//            // Once we recovered all the customer registers, we iterate over the list.
//            recoveredCustomers.forEach(System.out::println);
//
//            // CREATING A NEW REGISTER
            System.out.println("[TESTING] Creating a new register with a 'DIAMOND' membership status on MySQL: ");
            Customer customerToCreate = new Customer(3, "Juan", "Pérez", MEMBERSHIP_TYPE.DIAMOND);
            var resultOp = customerCRUD.createRegister(customerToCreate);
            System.out.println("Creation completed:\s" + resultOp);
//
//            // LISTING ALL REGISTERS (AFTER THE NEW INSERTION)
//            System.out.println("[TESTING] Listing all the customer registers (after the new insertion): ");
//            recoveredCustomers = customerCRUD.searchAll();
//            recoveredCustomers.forEach(System.out::println);

            // SEARCHING A REGISTER BY ID
//              Customer customerToSearch = new Customer(1);
//            System.out.print("[TESTING] Searching an existing register by ID: " + customerCRUD.searchByID(customer2));

            // UPDATING AN EXISTING REGISTER
//            System.out.println("[TESTING] Updating an existing register by ID: " + customerCRUD.updateFullRegister(customerSample));

            // DELETE AN EXISTING REGISTER
//              Customer customerToDelete = new Customer(5);
//            System.out.println("[TESTING] Deleting an existing register by ID: " + customerCRUD.deleteRegister(customer2));
    }
}
