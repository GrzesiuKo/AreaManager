package Common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorTest {

    @Test
    void findAngleBetween() {
        //Given
        Vector a = new Vector(0, 10);
        Vector b = new Vector(10, 0);
        double result;
        double expected = Math.PI / 2;
        //When
        result = a.findAngleBetween(b);
        //Then
        assertEquals(expected, result);
    }

    @Test
    void findCosinusBetween() {
        //Given
        Vector a = new Vector(0, 10);
        Vector b = new Vector(10, 0);
        double result;
        double expected = 0;
        //When
        result = a.findCosinusBetween(b);
        //Then
        assertEquals(expected, result);
    }
}