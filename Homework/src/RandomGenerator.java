// Lanard Johnson
// Advanced Data Structures COSC-2454
// Dr. Zaki
// 4/9/2025
// Loaded Dice Random Generator

/*
This Java program simulates rolling a 6-sided dice 100 times using a custom class, LoadedDice,
that extends the Random class. The LoadedDice class overrides the nextInt() method to return
the maximum possible value (num - 1) with a 50% probability, simulating a "loaded" dice.
The program demonstrates polymorphism by using LoadedDice in a method that accepts a Random object.
*/

import java.util.Random;

public class RandomGenerator {

    public static void printDiceRolls(Random randGenerator) {
        int counts[] = new int[6];

        for (int i = 0; i < 10000; i++) {
            int output = randGenerator.nextInt(6) + 1;
            counts[output - 1]++;
        }

        for (int i = 0; i < 6; i++) {
            System.out.printf("Percentage of %d is: %.2f%%\n", (i + 1), (counts[i] / 10000.0) * 100);
        }
    }

    public static void main(String[] args) {
        Random randGenerator = new Random();
        System.out.println("Regular Dice:");
        printDiceRolls(randGenerator);

        System.out.println("----------------------------------------------------------------------------------------------");

        LoadedDice myDice = new LoadedDice();
        System.out.println("Loaded Dice:");
        printDiceRolls(myDice);
    }
}
