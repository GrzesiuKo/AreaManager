package Graphic;

import javafx.scene.canvas.GraphicsContext;

public class DrawingLogic {

    public static void drawLine(GraphicsContext gc, double x, double y) {
        gc.setLineWidth(1);
        gc.strokeLine(40, 10, 10, 40);
    }
}
