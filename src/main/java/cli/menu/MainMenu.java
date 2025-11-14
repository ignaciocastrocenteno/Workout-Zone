package cli.menu;

public class MainMenu extends Menu {
    // Main Menu Static Attributes
        // Menu Option Titles & Divider Line
        public final static String MENU_OPTION_SEARCH_ALL = "List all gym members";
        public final static String MENU_OPTION_SEARCH_BY_ID = "Search gym member by ID";
        public final static String MENU_OPTION_CREATE_REGISTER= "Create a new gym member";
        public final static String MENU_OPTION_FULL_UPDATE = "Update an existing gym member by ID (Full Update)";
        public final static String MENU_OPTION_ID_PROMPT = "Please enter the register ID to be updated: ";
        public final static String MENU_OPTION_PARTIAL_UPDATE = "Update an existing gym member by ID (Partial Update)";
        public final static String MENU_OPTION_DELETE_REGISTER = "Delete a gym member";
        public final static String MENU_OPTION_EXIT_SYSTEM = "Exit";
        public final static String MENU_ENTER_AN_OPTION = "\nEnter an option (1-7): ";
        public final static String MENU_DIVIDER_ROW = "----------------------------------------------------------------";

        // Functionality Text Prompts
        public final static String MENU_SEARCH_BY_ID_PROMPT = "Please enter the ID to find: ";
        public final static String MENU_DELETE_BY_ID_PROMPT = "Please enter the ID to be deleted: ";
        public final static String MENU_CUSTOMER_DELETION_TEXT = "The following register is about to be deleted:";
        public final static String MENU_FIRSTNAME_PROMPT = "Please enter the firstname of the person: ";
        public final static String MENU_LASTNAME_PROMPT = "Please enter the lastname of the person: ";
        public final static String MENU_PLAN_CHOSEN_PROMPT = "Please enter the membership plan chosen:";
        public final static String MENU_CONFIRM_INFORMATION_ANNOUNCE = "\nPlease confirm all the information provided, before proceeding:";
        public final static String MENU_CONFIRM_CREATION_PROMPT = "\nProceed with the register creation? (Y/N): ";
        public final static String MENU_CONFIRM_UPDATE_PROMPT = "\nProceed with of register update? (Y/N): ";
        public final static String MENU_CONFIRM_DELETION_PROMPT = "\nProceed with of register deletion? (Y/N): ";
        public final static String MENU_CUSTOMER_FOUND_ANNOUNCE = "\nCustomer found!";
        public final static String MENU_PERFORM_ACTION_ANNOUNCE = "Performing requested action... please wait";
        public final static String MENU_RETRIEVING_INFORMATION_ANNOUNCE = "Retrieving information from the database...";
        public final static String MENU_REGISTER_NOT_FOUND_ANNOUNCE = "The register ID was not found in the database! The ID does not exist " +
                "or was safely deleted";
        public final static String MENU_OPERATION_SUCCESS_ANNOUNCE = "\nThe operation was successful!";
        public final static String MENU_OPERATION_FAILURE_ANNOUNCE = "The operation failed!";
        public final static String MENU_OPERATION_CANCELLED = "The operation was cancelled! All the related data " +
                "was erased correctly!\n";
        public final static String MENU_UNIMPLEMENTED_FUNCTION_ANNOUNCE = """
                        Unimplemented Function!
                        Coming Soon...
                        """;

        public static void displayMainMenu(String softwareVersion) {
            String menu = """
                    WELCOME TO THE WORKOUT-ZONE SYSTEM - REV %s
                    %s
                    1) %s
                    2) %s
                    3) %s
                    4) %s
                    5) %s
                    6) %s
                    7) %s
                    """;

            System.out.printf(menu, softwareVersion, MENU_DIVIDER_ROW, MENU_OPTION_SEARCH_ALL, MENU_OPTION_SEARCH_BY_ID,
                    MENU_OPTION_CREATE_REGISTER, MENU_OPTION_FULL_UPDATE, MENU_OPTION_PARTIAL_UPDATE,
                    MENU_OPTION_DELETE_REGISTER, MENU_OPTION_EXIT_SYSTEM);
        }
}
