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

import java.text.DecimalFormat;

public class ShowAreaShape {
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat(".00");

        Shape[] shapes = {
                new Circle(5.5), new Circle(6.5), new Circle(7.5),
                new Rectangle(3, 4), new Rectangle(5, 6), new Rectangle(4, 5),
        };

        for (Shape shape : shapes) {
            if (shape instanceof Circle) {
                System.out.println("Area of Circle: " + df.format(shape.area()));
            } else if (shape instanceof Rectangle) {
                System.out.println("Area of Rectangle: " + df.format(shape.area()));
            }
        }
    }
}


