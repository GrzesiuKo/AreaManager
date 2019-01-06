package Graphic;

import Common.KeyPoint;
import Common.Point;
import Diagram.Diagram;
import Statistics.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class DrawingLogic {

    public static void draw(GraphicsContext gc , Diagram diagram){
        gc.setLineWidth(2);
        drawCountour( gc, diagram.getContour().getContourPoints());
        drawKeyPoints(gc , diagram.getKeyPoints());
        drawUserPoints(gc);
    }

    private static void drawCountour(GraphicsContext gc, List<Point> contour) {
        double[] xPoints = new double[contour.size()];
        double[] yPoints = new double[contour.size()];
        for (int i = 0; i < contour.size(); i++) {
            xPoints[i] = contour.get(i).getX();
            yPoints[i] = contour.get(i).getY();
        }
        gc.strokePolygon(xPoints, yPoints, contour.size());
    }
    private static void drawKeyPoints(GraphicsContext gc , List<KeyPoint> points){
        gc.setFill(Color.RED);
        for(int i = 0 ; i < points.size() ; i++){
            gc.fillOval(points.get(i).getX(), points.get(i).getY(), 2, 2);
        }
    }

    private static void drawUserPoints(GraphicsContext gc ) {
        Statistics toDraw = Statistics.getInstance();
        List<Bear> bears = toDraw.getBearList();
        List<School> schools = toDraw.getSchoolList();
        List<Residential> residentials = toDraw.getResidentialList();
        gc.setFill(Color.GREEN);
        for(int i = 0 ; i < bears.size() ; i++){
           gc.fillOval( bears.get(i).getLocalization().getX(), bears.get(i).getLocalization().getY(), 2,2);
        }
        for(int i = 0 ; i < schools.size() ; i++){
            gc.fillOval( schools.get(i).getLocalization().getX(), schools.get(i).getLocalization().getY(), 2,2);
        }
        for(int i = 0 ; i < residentials.size() ; i++){
            gc.fillOval( residentials.get(i).getLocalization().getX(), residentials.get(i).getLocalization().getY(), 2,2);
        }
    }
}
