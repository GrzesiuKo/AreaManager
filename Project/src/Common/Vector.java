package Common;

public class Vector extends Point {
    public Vector(Point start, Point end) {
        super(end.getX() - start.getX(), end.getY() - start.getY());
    }

    public Vector(double start, double end) {
        super(start, end);
    }

    public double findAngleBetween(Vector a) {
        double cosinus, result;

        cosinus = findCosinusBetween(a);
        result = Math.acos(cosinus);

        return result;
    }

    public double findCosinusBetween(Vector a) {
        double scalar;
        double lengthA;
        double lengthB;
        double result;

        scalar = scalarProduct(this, a);
        lengthA = a.getLength();
        lengthB = getLength();

        result = scalar / (lengthA * lengthB);

        return result;
    }


    private double scalarProduct(Vector a, Vector b) {
        double x, y;

        x = a.getX() * b.getX();
        y = a.getY() * b.getY();

        return x + y;
    }

    private double getLength() {
        double a = Math.pow(this.getX(), 2);
        double b = Math.pow(this.getY(), 2);
        return Math.sqrt(a + b);
    }

}
