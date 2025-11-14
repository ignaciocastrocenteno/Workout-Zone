package cli;

import static cli.menu.Menu.*;
import static cli.menu.MainMenu.*;
import dao.CustomerDAO;

import java.util.Scanner;

public class WorkoutZone {
    // Software Version
    private final static String SOFTWARE_VERSION = "1.0";

    // System Variables
    private static boolean END_OF_DATA = false;

    public static void main(String[] args) {
        app();
    }

    private static void app() {
        Scanner input = new Scanner(System.in);
        CustomerDAO customerOperations = new CustomerDAO();

        while(!WorkoutZone.END_OF_DATA){
            try {
                displayMainMenu(SOFTWARE_VERSION);
                END_OF_DATA = executeOption(input, customerOperations);
            } catch (Exception menuOptionsException) {
                System.err.println(ERROR_DISPLAY_MENU);
                System.err.println(menuOptionsException.getMessage());
            }
            // After each requested operation, we leave a blank space representing a line break
            System.out.println();
        }
        input.close();
    }
}
