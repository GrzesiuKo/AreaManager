package Statistics;

import Common.KeyPoint;
import Common.Point;
import Exceptions.EmptyKeyPointsList;

import java.util.*;

public class Statistics {
    private static Statistics instance = null;
    private List<Bear> bearList;
    private List<School> schoolList;
    private List<Residential> residentialList;
    private List<Moose> mooseList;
    private List<Point> occupied;

    private Statistics() {
        bearList = new LinkedList<>();
        schoolList = new LinkedList<>();
        residentialList = new LinkedList<>();
        mooseList = new LinkedList<>();
        occupied = new ArrayList<>();
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
        if (fromUser == null) {
            return null;
        }
        KeyPoint closes = findKeyPoint(fromUser, keyPoints);
        for (Bear aBearList : bearList) {
            if (aBearList.getMemberOf().equals(closes)) {
                result.add(aBearList.toString());
            }
        }

        for (Residential aResidentialList : residentialList) {
            if (aResidentialList.getMemberOf().equals(closes)) {
                result.add(aResidentialList.toString());
            }
        }


        for (School aSchoolList : schoolList) {
            if (aSchoolList.getMemberOf().equals(closes)) {
                result.add(aSchoolList.toString());
            }
        }

        for (Moose aMooseList : mooseList) {
            if (aMooseList.getMemberOf().equals(closes)) {
                result.add(aMooseList.toString());
            }
        }

        return result;
    }

    public List<String> printGroupObjectList(Point fromUser, List<KeyPoint> keyPoints) throws EmptyKeyPointsList {
        if (keyPoints.isEmpty()) {
            throw new EmptyKeyPointsList();
        }
        if (fromUser == null) {
            return null;
        }
        Map<String, Integer> values = new HashMap<>();
        KeyPoint closes = findKeyPoint(fromUser, keyPoints);
        List<String> result = new LinkedList<>();
        UserObject current;
        for (Bear aBearList : bearList) {
            current = aBearList;
            check(current, values, closes);
        }
        for (Residential aResidentialList : residentialList) {
            current = aResidentialList;
            check(current, values, closes);
        }
        for (School aSchoolList : schoolList) {
            current = aSchoolList;
            check(current, values, closes);
        }
        for (Moose aMooseList : mooseList) {
            current = aMooseList;
            check(current, values, closes);
        }

        Iterator it = values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            result.add(pair.getKey().toString() + " " + pair.getValue());
            it.remove();
        }

        return result;
    }

    private void check(UserObject current, Map<String, Integer> values, KeyPoint closes) {
        Integer counter;
        if (current.getMemberOf().equals(closes)) {
            if (values.containsKey(current.getObjectName())) {
                counter = values.get(current.getObjectName()) + 1;
                values.replace(current.getObjectName(), counter);
            } else {
                values.put(current.getObjectName(), 1);
            }
        }
    }

    public List<String> printResidentsNumber(Point fromUser, List<KeyPoint> keyPoints) throws EmptyKeyPointsList {
        if (keyPoints.isEmpty()) {
            throw new EmptyKeyPointsList();
        }
        if (fromUser == null) return null;
        else {
            int counter = 0;
            KeyPoint closes = findKeyPoint(fromUser, keyPoints);
            Residential current;
            List<String> result = new LinkedList<>();
            for (Residential aResidentialList : residentialList) {
                if (aResidentialList.getMemberOf().equals(closes) && aResidentialList.getObjectName().equals("DOM")) {
                    counter = counter + aResidentialList.getPopulation();
                }
            }
            result.add("Liczba mieszkańców: " + counter);
            return result;
        }
    }

    public void recheckData(List<KeyPoint> keyPoints, boolean[][] area) {
        UserObject object;
        for (Bear aBearList : bearList) {
            aBearList.setMemberOf(findKeyPoint(aBearList.getLocalization(), keyPoints));
        }

        for (Residential aResidentialList : residentialList) {
            aResidentialList.setMemberOf(findKeyPoint(aResidentialList.getLocalization(), keyPoints));
        }

        for (School aSchoolList : schoolList) {
            aSchoolList.setMemberOf(findKeyPoint(aSchoolList.getLocalization(), keyPoints));
        }

        for (Moose aMooseList : mooseList) {
            aMooseList.setMemberOf(findKeyPoint(aMooseList.getLocalization(), keyPoints));
        }
    }

    public void deleteObject(UserObject object, String objectName) {
        switch (objectName) {
            case "Moose":
                mooseList.remove(object);
                occupied.remove(object.getLocalization());
                break;
            case "Bear":
                bearList.remove(object);
                occupied.remove(object.getLocalization());
                break;
            case "Residential":
                residentialList.remove(object);
                occupied.remove(object.getLocalization());
                break;
            case "School":
                schoolList.remove(object);
                occupied.remove(object.getLocalization());
                break;
        }
    }

    public static KeyPoint findKeyPoint(Point fromUser, List<KeyPoint> keyPoints) {
        if (keyPoints.isEmpty()) {
            return null;
        }
        if (fromUser == null) {
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

    public List<Point> getOccupied() {
        return occupied;
    }
}
