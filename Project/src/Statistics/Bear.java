package Statistics;

import Common.Point;

public class Bear extends UserObject {

    public Bear(Point point , String objectName) {
        super(point , objectName);
    }

    @Override
    public String toString() {
        return "Bear " + super.toString();
    }
}
