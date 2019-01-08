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
        int scale = (int) gc.getCanvas().getWidth() /Diagram.X_SIZE ;
        drawCountour( gc, diagram.getContour().getContourPoints() , scale);
        drawKeyPoints(gc , diagram.getKeyPoints() , scale);
        drawUserPoints(gc , scale);
        drawVoronoi(gc,diagram.getKeyPoints() , scale);
    }

    private static void drawCountour(GraphicsContext gc, List<Point> contour, int scale) {
        double[] xPoints = new double[contour.size()];
        double[] yPoints = new double[contour.size()];
        for (int i = 0; i < contour.size(); i++) {
            xPoints[i] = contour.get(i).getX()* scale ;
            yPoints[i] = contour.get(i).getY()* scale;
        }
        gc.strokePolygon(xPoints, yPoints, contour.size());
    }
    private static void drawKeyPoints(GraphicsContext gc , List<KeyPoint> points , int scale){
        gc.setFill(Color.RED);
        for(int i = 0 ; i < points.size() ; i++){
            gc.fillOval(points.get(i).getX()* scale, points.get(i).getY()* scale, 4, 4);
        }
    }

    private static void drawUserPoints(GraphicsContext gc , int scale) {
        Statistics toDraw = Statistics.getInstance();
        List<Bear> bears = toDraw.getBearList();
        List<School> schools = toDraw.getSchoolList();
        List<Residential> residentials = toDraw.getResidentialList();
        gc.setFill(Color.GREEN);
        for(int i = 0 ; i < bears.size() ; i++){
            gc.fillOval( bears.get(i).getLocalization().getX()* scale, bears.get(i).getLocalization().getY()* scale, 4,4);
        }
        for(int i = 0 ; i < schools.size() ; i++){
            gc.fillOval( schools.get(i).getLocalization().getX()* scale, schools.get(i).getLocalization().getY()* scale, 4,4);
        }
        for(int i = 0 ; i < residentials.size() ; i++){
            gc.fillOval( residentials.get(i).getLocalization().getX()* scale, residentials.get(i).getLocalization().getY()* scale, 4,4);
        }
    }

    private static void drawVoronoi(GraphicsContext gc , List<KeyPoint> keyPoints , int scale){
        for(int i = 0 ; i < keyPoints.size() ; i ++){
            List<Point> points = keyPoints.get(i).getAreaPoints();
            gc.setFill(Color.color(0.1*i,0.2*i,0.15*i));
            for (int j = 0 ; j < points.size() ; j ++){
                gc.fillOval( Point.scaleCoordinateToInt(points.get(j).getX(), Diagram.X_SIZE)  ,  Point.scaleCoordinateToInt(points.get(j).getY(), Diagram.Y_SIZE)  , 1,1);
            }
        }
    }
}
