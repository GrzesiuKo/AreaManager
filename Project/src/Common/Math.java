package Common;

import Diagram.Arc;

public class Math {

    public static class TwoResults {
        double first;
        double second;

        public TwoResults(double resultFirst, double resultSecond){
            first = resultFirst;
            second = resultSecond;
        }

        public double getBigger(){
            return java.lang.Math.max(first, second);
        }

        public double getSmaller(){
            return java.lang.Math.min(first, second);
        }
    }

    public static TwoResults findCommonXofTwoArcs(Arc first, Arc second, double currentYofSweepLine){
        Point f = first.getFocus();
        Point s = second.getFocus();

        double mf = 2*(f.getY()-currentYofSweepLine);
        double af = 1/mf;
        double bf = (-2*f.getX())/mf;
        double cf = (java.lang.Math.pow(f.getX(), 2)+ java.lang.Math.pow(f.getY(), 2) - java.lang.Math.pow(currentYofSweepLine, 2))/mf;

        double ms = 2*(s.getY()-currentYofSweepLine);
        double as = 1/ms;
        double bs = (-2*s.getX())/ms;
        double cs = (java.lang.Math.pow(s.getX(), 2)+ java.lang.Math.pow(s.getY(), 2) - java.lang.Math.pow(currentYofSweepLine, 2))/ms;

        double a = af-as;
        double b = bf-bs;
        double c = cf-cs;

        double delta = b*b - 4*a*c;
        double firstResult = (-b - java.lang.Math.sqrt(delta))/(2*a);
        double secondResult = (-b + java.lang.Math.sqrt(delta))/(2*a);

        return new TwoResults(firstResult, secondResult);
    }


}
