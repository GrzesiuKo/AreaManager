package Common;

public class Point {
    private double x;
    private double y;
    private KeyPoint keyPoint;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static int scaleCoordinateToInt(double a, int precision) {
        a *= precision;
        a = Math.round(a);
        return (int) a;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            return this.getX() == ((Point) obj).getX() && this.getY() == ((Point) obj).getY();
        } else {
            return false;
        }
    }

    public Point getSmaller(Point p) {
        if (p == null) {
            return this;
        }
        if (getY() < p.getY()) {
            return this;
        } else if (getY() > p.getY()) {
            return p;
        } else if (getY() == p.getY()) {
            if (getX() < p.getX()) {
                return this;
            } else if (getX() > p.getX()) {
                return p;
            } else if (getX() == p.getX()) {
                return this;
            }
        }
        return this;
    }

    public Point getBigger(Point p) {
        if (p == null) {
            return this;
        }
        if (getY() > p.getY()) {
            return this;
        } else if (getY() < p.getY()) {
            return p;
        } else if (getY() == p.getY()) {
            if (getX() > p.getX()) {
                return this;
            } else if (getX() < p.getX()) {
                return p;
            } else if (getX() == p.getX()) {
                return this;
            }
        }
        return this;
    }

    public boolean isHigherThan(Point p){
        return y-p.getY()>0;
    }

    public boolean isLowerThan(Point p){
        return y-p.getY()<0;
    }

    public boolean hasTheSameHeightAs(Point p){
        return y == p.getY();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("x = ");
        sb.append(x);
        sb.append(" y = ");
        sb.append(y);

        return sb.toString();
    }

    public KeyPoint getKeyPoint() {
        return keyPoint;
    }

    public void setKeyPoint(KeyPoint keyPoint) {
        this.keyPoint = keyPoint;
    }
}
