package GrzesTesty;

import Common.Point;
import Diagram.Voronoi;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int N = 20;

        Stopwatch s = new Stopwatch();

        ArrayList<Point> points = new ArrayList<Point>();

        Random gen = new Random();

        for (int i = 0; i < N; i++) {
            if (i == 1) {
                points.add(new Point(0.3, 0.9));
                points.add(new Point(0.3, 0.5));
                points.add(new Point(0.3, 0.05));
                //points.add(new Point(0.4,0.06));
                //points.add(new Point(0.6,0.8));
            }
// else {
//               double x = gen.nextDouble();
//                double y = gen.nextDouble();
//                points.add(new Point(x, y));
////            }
        }

        double start = s.elapsedTime();
        //Voronoi diagram = new Voronoi();
        double stop = s.elapsedTime();

        System.out.println(stop - start);

//        diagram.generateDiagram(points);
//        diagram.getRoot().printTree(diagram.getRoot());
        // draw results
        StdDraw.setPenRadius(.005);
        for (Point p : points) {
            StdDraw.point(p.getX(), p.getY());
        }
        StdDraw.setPenRadius(.002);
        int i = 0;
//        for (Edge e: diagram.getEdges()) {
//            i++;
//            StdDraw.line(e.getStart().getX(), e.getStart().getY(), e.getEnd().getX(), e.getEnd().getY());
//            System.out.println(i);
//        }
//        System.out.println("Root: "+diagram.getRoot().getClass());
//        System.out.println("    left: "+diagram.getRoot().getLeft().getClass());
//        System.out.println("    right: "+diagram.getRoot().getRight().getClass());
//        System.out.println("        left: "+diagram.getRoot().getRight().getLeft().getClass());
//        System.out.println("        right: "+diagram.getRoot().getRight().getRight().getClass());


    }
}
