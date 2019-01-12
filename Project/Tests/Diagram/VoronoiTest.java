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
        size = 1000;
        KeyPoint a = new KeyPoint(0.4, 0.0);
        KeyPoint b = new KeyPoint(0.0, 0.4);
        keyPoints.add(a);
        keyPoints.add(b);
        //When
        voronoi = new Voronoi(size, keyPoints, null);
        voronoi.makeAreas(size, keyPoints);
        //Then
        for (Point p : a.getAreaPoints()) {
            assertTrue(findLengthOfSegment(p, a) <= findLengthOfSegment(p, b));
        }
        for (Point p : b.getAreaPoints()) {
            assertTrue(findLengthOfSegment(p, a) >= findLengthOfSegment(p, b));
        }
    }

    @Test
    void addKeyPoint() {
        //Given
        size = 100;
        KeyPoint a = new KeyPoint(0.4, 0.0);
        KeyPoint b = new KeyPoint(0.0, 0.4);
        KeyPoint c = new KeyPoint(0.0, 0.2);
        keyPoints.add(a);
        keyPoints.add(b);
        //When
        voronoi = new Voronoi(size, keyPoints, null);
        voronoi.makeAreas(size, keyPoints);
        voronoi.addKeyPoint(c);
        //Then
        for (Point p : a.getAreaPoints()) {
            assertTrue(findLengthOfSegment(p, a) <= findLengthOfSegment(p, b));
        }
        for (Point p : b.getAreaPoints()) {
            assertTrue(findLengthOfSegment(p, a) >= findLengthOfSegment(p, b));
        }
        for (Point p : c.getAreaPoints()) {
            assertTrue(findLengthOfSegment(p, a) >= findLengthOfSegment(p, c));
            assertTrue(findLengthOfSegment(p, b) >= findLengthOfSegment(p, c));
        }
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