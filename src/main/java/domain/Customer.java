package domain;

import java.util.Objects;

public class Customer {
    // All the attributes defined in any domain class, have to match the columns of the corresponding SQL related table
    private int id;
    private String firstName;
    private String lastName;
    private MEMBERSHIP_TYPE membershipType;

    // Depending on the operation we need to perform onto the database, we'll need one constructor or the others.
    public Customer() {

    }

    // Constructor to search by ID or to delete a customer from the database - we only need to know the ID to concrete these operations
    public Customer(int id) {
        setId(id);
    }

    // Constructor to create a new customer in the database - we'll need all the arguments in order to create a new register.
    public Customer(String firstName, String lastName, MEMBERSHIP_TYPE membershipType) {
        setFirstName(firstName);
        setLastName(lastName);
        setMembershipType(membershipType);
    }

    /*
        Constructor to search all the customer registers from the database or to perform a full update - here
        we reference the 3 params constructor, but now additionally considering the ID coming from the DB. We set it up,
        right after the first constructor call.
     */
    public Customer(int id, String firstName, String lastName, MEMBERSHIP_TYPE membershipType) {
        // Keeping the code dry, using the already existing constructors as the top declaration
        this(firstName, lastName, membershipType);
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id <= 0){
            throw new IllegalArgumentException("The ID cannot be a negative number or zero!");
        }
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName.isBlank()) {
            throw new IllegalArgumentException("The firstname field is invalid!");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName.isBlank()) {
            throw new IllegalArgumentException("The lastname field is invalid!");
        }
        this.lastName = lastName;
    }

    public MEMBERSHIP_TYPE getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MEMBERSHIP_TYPE membershipType) {
        // The 'membershipType' field can be null, meaning the customer doesn't count with an active membership status.
    //        if(membershipType == null) {
    //            throw new IllegalArgumentException("The membership type cannot be null!");
    //        }
        this.membershipType = membershipType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", membershipType=").append(membershipType);
        sb.append('}');
        return sb.toString();
    }

    /*
         As the instances of this class are expected to be stored within ordered data structures once recovered, it's a
         good practice to override the methods equals() and hashCode() to speed up the insertion, searching and
         sorting of this object types. These advantages come automatically while overriding the methods, we don't have
         to do something more.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;

        return id == customer.id && Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) && membershipType == customer.membershipType;
    }

    /*
        Recapping how does the hashCode() method work: This method generates an integer value that's going to be unique
        during runtime and cannot be repeated (and that's going to operate like a hash). This integer number will be
        assigned individually to each instance of a given class (in this case, a hashCode is going to be set for every
        'Customer' object that's going to be created during the execution). This hashCode is the responsible to speed
        the insertion, searching and sorting of the objects within ordered data-structures, because we can use this
        hash to identify univocally each of those objects, instead of considering the memory address as the first option.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, membershipType);
    }

    /*
        CONCLUSION: It's not necessary at all, to create the domain class that we need to operate inside the project,
        because we can perfectly bring the information the database directly onto the app.
        However, it's understood as good practice to represent all the existing SQL tables within the project, just
        as a way to check that everything stored in the database is usable information from the Java application.

        Each SQL table should be represented as a different class in Java, being each column a specific attribute.
        This is what the ORMs/ODMs do to map the SQL tables to entities of the programming language.
     */
}
