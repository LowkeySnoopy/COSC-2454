// Lanard Johnson
// Advanced Data Structures COSC-2454
// Dr. Zaki
// 4/9/2025
// Shape Interface – Part A

/*
This Java program demonstrates the use of interfaces in object-oriented programming.
An interface named Shape is defined with a method area(). Two classes, Circle and Rectangle,
implement this interface and provide their own logic to calculate area.
The program tests polymorphism by passing Circle and Rectangle objects to a method
that prints their respective areas using the Shape interface.
*/

public class Rectangle implements Shape {
    private double height;
    private double width;

    public Rectangle(double height, double width) {
        if (height < 0 || width < 0) {
            // Handle negative values by setting them to NaN or throwing an exception
            this.height = Double.NaN;
            this.width = Double.NaN;
        } else {
            this.height = height;
            this.width = width;
        }
    }

    @Override
    public double area() {
        if (Double.isNaN(this.height) || Double.isNaN(this.width)) {
            return Double.NaN;
        }
        return this.height * this.width;
    }
}
