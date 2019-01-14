package Statistics;

import Common.KeyPoint;
import Common.Point;

public class UserObject {
    private Point localization;
    private KeyPoint memberOf;
    private String objectName;

    public UserObject(Point point, String objectName) {
        this.localization = point;
        this.memberOf = null;
        this.objectName = objectName;
    }

    public static void addObject(Point point, String objectName) {
        Statistics st = Statistics.getInstance();
        st.addBear(new Bear(point, objectName));
    }

    public static void addObject(Point point, String objectName, String name) {
        Statistics st = Statistics.getInstance();
        st.addSchool(new School(point, objectName, name));
    }

    public static void addObject(Point point, String objectName, int value) {
        Statistics st = Statistics.getInstance();
        st.addResidentail(new Residential(point, objectName, value));
    }

    public static void addObject(Point point, String objectName, double value) {
        Statistics st = Statistics.getInstance();
        st.addMoose(new Moose(point, objectName, value));
    }

    @Override
    public String toString() {
        return String.valueOf(objectName + " " + localization.toString());
    }

    public Point getLocalization() {
        return localization;
    }

    public KeyPoint getMemberOf() {
        return memberOf;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setMemberOf(KeyPoint memberOf) {
        this.memberOf = memberOf;
    }
}
