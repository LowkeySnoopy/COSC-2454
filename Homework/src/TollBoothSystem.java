// Lanard Johnson
// Advanced Data Structures COSC-2454
// Dr. Zaki
// 4/9/2025
// Tollbooth Simulation

/*
This Java program simulates a toll collection system for the Department of Highways of Houston.
It includes a TollBooth class that calculates tolls based on truck information, tracks total receipts, and handles cash drawer collections.
A Truck class stores the truck's number of axles and total weight.
The main program provides a text-based interface that allows a toll operator to scan trucks, calculate tolls,
view totals since the last collection, and reset totals after cash is collected.
*/
// Interface for Truck
interface Truck {
    int getAxles();
    int getWeight();
}

// Interface for TollBooth
interface TollBooth {
    void calculateToll(Truck truck);
    void displayData();
    void collectReceipts();
}

// Concrete class for Truck
class Truck1 implements Truck {
    private int axles;
    private int weight;

    // Constructor
    public Truck1(int axles, int weight) {
        this.axles = axles;
        this.weight = weight;
    }

    public int getAxles() {
        return axles;
    }

    public int getWeight() {
        return weight;
    }
}

// Concrete class for TollBooth
class Toll implements TollBooth {
    private int truckCount = 0;
    private int totalReceipts = 0;

    // Method to calculate toll
    public void calculateToll(Truck truck) {
        int axles = truck.getAxles();
        int weight = truck.getWeight();
        int tollDue = 5 * axles + (weight / 1000) * 10; // $5 per axle + $10 per half-ton

        truckCount++;
        totalReceipts += tollDue;

        // Display truck info and toll due
        System.out.println("Truck arrival - ");
        System.out.println("Axles: " + axles);
        System.out.println("Total weight: " + weight);
        System.out.println("Calculated Toll due: $" + tollDue);
        System.out.println("--------------------------------------------------");
    }

    // Method to display total data
    public void displayData() {
        System.out.println("Totals since last collection:");
        System.out.println("Receipts: $" + totalReceipts);
        System.out.println("Trucks: " + truckCount);
        System.out.println("--------------------------------------------------");
    }

    // Method to collect receipts and reset totals
    public void collectReceipts() {
        System.out.println("*** Collecting receipts ***");
        System.out.println("Totals since last collection:");
        System.out.println("Receipts: $" + totalReceipts);
        System.out.println("Trucks: " + truckCount);

        // Reset totals for the next collection period
        totalReceipts = 0;
        truckCount = 0;
        System.out.println("--------------------------------------------------");
    }
}

// Main class to run the program
public class TollBoothSystem {
    public static void main(String[] args) {
        Toll tollBooth = new Toll();
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        // Displaying menu and allowing user to interact
        while (true) {
            System.out.println("Welcome to Houston Toll Road.");
            System.out.println("Make one of the following Selections:");
            System.out.println("1- Scan truck info and Display Toll");
            System.out.println("2- Calculate and Display Booths total");
            System.out.println("3- Remove cash drawer");
            System.out.println("4- Exit program");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    // Scan truck info and display toll
                    System.out.println("Enter the number of axles for the truck:");
                    int axles = scanner.nextInt();
                    System.out.println("Enter the weight of the truck (in kg):");
                    int weight = scanner.nextInt();

                    // Create a truck object with user input
                    Truck truck = new Truck1(axles, weight);

                    // Calculate and display toll for the truck
                    tollBooth.calculateToll(truck);
                    break;

                case 2:
                    // Display total receipts and trucks count
                    tollBooth.displayData();
                    break;

                case 3:
                    // Remove cash drawer (collect and reset)
                    tollBooth.collectReceipts();
                    break;

                case 4:
                    // Exit program
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
