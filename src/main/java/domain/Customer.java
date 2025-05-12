package domain;

import java.util.Objects;

public class Customer {
    // All the attributes defined in any domain class, have to match the columns of the corresponding SQL table
    private int id;
    private String firstName;
    private String lastName;
    private MEMBERSHIP_TYPE membershipType;

    // Depending on the operation we need to perform onto the database, we'll need one constructor or the others.
    public Customer() {

    }

    // Deleting a customer from the database - we only need to know the ID to concrete this operation
    public Customer(int id) {
        setId(id);
    }

    // Creating a new customer in the database - we'll need all the fields completed in order to create a new register.
    public Customer(String firstName, String lastName, MEMBERSHIP_TYPE membershipType) {
        setFirstName(firstName);
        setLastName(lastName);
        setMembershipType(membershipType);
    }

    // Recovering all the existing customer registers in the database
    public Customer(int id, String firstName, String lastName, MEMBERSHIP_TYPE membershipType) {
        // Keeping the code dry, using the already existing constructors as the top declaration
        this(firstName, lastName, membershipType);
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName.isBlank()) {
            throw new IllegalArgumentException("The first name field is invalid!");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName.isBlank()) {
            throw new IllegalArgumentException("The last name field is invalid!");
        }
        this.lastName = lastName;
    }

    public MEMBERSHIP_TYPE getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MEMBERSHIP_TYPE membershipType) {
        if(membershipType == null) {
            throw new IllegalArgumentException("The membership type cannot be null!");
        }
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
         As the instances of this class are expected to be stored within ordered data structures, it's a good practice
         to override the methods equals() and hashCode(), to speed up the searching and sorting of this object types.
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
        Recapping how does the hashCode() method work: This method generates an integer value that's unique and that's
        going to be assigned individually to each instance of a given class (in this case, a hashCode is going to be
        set for every 'Customer' object that's going to be created during the execution). This hashCode is the
        responsible to speed the searching and sorting of the objects within ordered data-structures. Just by overriding
        these methods, we automatically take the advantage of this ease, without having to do something more.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, membershipType);
    }
}
