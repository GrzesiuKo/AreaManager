package Graphic;

import Common.KeyPoint;
import Common.Point;
import Diagram.Diagram;
import Statistics.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.List;

public class DrawingLogic {
    private Polygon contour;
    private GraphicsContext gc;
    private Diagram diagram;
    private Double scale;
    private int keyPointSize;
    private int objectPointSize;


    DrawingLogic(GraphicsContext gc, Diagram diagram) {
        scale = gc.getCanvas().getWidth() / 100;
        this.diagram = diagram;
        Double[] Points = new Double[diagram.getContour().getContourPoints().size() * 2];
        for (int i = 0; i < diagram.getContour().getContourPoints().size(); i++) {
            Points[2 * i] = diagram.getContour().getContourPoints().get(i).getX() * scale + 0.1;
            Points[2 * i + 1] = diagram.getContour().getContourPoints().get(i).getY() * scale + 0.1;
        }
        contour = new Polygon();
        contour.getPoints().addAll(Points);
        checkKeyPoints();
        this.gc = gc;
        keyPointSize = 10;
        objectPointSize = 8;
    }

    public static void clear(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void draw() {
        clear(gc);
        gc.setLineWidth(6);
        checkKeyPoints();
        drawVoronoi();
        drawCountour();
        drawKeyPoints();
        drawUserPoints();
    }

    private void drawCountour() {
        List<Point> contour = diagram.getContour().getContourPoints();
        double[] xPoints = new double[contour.size()];
        double[] yPoints = new double[contour.size()];
        for (int i = 0; i < contour.size(); i++) {
            xPoints[i] = contour.get(i).getX() * scale;
            yPoints[i] = contour.get(i).getY() * scale;
        }
        gc.strokePolygon(xPoints, yPoints, contour.size());
    }

    private void drawKeyPoints() {
        List<KeyPoint> points = diagram.getKeyPoints();
        gc.setFill(Color.RED);
        for (KeyPoint point : points) {
            if (contour.contains(point.getX() * scale, point.getY() * scale)) {
                gc.fillOval(point.getX() * scale, point.getY() * scale, keyPointSize, keyPointSize);
            }
        }
    }

    private void drawUserPoints() {
        Statistics toDraw = Statistics.getInstance();
        toDraw.recheckData(diagram.getKeyPoints(), diagram.getArea());
        List<Bear> bears = toDraw.getBearList();
        List<School> schools = toDraw.getSchoolList();
        List<Residential> residentials = toDraw.getResidentialList();
        List<Moose> mooses = toDraw.getMooseList();

        gc.setFill(Color.BLUEVIOLET);
        for (int i = 0; i < bears.size(); i++) {
            if (contour.contains(bears.get(i).getLocalization().getX() * scale, bears.get(i).getLocalization().getY() * scale)) {
                gc.fillOval(bears.get(i).getLocalization().getX() * scale, bears.get(i).getLocalization().getY() * scale, objectPointSize, objectPointSize);
            } else {
                toDraw.deleteObject(bears.get(i), "Bear");
                i--;
            }
        }
        for (int i = 0; i < schools.size(); i++) {
            if (contour.contains(schools.get(i).getLocalization().getX() * scale, schools.get(i).getLocalization().getY() * scale)) {
                gc.fillOval(schools.get(i).getLocalization().getX() * scale, schools.get(i).getLocalization().getY() * scale, objectPointSize, objectPointSize);
            } else {
                toDraw.deleteObject(schools.get(i), "School");
                i--;
            }
        }
        for (int i = 0; i < residentials.size(); i++) {
            if (contour.contains(residentials.get(i).getLocalization().getX() * scale, residentials.get(i).getLocalization().getY() * scale)) {
                gc.fillOval(residentials.get(i).getLocalization().getX() * scale, residentials.get(i).getLocalization().getY() * scale, objectPointSize, objectPointSize);
            } else {
                toDraw.deleteObject(residentials.get(i), "Residential");
                i--;
            }
        }
        for (int i = 0; i < mooses.size(); i++) {
            if (contour.contains(mooses.get(i).getLocalization().getX() * scale, mooses.get(i).getLocalization().getY() * scale)) {
                gc.fillOval(mooses.get(i).getLocalization().getX() * scale, mooses.get(i).getLocalization().getY() * scale, objectPointSize, objectPointSize);
            } else {
                toDraw.deleteObject(mooses.get(i), "Moose");
                i--;
            }
        }
    }

    private void drawVoronoi() {
        boolean[][] colored = new boolean[10000][10000];
        double x, y;
        List<KeyPoint> keyPoints = diagram.getKeyPoints();
        for (int i = keyPoints.size() - 1; i >= 0; i--) {
            List<Point> points = keyPoints.get(i).getAreaPoints();
            gc.setFill(Color.rgb((80 * i) % 255, (90 * i) % 255, (70 * i) % 255, 0.1));
            for (Point point : points) {
                x = point.getX() * scale;
                y = point.getY() * scale;
                if (contour.contains(x, y) && !colored[(int) x * 10][(int) y * 10]) {
                    gc.fillOval(x, y, 3, 3);
                    colored[(int) x * 10][(int) y * 10] = true;
                }
            }
        }
    }

    public boolean inContour(Point point) {
        return contour.contains(point.getX(), point.getY());
    }

    public KeyPoint findKeyPoint(Point fromUser) {
        KeyPoint nearest = Statistics.findKeyPoint(fromUser, diagram.getKeyPoints());
        if (nearest == null) {
            return null;
        }
        if (Math.sqrt(Math.pow(fromUser.getX() - nearest.getX(), 2) + Math.pow(fromUser.getY() - nearest.getY(), 2)) < 3) {
            return nearest;
        }
        return null;
    }

    public Point findCounturPoint(Point fromUser) {
        List<Point> contourPoints = diagram.getContour().getContourPoints();
        double distance = Math.sqrt(Math.pow(fromUser.getX() - contourPoints.get(0).getX(), 2) + Math.pow(fromUser.getY() - contourPoints.get(0).getY(), 2));
        Point nearest = contourPoints.get(0);
        for (Point contourPoint : contourPoints) {
            if (Math.sqrt(Math.pow(fromUser.getX() - contourPoint.getX(), 2) + Math.pow(fromUser.getY() - contourPoint.getY(), 2)) < distance) {
                distance = Math.sqrt(Math.pow(fromUser.getX() - contourPoint.getX(), 2) + Math.pow(fromUser.getY() - contourPoint.getY(), 2));
                nearest = contourPoint;
            }
        }
        if (Math.sqrt(Math.pow(fromUser.getX() - nearest.getX(), 2) + Math.pow(fromUser.getY() - nearest.getY(), 2)) < 3) {
            return nearest;
        }
        return null;
    }

    private void checkKeyPoints() {
        List<KeyPoint> points = diagram.getKeyPoints();
        for (int i = 0; i < points.size(); i++) {
            KeyPoint point = points.get(i);
            if (!contour.contains(point.getX() * scale, point.getY() * scale)) {
                diagram.deleteKeyPoint(point);
                i--;
            }
        }
    }

    public Double getScale() {
        return scale;
    }


}
