package Common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTest {

    @Test
    void scaleCoordinateToInt() {
        //Given
        double a = 2.4;
        int precision = 10;
        int expected = 24;
        int result;
        //When
        result = Point.scaleCoordinateToInt(a, precision);
        //Then
        assertEquals(expected, result);

    }

    @Test
    void getBigger() {
        //Given
        Point a = new Point(20, 30);
        Point b = new Point(30, 20);
        Point result;
        //When
        result = a.getBigger(b);
        //Then
        assertEquals(a, result);
    }

    @Test
    void getSmaller() {
        //Given
        Point a = new Point(20, 30);
        Point b = new Point(30, 20);
        Point result;
        //When
        result = a.getSmaller(b);
        //Then
        assertEquals(b, result);
    }
}