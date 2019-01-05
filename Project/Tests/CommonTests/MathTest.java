package CommonTests;


import Common.Math;
import Common.Point;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathTest {

    @Test
    void findBisectorEasy() {
        //Given
        Point a = new Point(0, 0);
        Point b = new Point(0, 2);
        Math.TwoResults results;
        //When
        results = Math.findBisector(a, b);
        //Then
        assertEquals(0, results.getFirst());
        assertEquals(1, results.getSecond());
    }

    @Test
    void findBisectorMedium() {
        //Given
        Point a = new Point(0, 4);
        Point b = new Point(4, 0);
        Math.TwoResults results;
        //When
        results = Math.findBisector(a, b);
        //Then
        assertEquals(1, results.getFirst());
        assertEquals(0, results.getSecond());
    }

    @Test
    void findIntersectionOfTwoStraightLines() {
        //Given
        Edge a = new Edge(null, new Point(0, 4), new Point(4, 0));
        Edge b = new Edge(null, new Point(0, 0), new Point(1, 1));
        Point result;
        //When
        result = Math.findIntersectionOfTwoStraightLines(a, b);
        //Then
        assertEquals(0.5, result.getX());
        assertEquals(0.5, result.getY());
    }

    @Test
    void findLengthOfSegment() {
        //Given
        Point a = new Point(0, 0);
        Point b = new Point(0, 5);
        double result;
        //When
        result = Math.findLengthOfSegment(a, b);
        //Then
        assertEquals( 5, result);
    }
}