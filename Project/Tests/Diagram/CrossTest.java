package Diagram;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrossTest {
    Cross AB = new Cross();
    Cross BA = new Cross();
    Cross BC = new Cross();
    Cross CB = new Cross();
    Arc a = new Arc(null);
    Arc b = new Arc(null);
    Arc c = new Arc(null);

    @BeforeEach
    void beforeEach(){
        AB.setParent(null);
        AB.setRight(BA);
        BA.setParent(AB);
        BA.setLeft(BC);
        BC.setParent(BA);
        BC.setRight(CB);
        CB.setParent(BC);

        AB.setLeft(a);
        a.setParent(AB);

        BA.setRight(a);
        a.setParent(BA);

        BC.setLeft(b);
        CB.setLeft(c);
        CB.setRight(b);
    }

    @Test
    void getNearestLeftArc() {
        assertSame(b, BA.getNearestLeftArc());
        assertSame( a, AB.getNearestLeftArc());
    }

    @Test
    void getNearestRightArc() {
        assertSame(a, BA.getNearestRightArc());
        assertSame( b, AB.getNearestRightArc());
    }

}