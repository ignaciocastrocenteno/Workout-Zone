package cli.menu;

import domain.MEMBERSHIP_TYPE;
import domain.TYPE_OF_CURRENCY;

import java.util.Scanner;

public class MembershipMenu extends Menu {
    // Membership Menu Static Attributes
    public final static String MEMBERSHIP_MENU_ENTER_AN_OPTION = "\nPlease enter an option (1-4): ";

    public static MEMBERSHIP_TYPE displayMembershipsMenu(Scanner input) {
        MEMBERSHIP_TYPE selectedPlan = null;

        String membershipMenu = """
                \t1) %s
                \t\t * Price: %.2f %s (%s)
                \t\t * Location Restriction: %s
                
                \t2) %s
                \t\t * Price: %.2f %s (%s)
                \t\t * Location Restriction: %s
                
                \t3) %s
                \t\t * Price: %.2f %s (%s)
                \t\t * Location Restriction: %s
                
                \t4) %s
                \t\t* Price: %.2f %s (%s)
                \t\t* Location Restriction: %s
                """;

        System.out.printf(membershipMenu,
                // Information about the "Gold Pack"
                MEMBERSHIP_TYPE.GOLD.getName(), MEMBERSHIP_TYPE.GOLD.getPrice(), TYPE_OF_CURRENCY.USD.name(),
                MEMBERSHIP_TYPE.GOLD.getBillingFrequency(), MEMBERSHIP_TYPE.GOLD.getAccessLevel(),

                // Information about the "Platinum Pack"
                MEMBERSHIP_TYPE.PLATINUM.getName(), MEMBERSHIP_TYPE.PLATINUM.getPrice(), TYPE_OF_CURRENCY.USD.name(),
                MEMBERSHIP_TYPE.PLATINUM.getBillingFrequency(), MEMBERSHIP_TYPE.PLATINUM.getAccessLevel(),

                // Information about the "Diamond Pack"
                MEMBERSHIP_TYPE.DIAMOND.getName(), MEMBERSHIP_TYPE.DIAMOND.getPrice(), TYPE_OF_CURRENCY.USD.name(),
                MEMBERSHIP_TYPE.DIAMOND.getBillingFrequency(), MEMBERSHIP_TYPE.DIAMOND.getAccessLevel(),

                // Information about the "Global Pack"
                MEMBERSHIP_TYPE.GLOBAL.getName(), MEMBERSHIP_TYPE.GLOBAL.getPrice(), TYPE_OF_CURRENCY.USD.name(),
                MEMBERSHIP_TYPE.GLOBAL.getBillingFrequency(), MEMBERSHIP_TYPE.GLOBAL.getAccessLevel()
        );

        // Based on the membership plan selected by the user, we set and return the specified MEMBERSHIP_TYPE
        // Otherwise, we continue iterating over until we receive a valid option
        System.out.print(MEMBERSHIP_MENU_ENTER_AN_OPTION);
        int membershipPlan = Integer.parseInt(input.nextLine());
        do {
            switch (membershipPlan) {
                case 1 -> selectedPlan = MEMBERSHIP_TYPE.GOLD;
                case 2 -> selectedPlan = MEMBERSHIP_TYPE.PLATINUM;
                case 3 -> selectedPlan = MEMBERSHIP_TYPE.DIAMOND;
                case 4 -> selectedPlan = MEMBERSHIP_TYPE.GLOBAL;
                default -> System.out.println("The membership plan does not exist. Please try again!");
            }
        } while(membershipPlan < 1 || membershipPlan > 4);

        System.out.println(selectedPlan);

        return selectedPlan;
    }
}
