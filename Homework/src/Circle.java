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

public class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        if (radius < 0) {
            return Double.NaN;
        }
        return Math.PI * radius * radius;
    }
}
