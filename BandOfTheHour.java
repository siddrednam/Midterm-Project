package MidtermProject;

import java.util.Scanner;

public class BandOfTheHour {
    /**
     * Global Variable Declarations
     */
    private static final int MAX_ROW = 10;
    private static final int MAX_POSITIONS = 8;
    private double[][] positions;
    private int[] positionsPerRow;
    private Scanner keyboard;

    public static void main(String[] args) {

        // Welcome Screen
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Band of the Hour");
        System.out.println("-------------------------------");
        System.out.print("Please enter number of rows    : ");
        int numRows = input.nextInt();

        // User Input Verification & Error Throw
        while (numRows <= 0 || numRows > MAX_ROW) {
            System.out.print("ERROR: Out of range, try again : ");
            numRows = input.nextInt();
        }

        BandOfTheHour organizer = new BandOfTheHour(numRows);
        organizer.perform();

    } // end of the main class

    public BandOfTheHour(int numberOfRows) {
        positions = new double[MAX_ROW][MAX_POSITIONS];
        positionsPerRow = new int[MAX_ROW];
        keyboard = new Scanner(System.in);

        for (int i = 0; i < numberOfRows; i++) {

            System.out.printf("Please enter number of positions in row %c: ", 'A' + i);
            int numPositions = keyboard.nextInt();

            while (numPositions <= 0 || numPositions > MAX_POSITIONS) {

                System.out.println("ERROR: Out of range, try again.");
                numPositions = keyboard.nextInt();

            } // end of the while-loop

            positionsPerRow[i] = numPositions;

        } // end of the for-loop
    }

    /**
     * perform: This method focuses on intaking, storing, & verifying user input,
     * as well as verifying the user input.
     */
    public void perform() {
        // Local variable declaration: String
        String choice;

        /**
         * do-while loop intakes, performs, and verifies menu selection
         */
        do {
            System.out.print("(A)dd, (R)emove, (P)rint, e(X)it:");
            System.out.println(" ");
            choice = keyboard.next();

            // switch-case statement runs corresponding performance to menu selection
            switch (choice.toUpperCase()) {
                case "A":
                    addMusician();
                    break;
                case "R":
                    removeMusician();
                    break;
                case "P":
                    printPositioning();
                    break;
                case "X":
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("ERROR: Invalid option, try again.");
            } // end of the switch-case statement

        } while (!choice.equalsIgnoreCase("X"));
        // end of do-while loop

    } // end of the perform method

    /**
     * addMusician: This method adds a musician to the given position & verifies vacancy
     */
    private void addMusician() {

        int rowIndex = -1;

        while (rowIndex < 0 || rowIndex >= MAX_ROW) {
            System.out.println("Please enter row letter:");
            char row = keyboard.next().toUpperCase().charAt(0);
            rowIndex = row - 'A';
            if (rowIndex >= 0 && rowIndex < MAX_ROW && positionsPerRow[rowIndex] != 0) {
                break;
            }
            System.out.println("ERROR: Out of range, try again.");
        } // end of the while-loop

        System.out.println("Please enter position number (1 to " + positionsPerRow[rowIndex] + "):");
        int position = keyboard.nextInt() - 1;
        while (position < 0 || position >= positionsPerRow[rowIndex]) {
            System.out.println("ERROR: Out of range, try again.");
            position = keyboard.nextInt() - 1;
        } // end of the while-loop

        // if-statement throws an error if position is occupied
        if (positions[rowIndex][position] > 0) {
            System.out.println("ERROR: There is already a musician there.");
            return;
        } // end of the if-statement

        // code cluster intakes, stores, and verifies weight of musician
        System.out.println("Please enter weight (45.0 to 200.0):");
        double weight = keyboard.nextDouble();
        while (weight < 45.0 || weight > 200.0) {
            System.out.println("ERROR: Out of range, try again.");
            weight = keyboard.nextDouble();
        } // end of the while-loop

        // calculates total weight for the row and check if it exceeds 100 kg per position
        double totalWeightForRow = totalWeightPerRow(rowIndex);
        if (totalWeightForRow + weight > positionsPerRow[rowIndex] * 100) {
            System.out.println("ERROR: That would exceed the average weight limit.");
            return;
        } // end of the if-statement

        positions[rowIndex][position] = weight;
        System.out.println("****** Musician added.");

    } // end of the addMusician method

    /**
     * totalWeightPerRow: This method returns total weight per row
     * @param rowIndex
     * @return
     */
    private double totalWeightPerRow(int rowIndex) {

        double totalWeight = 0;
        for (int i = 0; i < positionsPerRow[rowIndex]; i++) {
            totalWeight += positions[rowIndex][i];
        }
        return totalWeight;

    } // end of the totalWeightPerRow method

    /**
     * removeMusician: removes musician from inserted positioning
     */
    private void removeMusician() {
        System.out.println("Please enter row letter:");
        char row = keyboard.next().toUpperCase().charAt(0);
        int rowIndex = row - 'A';

        // verification clause
        if (rowIndex < 0 || rowIndex >= MAX_ROW || positionsPerRow[rowIndex] == 0) {
            System.out.println("ERROR: Out of range, try again.");
            return;
        }

        System.out.println("Please enter position number (1 to " + positionsPerRow[rowIndex] + "):");
        int position = keyboard.nextInt() - 1;
        // verification clause
        if (position < 0 || position >= positionsPerRow[rowIndex] || positions[rowIndex][position] == 0) {
            System.out.println("ERROR: That position is vacant.");
            return;
        }

        positions[rowIndex][position] = 0.0;
        System.out.println("****** Musician removed.");

    } // end of the removeMusician method

    /**
     * printAssignments: This method outputs the position assignments/weight
      */
    private void printPositioning() {

        for (int i = 0; i < positions.length; i++) {
            if (positionsPerRow[i] == 0)
                continue;

            double totalWeight = 0;
            System.out.print((char) ('A' + i) + ": ");

            for (int a = 0; a < positionsPerRow[i]; a++) {
                System.out.printf("%6.1f ", positions[i][a]);
                totalWeight += positions[i][a];
            } // end of the interior for-loop

            double avgWeight = totalWeight / positionsPerRow[i];
            System.out.printf(" [%6.1f, %6.1f]%n", totalWeight, avgWeight);
        } // end of the anterior for-loop

    } // end of the printAssignments method

} // end of the BandOfTheHour class


