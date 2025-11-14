package cli.menu;

import static cli.auth.Authentication.*;
import static cli.auth.Authentication.validateBooleanPrompt;
import static cli.menu.MainMenu.*;
import static cli.menu.MainMenu.MENU_REGISTER_NOT_FOUND_ANNOUNCE;
import static cli.menu.MembershipMenu.displayMembershipsMenu;
import dao.CustomerDAO;
import domain.Customer;
import domain.MEMBERSHIP_TYPE;
import domain.TYPE_OF_CURRENCY;

import java.util.List;
import java.util.Scanner;

public class Menu {
    // CLI Warnings
    public final static String MENU_WARNING_RE_ENTER_FIRSTNAME = "Re-enter the firstname: ";
    public final static String MENU_WARNING_RE_ENTER_LASTNAME = "Re-enter the lastname: ";

    // CLI Errors
    public final static String ERROR_DISPLAY_MENU = "[ERROR] - The app failed to display the main menu. Please contact the system's " +
            "administrator";
    public final static String ERROR_INVALID_ID = "[ERROR] The entered ID is invalid. It must be greater than zero. Try again!";
    public final static String ERROR_INVALID_FULLNAME = "[ERROR] The firstname and/or the lastname entered are invalid. Please re-enter the information!";
    public final static String ERROR_INVALID_BOOLEAN_OPERATION = "[ERROR] Invalid operation option! Please select 'Yes(Y)' or 'No(N)'";
    public final static String ERROR_INVALID_MENU_OPTION = "[ERROR] The option entered does not exist or it is no longer valid. Please try again!";

    public static void displayCheckoutMenu(String firstName, String lastName, MEMBERSHIP_TYPE membershipType) {
        String checkoutMenu = """
                Person's Information:
                \t- Firstname: %s
                \t- Lastname: %s
                \t- Membership plan: %s
                \t- Price: %.2f %s
                """;

        // To simplify the CLI view, the MEMBERSHIP_TYPE is presented in a short format using the enum denomination
        System.out.printf(checkoutMenu, firstName, lastName, membershipType.name(), membershipType.getPrice(),
                TYPE_OF_CURRENCY.USD.name());
    }

    public static void displayGoodbyeMessage() {
        System.out.println("""
                        ***********************************************************
                        ** THANK YOU FOR USING THE WORKOUT-ZONE SYSTEM. GOODBYE! **
                        ***********************************************************
                        """);
    }

