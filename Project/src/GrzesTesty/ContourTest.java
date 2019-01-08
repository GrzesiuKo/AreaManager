package GrzesTesty;

import Common.Contour;
import Common.Point;

import java.util.LinkedList;
import java.util.List;

public class ContourTest {
    public static void main(String[] args) {
        Contour contour;
        List<Point> points;

        points = new LinkedList<>();
        points.add(new Point(0, 0));
        points.add(new Point(5, 1));
        points.add(new Point(5, 4));
        points.add(new Point(1, 3));
        points.add(new Point(0, 5));
        points.add(new Point(-3, 3));
        points.add(new Point(0, 6));

        contour = new Contour(points);

        System.out.println("Contour Points: ");
        printList(contour.getContourPoints());

        System.out.println("Ignored Points: ");
        printList(contour.getIgnoredPoints());

    }

    private static void printList(List<? extends Point> list) {
        for (Point p : list) {
            System.out.println("x = " + p.getX() + " y = " + p.getY());
        }

        System.out.println();
    }

}
