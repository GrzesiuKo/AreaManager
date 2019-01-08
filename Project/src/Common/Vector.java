package Common;

public class Vector extends Point {
    public Vector(Point start, Point end) {
        super(end.getX() - start.getX(), end.getY() - start.getY());
    }

    public Vector(double start, double end){
        super(start,end);
    }


    public double findCosinusBetween(Vector a) {
        double scalar;
        double lengthA;
        double lengthB;
        double result;

        scalar = scalarProduct(this, a);
        lengthA = a.getLength();
        lengthB = getLength();

        result = scalar/(lengthA*lengthB);

        return result;
    }


    private double scalarProduct(Vector a, Vector b) {
        double x, y;

        x = a.getX()*b.getX();
        y = a.getY()*b.getY();

        return x+y;
    }

    private double getLength() {
        //TODO
        return 0;
    }

}
