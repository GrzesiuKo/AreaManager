package Common;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point ) {
            return this.getX() == ((Point) obj).getX() && this.getY() == ((Point) obj).getY();
        } else {
            return false;
        }
    }

    public int scaleCoordinateToInt(double a, int precision){
        a*=precision;
        a = Math.round(a);
        return (int) a;
    }

}
