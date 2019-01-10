package Statistics;

import Common.KeyPoint;
import Common.Point;

public class UserObject {
    private Point localization;
    private KeyPoint memberOf;

    public UserObject(Point point) {
        this.localization = point;
        this.memberOf = null;
    }

    public static void addObject(Point point, String objectName) {
        Statistics st = Statistics.getInstance();
        st.addBear(new Bear(point));
    }

    public static void addObject(Point point, String objectName, String name) {
        Statistics st = Statistics.getInstance();
        st.addSchool(new School(point, name));
    }

    public static void addObject(Point point, String objectName, int value) {
        Statistics st = Statistics.getInstance();
        st.addResidentail(new Residential(point, value));
    }

    @Override
    public String toString() {
        return  String.valueOf(localization.toString() );
    }

    public Point getLocalization() {
        return localization;
    }

    public KeyPoint getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(KeyPoint memberOf) {
        this.memberOf = memberOf;
    }
}
