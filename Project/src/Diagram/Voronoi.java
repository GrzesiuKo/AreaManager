package Diagram;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import Common.Point;

public class Voronoi {
    private List<Edge> edges;
    private Item root;
    private Queue<Event> events;
    public static double currentYofSweepLine;


    public void generateDiagram(List<Point> points) {
        Queue<Event> events;
        Event currentEvent;

        events = getEvents(points);

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

    private Queue<Event> getEvents(List<Point> points){
        List<Point> keyPoints;
        Queue<Event> events = new PriorityQueue<Event>();

        keyPoints = points;

        keyPoints = sortPoints(keyPoints);

        for (Point p: keyPoints) {
            events.add(new Event(p, Event.SITE_EVENT));
        }

        return events;
    }

    private List<Point> sortPoints(List<Point> points) {
        List<Point> resultPoints = points;

        resultPoints.sort((one, two) -> (int) (two.getY() - one.getY()));

        return resultPoints;
    }

    private void handleSiteEvent(Point point){
        Arc arcAbove;

        if (root == null){
            root = new Arc(point);
            return;
        }


    }

}
