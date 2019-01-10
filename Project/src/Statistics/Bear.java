package Statistics;

import Common.Point;

public class Bear extends UserObject {

    public Bear(Point point) {
        super(point);
    }

    @Override
    public String toString() {
        return "Bear " + super.toString();
    }
}
