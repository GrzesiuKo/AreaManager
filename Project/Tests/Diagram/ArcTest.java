package Diagram;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcTest {

    Cross AB = new Cross();
    Cross BA = new Cross();
    Cross BC = new Cross();
    Cross CB = new Cross();
    Arc a = new Arc(null);
    Arc a2 = new Arc( null);
    Arc b = new Arc(null);
    Arc b2 = new Arc(null);
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

        BA.setRight(a2);
        a2.setParent(BA);

        BC.setLeft(b);
        b.setParent(BC);
        CB.setLeft(c);
        c.setParent(CB);
        CB.setRight(b2);
        b2.setParent(CB);
    }


    @Test
    void getNearestLeftCross() {
        assertSame(AB, b.getNearestLeftCross());
        assertSame(BC, c.getNearestLeftCross());
    }

    @Test
    void getNearestRightCross() {
        assertSame(BC, b.getNearestRightCross());
        assertSame(AB, a.getNearestRightCross());
    }

}