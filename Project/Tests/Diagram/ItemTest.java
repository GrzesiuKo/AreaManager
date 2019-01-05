package Diagram;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ItemTest {

    @Test
    void replaceParentIfParentNull() {
        //Given
        Item a = new Item();
        Item b = new Item();
        Item c = new Item();
        Item x = new Item();

        a.setParent(null);
        a.setRight(b);
        a.setLeft(c);

        b.setParent(a);
        c.setParent(a);

        x.setParent(c);

        //When
        Item.replaceParent(a, x);
        //Then
        assertEquals(true, null == x.getParent());

    }

    @Test
    void replaceParentIfParentNotNull() {
        //Given
        Item a = new Item();
        Item b = new Item();
        Item c = new Item();
        Item x = new Item();

        a.setParent(null);
        a.setRight(b);
        a.setLeft(c);

        b.setParent(a);
        c.setParent(a);

        x.setParent(c);

        //When
        Item.replaceParent(c, x);
        //Then
        assertEquals(true, a == x.getParent());

    }

}