package Diagram;

import Common.Math;
import Common.Point;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Voronoi {
    public static double currentYofSweepLine;
    private List<Edge> edges;
    private Item root;
    private Queue<Event> siteEvents;
    private Queue<Event> circleEvents;

    public void generateDiagram(List<Point> points) {
        Event currentEvent;

        circleEvents = new PriorityQueue<Event>();
        siteEvents = getSiteEvents(points);

        while (!siteEvents.isEmpty()) {
            currentEvent = siteEvents.poll();
            currentYofSweepLine = currentEvent.getPoint().getY();
            handleSiteEvent(currentEvent.getPoint());
        }

        while (!circleEvents.isEmpty()) {
            currentEvent = circleEvents.poll();
            System.out.println(" x: " + currentEvent.getPoint().getX() + " y: " + currentEvent.getPoint().getY());
            //handleCircleEvent(currentEvent.getPoint());
        }
    }

    private Queue<Event> getSiteEvents(List<Point> points) {
        List<Point> keyPoints;
        Queue<Event> events = new PriorityQueue<Event>();

        keyPoints = points;

        keyPoints = sortPoints(keyPoints);

        for (Point p : keyPoints) {
            events.add(new Event(p, Event.SITE_EVENT));
        }

        for (Event e: events){
            System.out.println("x: "+e.getPoint().getX()+" y: "+e.getPoint().getY());
        }

        return events;
    }

    private List<Point> sortPoints(List<Point> points) {
        List<Point> resultPoints = points;

        resultPoints.sort((one, two) -> {
            double resultY = one.getY() - two.getY();
            if (resultY < 0) {
                return 1;
            } else if (resultY > 0) {
                return -1;
            }

            double resultX = one.getX() - two.getX();
            if (resultX < 0) {
                return 1;
            } else if (resultX > 0) {
                return -1;
            }
            return 0;
        });

//        for (Point p: points){
//            System.out.println("x: "+p.getX()+" y: "+p.getY());
//        }

        return resultPoints;
    }

    private void handleSiteEvent(Point point) {
        Arc arcAbove;

        if (root == null) {
            root = new Arc(point);
            return;
        }

        arcAbove = findArcAbove(point);

        System.out.println("Checked Point: x "+point.getX()+" y "+point.getY()+" found Arc: x "+arcAbove.getFocus().getX()+" y "+arcAbove.getFocus().getY());

        addArcToTheBeachLine(point, arcAbove);

    }

    private Arc findArcAbove(Point point) {
        Item item = root;
        double xCoordinateOfCross;

        while (item instanceof Cross) {
            xCoordinateOfCross = ((Cross) item).getCurrentX(currentYofSweepLine);
            if (point.getX() < xCoordinateOfCross) {
                item = item.getLeft();
            } else {
                item = item.getRight();
            }
        }

        return (Arc) item;
    }

    public void addArcToTheBeachLine(Point keyPoint, Arc arcAbove) {
        Cross leftCross = new Cross();
        Cross rightCross = new Cross();
        Point start = new Point(keyPoint.getX(), arcAbove.findY(keyPoint.getX()));
        Edge edgeLeft = new Edge(start, keyPoint, arcAbove.getFocus());
        Edge edgeRight = new Edge(start, keyPoint, arcAbove.getFocus());

        edgeLeft.setSister(edgeRight);
        edgeRight.setSister(edgeLeft);

        leftCross.setLeft(new Arc(arcAbove.getFocus()));

       if( Item.replaceParent(arcAbove, leftCross)==1){
           root = leftCross;
       }

        leftCross.setRight(rightCross);
        leftCross.setEdge(edgeLeft);

        rightCross.setRight(new Arc(arcAbove.getFocus()));
        rightCross.setLeft(new Arc(keyPoint));
        rightCross.setEdge(edgeRight);

        checkForCircleEvent((Arc) leftCross.getLeft());
        checkForCircleEvent((Arc) rightCross.getRight());
    }

    private void checkForCircleEvent(Arc arc) {
        Cross nearestLeftCross = arc.getNearestLeftCross();
        Cross nearestRightCross = arc.getNearestRightCross();
        Point intersection;
        Point eventPoint;
        double radius;
        double eventsY;

        if (nearestLeftCross == null || nearestRightCross == null) {
            return;
        }

        intersection = findIntersection(nearestLeftCross.getEdge(), nearestRightCross.getEdge());
if (intersection == null){

}
        radius = Math.findLengthOfSegment(arc.getFocus(), intersection);

        eventsY = intersection.getY() - radius;

        eventPoint = new Point(intersection.getX(), eventsY);

        addCircleEvent(eventPoint, arc);
    }

    private Point findIntersection(Edge first, Edge second) {
        return Math.findIntersectionOfTwoStraightLines(first, second);
    }

    private void addCircleEvent(Point eventPoint, Arc fadingArc){
        Event circleEvent = new Event(eventPoint, Event.CIRCLE_EVENT);

        fadingArc.setEvent(circleEvent);
        circleEvent.setArc(fadingArc);

        circleEvents.add(circleEvent);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Item getRoot() {
        return root;
    }
}
