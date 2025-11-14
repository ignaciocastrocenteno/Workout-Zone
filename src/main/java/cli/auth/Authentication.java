package cli.auth;

import static cli.menu.Menu.*;

import java.util.Scanner;

public class Authentication {
    public static void validateID(Scanner input, int id) {
        while(id <= 0) {
            System.out.println(ERROR_INVALID_ID);
            id = Integer.parseInt(input.nextLine());
        }
    }

    public static void validateFullName(Scanner input, String firstName, String lastName) {
        while(firstName.isBlank() || lastName.isBlank()) {
            System.out.println(ERROR_INVALID_FULLNAME);
            System.out.print(MENU_WARNING_RE_ENTER_FIRSTNAME);
            firstName = input.nextLine();
            System.out.print(MENU_WARNING_RE_ENTER_LASTNAME);
            lastName = input.nextLine();
        }
    }

    public static void validateBooleanPrompt(Scanner input, char proceed) {
        while (proceed != 'Y' && proceed != 'N') {
            System.out.println(ERROR_INVALID_BOOLEAN_OPERATION);
            proceed = input.nextLine().toUpperCase().charAt(0);
        }
    }
}
