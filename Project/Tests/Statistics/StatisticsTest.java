package Statistics;

import Common.KeyPoint;
import Common.Point;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {

    @Test
    void findKeyPoint() {
        //Given
        List<KeyPoint> keyPoints = new LinkedList<>();
        keyPoints.add(new KeyPoint(1,1));
        keyPoints.add(new KeyPoint(1.1 ,1.1));
        Point point = new Point(2,2);
        //When
        KeyPoint nearest = Statistics.findKeyPoint(point,keyPoints);
        //Then
        assertEquals(nearest,keyPoints.get(1));
    }

    @Test
    void findKeyPointEmptyList() {
        //Given
        List<KeyPoint> keyPoints = new LinkedList<>();
        Point point = new Point(15,15);
        //When
        KeyPoint nearest = Statistics.findKeyPoint(point,keyPoints);
        //Then
        assertNull(nearest);
    }

    @Test
    void findKeyPointNullPoint() {
        //Given
        List<KeyPoint> keyPoints = new LinkedList<>();
        keyPoints.add(new KeyPoint(5,5));
        Point point = null;
        //When
        KeyPoint nearest = Statistics.findKeyPoint(point,keyPoints);
        //Then
        assertNull(nearest);
    }
}