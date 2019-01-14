package Statistics;

import Common.Point;

public class Residential extends UserObject {
    private int population = 0;

    public Residential(Point point, String objectName ,int population) {
        super(point , objectName);
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return  super.toString() + " " + String.valueOf(population);
    }
}
