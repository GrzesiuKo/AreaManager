package Common;

public class Vector extends Point {
    public Vector(Point start, Point end) {
        super(end.getX() - start.getX(), end.getY() - start.getY());
    }

    public Vector(double start, double end){
        super(start,end);
    }


    private double findCosinusBetween(Vector a, Vector b) {
        //TODO
        return 0;
    }


    private double scalarProduct(Point a, Point b) {
        //TODO
        return 0;
    }

}
