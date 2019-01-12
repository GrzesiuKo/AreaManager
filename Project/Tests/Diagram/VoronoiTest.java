package Diagram;

import Common.KeyPoint;
import Common.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VoronoiTest {
    Voronoi voronoi;
    int size;
    List<KeyPoint> keyPoints;

    @BeforeEach
    public void beforeEach() {
        voronoi = null;
        size = 1000;
        keyPoints = new LinkedList<>();
    }

    @Test
    void makeAreas() {
        //Given
        size = 3;
        KeyPoint a = new KeyPoint(0.4, 0.0);
        KeyPoint b = new KeyPoint(0.0, 0.4);
        keyPoints.add(a);
        keyPoints.add(b);
        LinkedList<Point> aPoints = new LinkedList<>();
        aPoints.add(new Point(0.0, 0.0));
        aPoints.add(new Point(0.0, 0.1));
        aPoints.add(new Point(0.0, 0.2));
        aPoints.add(new Point(0.1, 0.0));
        aPoints.add(new Point(0.2, 0.0));
        aPoints.add(new Point(0.1, 0.1));
        LinkedList<Point> bPoints = new LinkedList<>();
        bPoints.add(new Point(0.3, 0.3));
        bPoints.add(new Point(0.2, 0.3));
        bPoints.add(new Point(0.1, 0.3));
        bPoints.add(new Point(0.3, 0.2));
        bPoints.add(new Point(0.3, 0.1));
        bPoints.add(new Point(0.2, 0.2));
        LinkedList<Point> rest = new LinkedList<>();
        rest.add(new Point(0.3, 0.0));
        rest.add(new Point(0.2, 0.1));
        rest.add(new Point(0.1, 0.2));
        rest.add(new Point(0.0, 0.3));

        //When
        voronoi = new Voronoi(size, keyPoints, null);
        voronoi.makeAreas(size, keyPoints);
        //Then
        for (Point p : a.getAreaPoints()) {
            assertTrue(findLengthOfSegment(p, a) < findLengthOfSegment(p, b));
        }
        for (Point p : b.getAreaPoints()) {
            assertTrue(findLengthOfSegment(p, a) > findLengthOfSegment(p, b));
        }
    }

    @Test
    void addKeyPoint() {
        //Given
        size = 10;
        keyPoints.add(new KeyPoint(0.0, 0.0));
        keyPoints.add(new KeyPoint(0.9, 0.0));
        keyPoints.add(new KeyPoint(0.0, 0.9));
        keyPoints.add(new KeyPoint(0.9, 0.9));

        //When
        voronoi = new Voronoi(size, keyPoints, null);
        keyPoints.add(new KeyPoint(5, 5));
        voronoi.makeAreas(100, keyPoints);
        //Then
    }

    @Test
    void deleteKeyPoint() {
    }

    private double findLengthOfSegment(Point a, Point b) {
        double first = java.lang.Math.pow(a.getX() - b.getX(), 2);
        double second = java.lang.Math.pow(a.getY() - b.getY(), 2);

        return java.lang.Math.sqrt(first + second);
    }
}