package Statistics;

import Common.KeyPoint;
import Common.Point;
import Exceptions.EmptyKeyPointsList;

import java.security.Key;
import java.util.*;

public class Statistics {
    private static Statistics instance = null;
    private List<Bear> bearList;
    private List<School> schoolList;
    private List<Residential> residentialList;
    private List<Moose> mooseList;

    private Statistics() {
        bearList = new LinkedList<>();
        schoolList = new LinkedList<>();
        residentialList = new LinkedList<>();
        mooseList = new LinkedList<>();
    }

    public static Statistics getInstance() {
        if (instance == null) {
            instance = new Statistics();
        }
        return instance;
    }

    public static void delete() {
        instance = null;
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

    public void addMoose(Moose moose) {
        mooseList.add(moose);
    }

    public List<String> printAllObjectList(Point fromUser, List<KeyPoint> keyPoints) throws EmptyKeyPointsList {
        List<String> result = new LinkedList<>();
        if (keyPoints.isEmpty()) {
            throw new EmptyKeyPointsList();
        }
        KeyPoint closes = findKeyPoint(fromUser, keyPoints);
        for (int i = 0; i < bearList.size(); i++) {
            if (bearList.get(i).getMemberOf().equals(closes)) {
                result.add(bearList.get(i).toString());
            }
        }

        for (int i = 0; i < residentialList.size(); i++) {
            if (residentialList.get(i).getMemberOf().equals(closes)) {
                result.add(residentialList.get(i).toString());
            }
        }


        for (int i = 0; i < schoolList.size(); i++) {
            if (schoolList.get(i).getMemberOf().equals(closes)) {
                result.add(schoolList.get(i).toString());
            }
        }

        for (int i = 0; i < mooseList.size(); i++) {
            if (mooseList.get(i).getMemberOf().equals(closes)) {
                result.add(mooseList.get(i).toString());
            }
        }

        return result;
    }

    public List<String> printGroupObjectList(Point fromUser, List<KeyPoint> keyPoints) throws EmptyKeyPointsList {
        if (keyPoints.isEmpty()) {
            throw new EmptyKeyPointsList();
        }
        Map<String, Integer> values = new HashMap<>();
        KeyPoint closes = findKeyPoint(fromUser, keyPoints);
        List<String> result = new LinkedList<>();
        UserObject current;
        for (int i = 0; i < bearList.size(); i++) {
            current = bearList.get(i);
            check(current, values, closes);
        }
        for (int i = 0; i < residentialList.size(); i++) {
            current = residentialList.get(i);
            check(current, values, closes);
        }
        for (int i = 0; i < schoolList.size(); i++) {
            current = schoolList.get(i);
            check(current, values, closes);
        }
        for (int i = 0; i < mooseList.size(); i++) {
            current = mooseList.get(i);
            check(current, values, closes);
        }

        Iterator it = values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            result.add(pair.getKey().toString() + " " + pair.getValue());
            it.remove();
        }

        return result;
    }

    private void check(UserObject current, Map<String, Integer> values, KeyPoint closes) {
        Integer counter = 0;
        if (current.getMemberOf().equals(closes)) {
            if (values.containsKey(current.getObjectName())) {
                counter = values.get(current.getObjectName()) + 1;
                values.replace(current.getObjectName(), counter);
            } else {
                values.put(current.getObjectName(), 1);
            }
        }
    }

    public List<String> printResidentsNumber(Point fromUser , List<KeyPoint> keyPoints) throws EmptyKeyPointsList {
        if (keyPoints.isEmpty()) {
            throw new EmptyKeyPointsList();
        }
        int counter = 0;
        KeyPoint closes = findKeyPoint(fromUser, keyPoints);
        Residential current;
        List<String> result = new LinkedList<>();
        for (int i = 0; i < residentialList.size(); i++) {
            current = residentialList.get(i);
            if(current.getLocalization().equals(closes) && current.getObjectName().equals("DOM")){
                counter = counter + current.getPopulation();
            }
        }
        result.add("Liczba mieszkańców: " + counter);
        return result;
    }

    public void recheckData(List<KeyPoint> keyPoints, boolean[][] area) {
        UserObject object = null;
        for (int i = 0; i < bearList.size(); i++) {
            object = bearList.get(i);
            bearList.get(i).setMemberOf(findKeyPoint(object.getLocalization(), keyPoints));
//            if (!area[(int) object.getLocalization().getX() ][(int) object.getLocalization().getY() ]) {
//                deleteObject(i, "Bear");
//            }
        }

        for (int i = 0; i < residentialList.size(); i++) {
            residentialList.get(i).setMemberOf(findKeyPoint(residentialList.get(i).getLocalization(), keyPoints));
//            if (!area[(int) object.getLocalization().getX() * 10][(int) object.getLocalization().getY() * 10]) {
//                deleteObject(i, "Residential");
//            }
        }

        for (int i = 0; i < schoolList.size(); i++) {
            schoolList.get(i).setMemberOf(findKeyPoint(schoolList.get(i).getLocalization(), keyPoints));
//            if (!area[(int) object.getLocalization().getX() * 10][(int) object.getLocalization().getY() * 10]) {
//                deleteObject(i, "School");
//            }
        }

        for (int i = 0; i < mooseList.size(); i++) {
            mooseList.get(i).setMemberOf(findKeyPoint(mooseList.get(i).getLocalization(), keyPoints));
        }
    }

    public void deleteObject(int index, String objectName) {
        if (objectName.equals("Bear")) {
            bearList.remove(index);
        } else if (objectName.equals("Residential")) {
            residentialList.remove(index);
        } else if (objectName.equals("School")) {
            schoolList.remove(index);
        }
    }

    public static KeyPoint findKeyPoint(Point fromUser, List<KeyPoint> keyPoints) {
        if (keyPoints.isEmpty()) {
            return null;
        }
        double distance = Math.sqrt(Math.pow(fromUser.getX() - keyPoints.get(0).getX(), 2) + Math.pow(fromUser.getY() - keyPoints.get(0).getY(), 2));
        KeyPoint nearest = keyPoints.get(0);
        for (int i = 0; i < keyPoints.size(); i++) {
            if (Math.sqrt(Math.pow(fromUser.getX() - keyPoints.get(i).getX(), 2) + Math.pow(fromUser.getY() - keyPoints.get(i).getY(), 2)) < distance) {
                distance = Math.sqrt(Math.pow(fromUser.getX() - keyPoints.get(i).getX(), 2) + Math.pow(fromUser.getY() - keyPoints.get(i).getY(), 2));
                nearest = keyPoints.get(i);
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

    public List<Moose> getMooseList() {
        return mooseList;
    }
}
