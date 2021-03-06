package Common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContourTests {
    Contour contour;
    List<Point> points;

    @BeforeEach
    public void beforeEach() {
        contour = null;
        points = null;
    }

    @Test
    public void notEnoughPoints() {
        //Given
        points = new LinkedList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, 2));
        //When
        contour = new Contour(points);
        //Then
        assertFalse(contour.isContourValid());
        assertNull(contour.getContourPoints());
        assertNull(contour.getIgnoredPoints());
    }

    @Test
    public void onlyCollinearPoints() {
        //Given
        points = new LinkedList<>();
        points.add(new Point(0, 1));
        points.add(new Point(0, 2));
        points.add(new Point(0, 3));
        //When
        contour = new Contour(points);
        //Then
        assertFalse(contour.isContourValid());
        assertNull(contour.getContourPoints());
        assertNull(contour.getIgnoredPoints());
    }

    @Test
    public void goodPoints() {
        //Given
        points = new LinkedList<>();
        points.add(new Point(0, 0));
        points.add(new Point(2, 1));
        points.add(new Point(1, 2));
        //When
        contour = new Contour(points);
        //Then
        assertTrue(contour.isContourValid());
        assertTrue(contour.getContourPoints().containsAll(points));
        assertTrue(contour.getIgnoredPoints().isEmpty());
    }

    @Test
    public void onePointToIgnore() {
        //Given
        LinkedList<Point> expectedIgnored = new LinkedList<>();
        Point toIgnore = new Point(1, 1);
        expectedIgnored.add(toIgnore);

        points = new LinkedList<>();
        points.add(new Point(0, -1));
        points.add(new Point(0, 2));
        points.add(new Point(0, 3));
        points.add(new Point(2, 2));
        points.add(toIgnore);

        //When
        contour = new Contour(points);
        points.remove(toIgnore);
        //Then
        assertTrue(contour.isContourValid());
        assertTrue(contour.getContourPoints().containsAll(points));
        assertEquals(expectedIgnored, contour.getIgnoredPoints());
    }

    @Test
    public void onePointToIgnoreTwoPointsSameMaxHeight() {
        //Given
        LinkedList<Point> expectedIgnored = new LinkedList<>();
        Point toIgnore = new Point(1, 1);
        expectedIgnored.add(toIgnore);

        points = new LinkedList<>();
        points.add(new Point(0, 0));
        points.add(new Point(2, 1));
        points.add(new Point(0, 2));
        points.add(new Point(2, 2));
        points.add(toIgnore);

        //When
        contour = new Contour(points);
        points.remove(toIgnore);
        //Then
        assertTrue(contour.isContourValid());
        assertTrue(contour.getContourPoints().containsAll(points));
        assertEquals(expectedIgnored, contour.getIgnoredPoints());
    }

    @Test
    public void onePointToIgnoreTwoPointsSameMinHeight() {
        //Given
        LinkedList<Point> expectedIgnored = new LinkedList<>();
        Point toIgnore = new Point(1, 1);
        expectedIgnored.add(toIgnore);

        points = new LinkedList<>();
        points.add(new Point(0, 0));
        points.add(new Point(2, 0));
        points.add(new Point(0, 1));
        points.add(new Point(2, 2));
        points.add(toIgnore);

        //When
        contour = new Contour(points);
        points.remove(toIgnore);
        //Then
        assertTrue(contour.isContourValid());
        assertTrue(contour.getContourPoints().containsAll(points));
        assertEquals(expectedIgnored, contour.getIgnoredPoints());
    }

    @Test
    public void onePointToIgnoreInSquare() {
        //Given
        LinkedList<Point> expectedIgnored = new LinkedList<>();
        Point toIgnore = new Point(1, 1);
        expectedIgnored.add(toIgnore);

        points = new LinkedList<>();
        points.add(new Point(0, 0));
        points.add(new Point(2, 0));
        points.add(new Point(0, 2));
        points.add(new Point(2, 2));
        points.add(toIgnore);

        //When
        contour = new Contour(points);
        points.remove(toIgnore);
        //Then
        assertTrue(contour.isContourValid());
        assertTrue(contour.getContourPoints().containsAll(points));
        assertEquals(expectedIgnored, contour.getIgnoredPoints());
    }

}