package Graphic;

import Common.Point;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class DrawingLogic {

    public static void drawCountour(GraphicsContext gc, List<Point> countour) {
        gc.setLineWidth(1);
        double[] xPoints = new double[countour.size()];
        double[] yPoints = new double[countour.size()];
        for (int i = 0; i < countour.size(); i++) {
            xPoints[i] = countour.get(i).getX();
            yPoints[i] = countour.get(i).getY();
        }
        gc.strokePolygon(xPoints, yPoints, countour.size());
    }
}
