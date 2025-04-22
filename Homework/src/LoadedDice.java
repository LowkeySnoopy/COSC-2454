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

public class LoadedDice extends Random {

    public LoadedDice() {
        super(); // Call Random's constructor
    }

    @Override
    public int nextInt(int num) {
        if (super.nextBoolean()) {
            return num - 1; // Return highest possible number 50% of the time
        } else {
            return super.nextInt(num); // Return a random number normally
        }
    }
}
