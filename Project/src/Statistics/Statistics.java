package Statistics;

import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private List<Bear> bearList;
    private List<School> schoolList;
    private List<Residential> residentialList;
    private static Statistics instance = null ;

    private Statistics(){
        bearList = new ArrayList<>();
        schoolList = new ArrayList<>();
        residentialList = new ArrayList<>();
    }

    public static Statistics getInstance(){
        if(instance == null){
           instance = new Statistics();
        }
        return instance;
    }

    public void addSchool(School school){
        schoolList.add(school);
    }
    public void addBear(Bear bear){
        bearList.add(bear);
    }
    public void addResidentail(Residential residential){
        residentialList.add(residential);
    }

    @Override
    public String toString() {
        return ("Bears: " + bearList.toString() + " Schools: " + schoolList.toString() + " Residentials" + residentialList.toString());
    }
}
