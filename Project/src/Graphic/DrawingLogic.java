package Graphic;

import Common.KeyPoint;
import Common.Point;
import Diagram.Diagram;
import Statistics.Bear;
import Statistics.Residential;
import Statistics.School;
import Statistics.Statistics;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.List;

public class    DrawingLogic {
    private Polygon contour;
    private GraphicsContext gc;
    private Diagram diagram;
    private Double scale;
    private int drawingScale;
    private int keyPointSize;
    private int objectPointSize;


    public DrawingLogic(GraphicsContext gc, Diagram diagram) {
        scale = gc.getCanvas().getWidth() / 100;
        drawingScale = 1;
        Double[] Points = new Double[diagram.getContour().getContourPoints().size() * 2];
        for (int i = 0; i < diagram.getContour().getContourPoints().size(); i++) {
            Points[2 * i] = diagram.getContour().getContourPoints().get(i).getX() * scale + 0.1;
            Points[2 * i + 1] = diagram.getContour().getContourPoints().get(i).getY() * scale + 0.1;
        }
        for (int i = 0; i < diagram.getContour().getContourPoints().size() / 2; i++) {
        }
        contour = new Polygon();
        contour.getPoints().addAll(Points);
        this.gc = gc;
        this.diagram = diagram; // = checkKeyPoints(diagram);
        keyPointSize = 10;
        objectPointSize = 8;
    }

    public void draw() {
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        gc.setLineWidth(6);
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
        for (int i = 0; i < points.size(); i++) {
            gc.fillOval(points.get(i).getX() * scale, points.get(i).getY() * scale, keyPointSize, keyPointSize);
        }
    }

    private void drawUserPoints() {
        Statistics toDraw = Statistics.getInstance();
        toDraw.recheckData(diagram.getKeyPoints() , diagram.getArea());
        List<Bear> bears = toDraw.getBearList();
        List<School> schools = toDraw.getSchoolList();
        List<Residential> residentials = toDraw.getResidentialList();

        gc.setFill(Color.BLUEVIOLET);
        for (int i = 0; i < bears.size(); i++) {
            if (contour.contains(bears.get(i).getLocalization().getX() * scale, bears.get(i).getLocalization().getY() * scale)) {
                gc.fillOval(bears.get(i).getLocalization().getX() * scale, bears.get(i).getLocalization().getY() * scale, objectPointSize, objectPointSize);
            } else {
                toDraw.deleteObject(i, "Bear");
            }
        }
        for (int i = 0; i < schools.size(); i++) {
            if (contour.contains(schools.get(i).getLocalization().getX() * scale, schools.get(i).getLocalization().getY() * scale)) {
                gc.fillOval(schools.get(i).getLocalization().getX() * scale, schools.get(i).getLocalization().getY() * scale, objectPointSize, objectPointSize);
            } else {
                toDraw.deleteObject(i, "School");
            }
        }
        for (int i = 0; i < residentials.size(); i++) {
            if (contour.contains(residentials.get(i).getLocalization().getX() * scale, residentials.get(i).getLocalization().getY() * scale)) {
                gc.fillOval(residentials.get(i).getLocalization().getX() * scale, residentials.get(i).getLocalization().getY() * scale, objectPointSize, objectPointSize);
            } else {
                toDraw.deleteObject(i, "Residential");
            }
        }
    }

    private void drawVoronoi() {
        boolean[][] colored = new boolean[10000][10000];
        double x,y;
        List<KeyPoint> keyPoints = diagram.getKeyPoints();
        for (int i = keyPoints.size() - 1; i >= 0 ; i--) {
            List<Point> points = keyPoints.get(i).getAreaPoints();
            gc.setFill(Color.rgb((80*i)%255,(90*i)%255,(70*i)%255,0.1));
            for (int j = 0; j < points.size() ; j++) {
                x = points.get(j).getX() * scale;
                y = points.get(j).getY() * scale;
                if (contour.contains(x, y) && !colored[(int)x * 10][(int)y * 10] ) {
                    gc.fillOval(x,y, 3, 3);
                    colored[(int)x * 10][(int)y *10] = true;
                }
            }
        }
    }

    public boolean inContour(Point point){
        return contour.contains(point.getX(), point.getY());
    }

//    private void checkKeyPoints(Diagram diagram){
//        List<KeyPoint> points = diagram.getKeyPoints();
//        for (int i = 0; i < points.size(); i++) {
//            gc.fillOval(points.get(i).getX() * scale, points.get(i).getY() * scale, keyPointSize, keyPointSize);
//        }
//    }

    public KeyPoint checkTwojaStara(Point fromUser){
       KeyPoint nearest = Statistics.findKeyPoint(fromUser , diagram.getKeyPoints());
       if (Math.sqrt(Math.pow(fromUser.getX() - nearest.getX(), 2) + Math.pow(fromUser.getY() - nearest.getY(), 2)) < 3){
           return  nearest;
       }
       return null;
    }

    public Double getScale() {
        return scale;
    }
}
