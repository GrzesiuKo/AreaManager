package Statistics;

import Common.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Statistics {
    private static Statistics instance = null;
    private List<Bear> bearList;
    private List<School> schoolList;
    private List<Residential> residentialList;

    private Statistics() {
        bearList = new LinkedList<>();
        schoolList = new LinkedList<>();
        residentialList = new LinkedList<>();
    }

    public static Statistics getInstance() {
        if (instance == null) {
            instance = new Statistics();
        }
        return instance;
    }

    public void addSchool(School school) {
        schoolList.add(school);
    }

    public void addBear(Bear bear) {
        bearList.add(bear);
    }

    public void addResidentail(Residential residential) {
        residentialList.add(residential);
    }

    public void printAllObjectList(Point fromUser) {
    }

    public void printGroupObjectList(Point fromUser) {
    }

    public void printResidentsNumber(Point fromUser) {
    }

    public void recheckData() {
    }

    public void deleteObject(int index , String objectName){
        if(objectName.equals("Bear")){
            bearList.remove(index);
        }
        else if(objectName.equals("Residential")){
            residentialList.remove(index);
        }

        else if(objectName.equals("School")){
            schoolList.remove(index);
        }
    }

    private Point findKeyPoint(Point fromUser) {
        List<Point> tmp = new ArrayList<Point>();
        tmp.add(new Point(1, 1));
        tmp.add(new Point(100, 100));
        double distance = Math.sqrt(Math.pow(fromUser.getX() - tmp.get(0).getX(), 2) + Math.pow(fromUser.getY() - tmp.get(0).getY(), 2));
        Point nearest = tmp.get(0);
        for (int i = 0; i < tmp.size(); i++) {
            if (Math.sqrt(Math.pow(fromUser.getX() - tmp.get(i).getX(), 2) + Math.pow(fromUser.getY() - tmp.get(i).getY(), 2)) < distance) {
                distance = Math.sqrt(Math.pow(fromUser.getX() - tmp.get(i).getX(), 2) + Math.pow(fromUser.getY() - tmp.get(i).getY(), 2));
                nearest = tmp.get(i);
            }
        }
        return nearest;
    }

    @Override
    public String toString() {
        return ("Bears: " + bearList.toString() + " Schools: " + schoolList.toString() + " Residentials" + residentialList.toString());
    }

    public List<Bear> getBearList() {
        return bearList;
    }

    public List<School> getSchoolList() {
        return schoolList;
    }

    public List<Residential> getResidentialList() {
        return residentialList;
    }

}
