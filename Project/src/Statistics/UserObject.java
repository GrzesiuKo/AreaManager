package Statistics;

import Common.Point;

public class UserObject {
    private Point localization ;
    private Point memberOf ;

    public UserObject(Point point){
        this.localization = point;
        this.memberOf = checkMembership();
    }

    public static void addObject(Point point, String objectName){
        Statistics st = Statistics.getInstance();
        st.addBear(new Bear(point));
    }

    public static void addObject(Point point, String objectName, String name){
        Statistics st = Statistics.getInstance();
        st.addSchool(new School(point , name));
    }

    public static void addObject(Point point, String objectName, int value){
        Statistics st = Statistics.getInstance();
        st.addResidentail(new Residential(point , value));
    }

    private Point checkMembership() {
        return null;
    }

    @Override
    public String toString() {
        return "x: " + String.valueOf(localization.getX()) + "  y: " + String.valueOf(localization.getY());
    }
}
