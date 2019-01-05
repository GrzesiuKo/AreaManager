package Diagram;

import Common.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoronoiTest {
    Point tmp = new Point(0, 0);
    Cross AB = new Cross();
    Cross BA = new Cross();
    Cross BC = new Cross();
    Cross CB = new Cross();
    Arc a = new Arc(tmp);
    Arc a2 = new Arc( tmp);
    Arc b = new Arc(tmp);
    Arc b2 = new Arc(tmp);
    Arc c = new Arc(tmp);

    @BeforeEach
    void beforeEach(){
        AB.setParent(null);

        AB.setRight(BA);
        BA.setParent(AB);

        BA.setLeft(b);
        b.setParent(BA);

        BA.setRight(a2);
        a2.setParent(BA);

        AB.setLeft(a);
        a.setParent(AB);
    }

    @Test
    void addArcToTheBeachLine() {
        //Given
        Voronoi diagram = new Voronoi();
        //When
        diagram.addArcToTheBeachLine(new Point(0, 0), b);
        //Then
        assertTrue(BA.getLeft() instanceof Cross);
        assertTrue(BA.getLeft().getLeft() instanceof Arc);
        assertTrue(BA.getLeft().getRight() instanceof Cross);
        assertTrue(BA.getLeft().getRight().getLeft() instanceof Arc);
        assertTrue(BA.getLeft().getRight().getRight() instanceof Arc);
    }
}