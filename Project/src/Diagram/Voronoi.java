package Diagram;

import Common.Math;
import Common.Point;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Voronoi {
    private List<Edge> edges;
    private Item root;
    private Queue<Event> events;
    public static double currentYofSweepLine;

    public void generateDiagram(List<Point> points) {
        Queue<Event> events;
        Event currentEvent;

        events = getEvents(points);
        circleEvents = new PriorityQueue<Event>();
        siteEvents = getSiteEvents(points);

        while (!events.isEmpty()){
            currentEvent = events.poll();
            currentYofSweepLine = currentEvent.getPoint().getY();
            if (currentEvent.isSiteEvent()){
               handleSiteEvent(currentEvent.getPoint());
            }else{
               // handleCircleEvent();
            }
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
        Edge edgeLeft = new Edge(start);
        Edge edgeRight = new Edge(start);

        leftCross.setLeft(new Arc(arcAbove.getFocus()));
        Item.replaceParent(arcAbove, leftCross);
        leftCross.setRight(rightCross);
        leftCross.setEdge(edgeLeft);

        rightCross.setRight(new Arc(arcAbove.getFocus()));
        rightCross.setLeft(new Arc(keyPoint));
        rightCross.setEdge(edgeRight);

    }
}
