package Diagram;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import Common.Point;

public class Voronoi {
    List<Edge> edges;
    Item root;
    Queue<Event> events;

    public void generateDiagram(List<Point> points) {
        Queue<Event> events;
        Event currentEvent;

        events = getEvents(points);

        while (!events.isEmpty()){
            currentEvent = events.poll();
            if (currentEvent.isSiteEvent()){
               // handleSiteEvent();
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


}
