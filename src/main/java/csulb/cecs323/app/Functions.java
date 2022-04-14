
package csulb.cecs323.app;

import java.util.Scanner;

/**
 * @author Daniel Tha, John Teano, Steven Dao
 * @version 1.0
 *
 * Performs various functions which are repeatable.
 */
public class Functions {
    /**
     * Prints the main menu options and returns the user's validated input.
     * @return  The validated integer indicating the user's choice.
     */
    public static int checkMainMenuInput() {
        System.out.print(
                "\n----------------------------------------" +
                "\n        Welcome to the Database!" +
                "\n----------------------------------------" +
                "\nPlease select an option:" +
                "\n\t1) Add a new object" +
                "\n\t2) List information about an object" +
                "\n\t3) Delete a book"  +
                "\n\t4) Update a book" +
                "\n\t5) List the primary keys" +
                "\n\t0) Quit the program" +
                "\n >> ");

        return checkIntRange(0, 5);
    } // End of checkMainMenuInput method

    /**
     * Prints the 'add object' menu options and returns the user's validated input.
     * @return  The validated integer indicating the user's choice.
     */
    public static int checkAddObjectInput() {
        System.out.print(
                "\nPlease select an option:" +
                "\n\t1) Add a new publisher" +
                "\n\t2) Add a new book" +
                "\n\t3) Add a new writing group" +
                "\n\t4) Add a new individual author" +
                "\n\t5) Add a new ad hoc team" +
                "\n\t6) Assign an individual author to an ad hoc team" +
                "\n\t0) Return to main menu" +
                "\n >> ");

        return checkIntRange(0, 6);
    } // End of checkAddObjectInput method

    /**
     * Prints the 'list information' menu options and returns the user's validated input.
     * @return  The validated integer indicating the user's choice.
     */
    public static int checkListInformationInput() {
        System.out.print(
                "\nPlease select an option:" +
                "\n\t1) List info about a publisher" +
                "\n\t2) List info about a book" +
                "\n\t3) List info about a writing group" +
                "\n\t0) Return to main menu" +
                "\n >> ");

        return checkIntRange(0, 3);
    } // End of checkListInformationInput method

    /**
     * Prints the 'print primary keys' menu options and returns the user's validated input.
     * @return  The validated integer indicating the user's choice.
     */
    public static int checkPrimaryKeysInput(){
        System.out.print(
                "List the primary keys of:" +
                "\n\t1) Publishers" +
                "\n\t2) Books" +
                "\n\t3) Authoring Entities" +
                "\n\t0) Return to main menu" +
                "\n >> " );

        return checkIntRange(0, 3);
    } // End of checkPrimaryKeyInput method

    /**
     * Checks if the inputted value is an integer within the specified range (ex: 1-10)
     * @param low  lower bound of the range.
     * @param high upper bound of the range.
     * @return the valid input.
     */
    public static int checkIntRange(int low, int high) {
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        int inputToInt = 0;

        while (!valid) {
            String input = in.nextLine();

            try {
                // if the input is not an int, it will trigger a NumberFormatException
                inputToInt = Integer.parseInt(input);

                if (inputToInt <= high && inputToInt >= low) {
                    valid = true;
                } else {
                    System.out.print("Input does not fall within the range; please try again: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input; please try again: ");
            }
        }
        return inputToInt;
    } // End of checkIntRange method
} // End of Functions class
