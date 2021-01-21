package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        cinema();
    }
    public static int[] getCinema() {
        Scanner scanner = new Scanner(System.in);
        int[] input = new int[2];
        System.out.println("Enter the number of rows:");
        input[0] = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        input[1] = scanner.nextInt();
        return input;
    }
    public static int getChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("4. Exit");
        System.out.println();
        return scanner.nextInt();
    }
    public static int[] getSecondUserInput() {
        Scanner scanner = new Scanner(System.in);
        int[] secondInput = new int[2];
        System.out.println("Enter a row number:");
        secondInput[0] = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        secondInput[1] = scanner.nextInt();
        return secondInput;
    }
    public static boolean checkSecondUserInput(int[] cinema, char[][] matrix, int[] secondUserInput) {
        if (secondUserInput[0] > cinema[0] || secondUserInput[1] > cinema[1]) {
            System.out.println("Wrong input!");
            System.out.println();
            return false;
        } else if (matrix[secondUserInput[0] - 1][secondUserInput[1] - 1] == 'B') {
            System.out.println("That ticket has already been purchased!");
            System.out.println();
            return false;
        } else {
            return true;
        }
    }
    public static char[][] makeCinemaMatrix(int[] input) {
        char[][] matrix = new char[input[0]][input[1]];
        for (char[] chars : matrix) {
            Arrays.fill(chars, 'S');
        }
        return matrix;
    }
    public static void printMatrix(char[][] inputMatrix, int[] input) {
        int[] rows = new int[input[0]];
        int[] seats = new int[input[1]];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = i + 1;
        }
        for (int i = 0; i < seats.length; i++) {
            seats[i] = i + 1;
        }
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int seat : seats) {
            System.out.print(seat + " ");
        }
        System.out.println();
        for (int i = 0; i < rows.length; i++) {
            System.out.print(rows[i] + " ");
            for (int j = 0; j < inputMatrix[i].length; j++) {
                System.out.print(inputMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static int getPrice(int[] cinema, int[] secondUserInput) {
        int rows = cinema[0];
        int seats = cinema[1];
        int sumSeats = rows * seats;
        if (sumSeats <= 60) {
            return 10;
        } else {
            int frontRows = rows / 2;
            if (secondUserInput[0] <= frontRows) {
                return 10;
            } else {
                return 8;
            }
        }
    }
    public static void setUpdatedMatrix(char[][] inputMatrix, int[] secondUserInput) {
        inputMatrix[secondUserInput[0] - 1][secondUserInput[1] - 1] = 'B';
    }

    public static void cinema() {
        int[] cinema = getCinema();
        char[][] matrix = makeCinemaMatrix(cinema);
        int choice = getChoice();
        while (choice != 4) {
            if (choice == 1) {
                printMatrix(matrix, cinema);
                choice = getChoice();
            } else if (choice == 2) {
                boolean correctInput;
                int[] inputBooking;
                do {
                    inputBooking = getSecondUserInput();
                    correctInput = checkSecondUserInput(cinema, matrix, inputBooking);
                } while (!correctInput);
                setUpdatedMatrix(matrix, inputBooking);
                int price = getPrice(cinema, inputBooking);
                System.out.println();
                System.out.printf("Ticket price: $%d%n",price);
                choice = getChoice();
            } else if (choice == 3) {
                getStatistics(matrix, cinema);
                choice = getChoice();
            } else {
                return;
            }
        }
    }
    public static void getStatistics(char[][] matrix, int[] cinema) {
        int soldSeats = 0;
        double totalSeats = cinema[0] * cinema[1];
        int currentIncome = 0;
        int totalIncome = 0;
        double percentage;
        for (int i = 0; i < cinema[0]; i++) {
            int[] tmp = new int[1];
            int[] tmp2 = new int[1];
            for (int j = 0; j < cinema[1]; j++) {
                if (matrix[i][j] == 'B') {
                    tmp[0] = i + 1;
                    currentIncome += getPrice(cinema, tmp);
                    soldSeats++;
                }
                tmp2[0] = i + 1;
                totalIncome += getPrice(cinema, tmp2);
            }
        }
        percentage = (soldSeats / totalSeats) * 100;
        System.out.println();
        System.out.printf("Number of purchased tickets: %d%n", soldSeats);
        System.out.printf("Percentage: %.2f%%%n", percentage);
        System.out.printf("Current income: $%d%n", currentIncome);
        System.out.printf("Total income: $%d%n",totalIncome);
    }
}
