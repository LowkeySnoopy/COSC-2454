// Lanard Johnson
// Advanced Data Structures COSC-2454
// Dr. Zaki
// 4/9/2025
// Shape Interface – Part B (JUnit Tests)

/*
This Java file includes JUnit test cases for the Shape interface implementation.
It verifies the correctness of the area() method in both the Circle and Rectangle classes.
Test cases include valid input, as well as edge cases like zero, negative values,
and nulls to ensure robustness and exception handling in area calculation logic.
*/

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ShapeTestJUnit {

    // Circle Tests
    @Test
    public void testCircleAreaPositive() {
        Shape circle = new Circle(5);
        assertEquals(78.53981633974483, circle.area(), "Area of Circle with radius 5 should be ~78.54");
    }

    @Test
    public void testCircleAreaZero() {
        Shape circle = new Circle(0);
        assertEquals(0, circle.area(), "Area of Circle with radius 0 should be 0");
    }

    @Test
    public void testCircleAreaNegative() {
        Shape circle = new Circle(-5);
        assertTrue(Double.isNaN(circle.area()), "Area should be NaN for negative radius");
    }


    // Rectangle Tests
    @Test
    public void testRectangleAreaPositive() {
        Shape rectangle = new Rectangle(5, 3);
        assertEquals(15, rectangle.area(), "Area of Rectangle with height 5 and width 3 should be 15");
    }

    @Test
    public void testRectangleAreaZeroHeight() {
        Shape rectangle = new Rectangle(0, 3);
        assertEquals(0, rectangle.area(), "Area of Rectangle with height 0 should be 0");
    }

    @Test
    public void testRectangleAreaZeroWidth() {
        Shape rectangle = new Rectangle(5, 0);
        assertEquals(0, rectangle.area(), "Area of Rectangle with width 0 should be 0");
    }

    @Test
    public void testRectangleAreaNegativeHeight() {
        Shape rectangle = new Rectangle(-5, 3);
        assertTrue(Double.isNaN(rectangle.area()), "Area should be NaN for negative height");
    }

    @Test
    public void testRectangleAreaNegativeWidth() {
        Shape rectangle = new Rectangle(5, -3);
        assertTrue(Double.isNaN(rectangle.area()), "Area should be NaN for negative width");
    }

    // Null Object Tests
    @Test
    public void testNullCircle() {
        Circle circle = null;
        assertThrows(NullPointerException.class, () -> {
            circle.area();
        }, "Area should throw NullPointerException if Circle is null");
    }

    @Test
    public void testNullRectangle() {
        Rectangle rectangle = null;
        assertThrows(NullPointerException.class, () -> {
            rectangle.area();
        }, "Area should throw NullPointerException if Rectangle is null");
    }
}