    public static boolean executeOption(Scanner input, CustomerDAO customerOperations) {
        boolean result = false;
        System.out.print(MENU_ENTER_AN_OPTION);
        int menuOption = Integer.parseInt(input.nextLine());

        int id;
        String firstName, lastName;
        MEMBERSHIP_TYPE membershipType;
        char proceed;
        switch(menuOption) {
            case 1:
                System.out.println("\nOption 1 - " + MENU_OPTION_SEARCH_ALL);
                System.out.println(MENU_DIVIDER_ROW);
                List<Customer> recoveredCustomers = customerOperations.searchAll();
                // Functional forEach and 'reference methods' using println()
                recoveredCustomers.forEach(System.out::println);
                // After each register listed from the database, we leave a blank space representing a line break
                System.out.println();

                break;
            case 2:
                System.out.println("\nOption 2 - " + MENU_OPTION_SEARCH_BY_ID);
                System.out.println(MENU_DIVIDER_ROW);
                System.out.print(MENU_SEARCH_BY_ID_PROMPT);
                id = Integer.parseInt(input.nextLine());

                validateID(input, id);

                Customer customerRetrieved = customerOperations.searchByID(new Customer(id));

                if(customerRetrieved != null) {
                    System.out.println(MENU_CUSTOMER_FOUND_ANNOUNCE);
                    System.out.println(MENU_RETRIEVING_INFORMATION_ANNOUNCE);
                    System.out.println(customerRetrieved);
                } else {
                    System.out.println(MENU_REGISTER_NOT_FOUND_ANNOUNCE);
                }

                break;
            case 3:
                System.out.println("\nOption 3 - " + MENU_OPTION_CREATE_REGISTER);
                System.out.println(MENU_DIVIDER_ROW);
                System.out.print(MENU_FIRSTNAME_PROMPT);
                firstName = input.nextLine();
                System.out.print(MENU_LASTNAME_PROMPT);
                lastName = input.nextLine();

                validateFullName(input, firstName, lastName);

                System.out.println(MENU_PLAN_CHOSEN_PROMPT);
                membershipType = displayMembershipsMenu(input);
                System.out.println(MENU_CONFIRM_INFORMATION_ANNOUNCE);
                System.out.println(MENU_DIVIDER_ROW);
                displayCheckoutMenu(firstName, lastName, membershipType);
                System.out.print(MENU_CONFIRM_CREATION_PROMPT);
                proceed = input.nextLine().toUpperCase().charAt(0);    // Transforming the char option to Uppercase

                validateBooleanPrompt(input, proceed);

                System.out.println(MENU_PERFORM_ACTION_ANNOUNCE);
                if(proceed == 'Y') {
                    /* [TODO] - Minor issue, while displaying a new register's information from the database on CLI.
                        After we persist the information on the database and we effectively created the new register,
                        when we want to display it to the console, after the feedback confirmation, we see the ID
                        attribute is always zero since we're not specifying a certain ID value, so it remains with
                        its default value which for integers types is zero.
                     */
                    Customer customerToCreate = new Customer(firstName, lastName, membershipType);
                    boolean resultOfCreation = customerOperations.createRegister(customerToCreate);
                    if (resultOfCreation) {
                        System.out.println(MENU_OPERATION_SUCCESS_ANNOUNCE);
                        System.out.println("The register was created on the database.");
                        System.out.println("Register created: \n" + customerToCreate);
                    } else {
                        System.out.println(MENU_OPERATION_FAILURE_ANNOUNCE);
                        System.out.println("The register was not created on the database. Please try again later!");
                    }
                } else if (proceed == 'N') {
                    System.out.println(MENU_OPERATION_CANCELLED);
                }

                break;
            case 4:
                System.out.println("\nOption 4 - " + MENU_OPTION_FULL_UPDATE);
                System.out.println(MENU_DIVIDER_ROW);
                System.out.print(MENU_OPTION_ID_PROMPT);
                id = Integer.parseInt(input.nextLine());

                validateID(input, id);

                System.out.print(MENU_FIRSTNAME_PROMPT);
                firstName = input.nextLine();
                System.out.print(MENU_LASTNAME_PROMPT);
                lastName = input.nextLine();

                validateFullName(input, firstName, lastName);

                System.out.println(MENU_PLAN_CHOSEN_PROMPT);
                membershipType = displayMembershipsMenu(input);
                System.out.println(MENU_CONFIRM_INFORMATION_ANNOUNCE);
                System.out.println(MENU_DIVIDER_ROW);
                displayCheckoutMenu(firstName, lastName, membershipType);
                System.out.print(MENU_CONFIRM_UPDATE_PROMPT);
                proceed = input.nextLine().toUpperCase().charAt(0);    // Transforming the char option to Uppercase

                validateBooleanPrompt(input, proceed);

                System.out.println(MENU_PERFORM_ACTION_ANNOUNCE);
                if(proceed == 'Y') {
                    Customer customerToUpdate = new Customer(id, firstName, lastName, membershipType);
                    boolean resultOfUpdate = customerOperations.updateFullRegister(customerToUpdate);
                    if (resultOfUpdate) {
                        System.out.println(MENU_OPERATION_SUCCESS_ANNOUNCE);
                        System.out.println("The register was updated on the database.");
                        System.out.println("Register information after the update: \n" + customerToUpdate);
                    } else {
                        System.out.println(MENU_OPERATION_FAILURE_ANNOUNCE);
                        System.out.println("The register was not updated on the database. Please try again later!");
                    }
                } else if (proceed == 'N') {
                    System.out.println(MENU_OPERATION_CANCELLED);
                }

                break;
            case 5:
                System.out.println("\nOption 5 - " + MENU_OPTION_PARTIAL_UPDATE);
                System.out.println(MENU_DIVIDER_ROW);
                // [TODO] Implement Partial register updates
                System.out.println(MENU_UNIMPLEMENTED_FUNCTION_ANNOUNCE);

                break;
            case 6:
                System.out.println("\nOption 6 - " + MENU_OPTION_DELETE_REGISTER);
                System.out.println(MENU_DIVIDER_ROW);
                System.out.print(MENU_DELETE_BY_ID_PROMPT);
                id = Integer.parseInt(input.nextLine());

                validateID(input, id);

                // Search the customer's register, before attempting its deletion
                Customer customerToDeleted = customerOperations.searchByID(new Customer(id));

                if(customerToDeleted != null) {
                    System.out.println(MENU_CUSTOMER_FOUND_ANNOUNCE);
                    System.out.println(MENU_CUSTOMER_DELETION_TEXT);
                    System.out.println(MENU_DIVIDER_ROW);
                    System.out.println(customerToDeleted);
                    System.out.print(MENU_CONFIRM_DELETION_PROMPT);
                    proceed = input.nextLine().toUpperCase().charAt(0);

                    validateBooleanPrompt(input, proceed);

                    if(proceed == 'Y') {
                        System.out.println(MENU_PERFORM_ACTION_ANNOUNCE);
                        boolean resultOfDeletion = customerOperations.deleteRegister(new Customer(id));
                        if(resultOfDeletion) {
                            System.out.println(MENU_OPERATION_SUCCESS_ANNOUNCE);
                        } else {
                            System.out.println(MENU_OPERATION_FAILURE_ANNOUNCE);
                        }
                    } else if (proceed == 'N') {
                        System.out.println(MENU_OPERATION_CANCELLED);
                    }

                } else {
                    System.out.println(MENU_REGISTER_NOT_FOUND_ANNOUNCE);
                }

                break;
            case 7:
                displayGoodbyeMessage();
                result = true;

                break;
            default:
                System.out.println(ERROR_INVALID_MENU_OPTION);
        }

        return result;
    }
}