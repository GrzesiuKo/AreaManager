package Diagram;

import Common.Math;
import Common.Point;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Voronoi {
    public static double currentYofSweepLine;
    private List<Edge> edges;
    public static Item root;
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

        return events;
    }

    private List<Point> sortPoints(List<Point> points) {
        List<Point> resultPoints = points;

        resultPoints.sort((one, two) -> (int) (two.getY() - one.getY()));

        return resultPoints;
    }

    private void handleSiteEvent(Point point) {
        Arc arcAbove;

        if (root == null) {
            root = new Arc(point);
            return;
        }

        arcAbove = findArcAbove(point);

        addArcToTheBeachLine(point, arcAbove);

    }

    private Arc findArcAbove(Point point) {
        Item item = root;
        double xCoordinateOfCross;

        while (item instanceof Cross) {
            xCoordinateOfCross = ((Cross) item).getCurrentX();
            if (point.getX() < xCoordinateOfCross) {
                item = item.getLeft();
            } else {
                item = item.getRight();
            }
        }

        return (Arc) item;
    }

    private void addArcToTheBeachLine(Point keyPoint, Arc arcAbove) {
        Cross leftCross = new Cross();
        Cross rightCross = new Cross();
        Point start = new Point(keyPoint.getX(), arcAbove.findY(keyPoint.getX()));
        Edge edgeLeft = new Edge(start, keyPoint, arcAbove.getFocus());
        Edge edgeRight = new Edge(start, keyPoint, arcAbove.getFocus());

        edgeLeft.setSister(edgeRight);
        edgeRight.setSister(edgeLeft);

        leftCross.setLeft(new Arc(arcAbove.getFocus()));
        Item.replaceParent(arcAbove, leftCross);
        leftCross.setRight(rightCross);
        leftCross.setEdge(edgeLeft);

        rightCross.setRight(new Arc(arcAbove.getFocus()));
        rightCross.setLeft(new Arc(keyPoint));
        rightCross.setEdge(edgeRight);

    }
}
