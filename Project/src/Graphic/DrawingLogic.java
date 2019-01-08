package Graphic;

import Common.KeyPoint;
import Common.Point;
import Diagram.Diagram;
import Statistics.*;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.List;

public class DrawingLogic {
    Polygon contour;
    GraphicsContext gc;
    Diagram diagram;
    int scale;
    int drawingScale;

    public DrawingLogic(GraphicsContext gc, Diagram diagram) {
        scale = (int) gc.getCanvas().getWidth();
        drawingScale = (int) gc.getCanvas().getWidth()/200;
        Double[] Points = new Double[diagram.getContour().getContourPoints().size() * 2];
        for (int i = 0; i < diagram.getContour().getContourPoints().size(); i++) {
            Points[2 * i] = diagram.getContour().getContourPoints().get(i).getX() * scale;
            Points[2 * i + 1] = diagram.getContour().getContourPoints().get(i).getY() * scale;
        }
        for (int i = 0; i < diagram.getContour().getContourPoints().size() / 2; i++) {
        }
        contour = new Polygon();
        contour.getPoints().addAll(Points);
        System.out.println(contour.getPoints());
        this.gc = gc;
        this.diagram = diagram;
    }

    public void draw() {
        gc.setLineWidth(2);
        int scale = (int) gc.getCanvas().getWidth();
        drawCountour();
        drawKeyPoints();
        drawUserPoints();
        drawVoronoi();
    }

    private void drawCountour( ) {
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
            gc.fillOval(points.get(i).getX() * scale, points.get(i).getY() * scale, 4 *drawingScale, 4*drawingScale);
        }
    }

    private  void drawUserPoints() {
        Statistics toDraw = Statistics.getInstance();
        List<Bear> bears = toDraw.getBearList();
        List<School> schools = toDraw.getSchoolList();
        List<Residential> residentials = toDraw.getResidentialList();
        gc.setFill(Color.CYAN);
        for (int i = 0; i < bears.size(); i++) {
            gc.fillOval(bears.get(i).getLocalization().getX() * scale, bears.get(i).getLocalization().getY() * scale, 4, 4);
        }
        for (int i = 0; i < schools.size(); i++) {
            gc.fillOval(schools.get(i).getLocalization().getX() * scale, schools.get(i).getLocalization().getY() * scale, 4, 4);
        }
        for (int i = 0; i < residentials.size(); i++) {
            gc.fillOval(residentials.get(i).getLocalization().getX() * scale, residentials.get(i).getLocalization().getY() * scale, 4, 4);
        }
    }

    private void drawVoronoi() {
        List<KeyPoint> keyPoints = diagram.getKeyPoints();
        for (int i = 0; i < keyPoints.size(); i++) {
            List<Point> points = keyPoints.get(i).getAreaPoints();
            gc.setFill(Color.color(0.1 * i, 0.2 * i, 0.15 * i, 0.03));
            for (int j = 0; j < points.size(); j++) {
                //if (contour.contains(points.get(j).getX() * scale, points.get(j).getY()* scale)) {
                    gc.fillOval((points.get(j).getX() - 0.01)  * scale , (points.get(j).getY() -0.01 ) * scale , 10 *drawingScale, 10 * drawingScale);
                //}
            }
        }
    }
}
