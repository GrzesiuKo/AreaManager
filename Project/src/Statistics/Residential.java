package Statistics;

import Common.Point;

public class Residential extends UserObject {
    private int population = 0;

    public Residential(Point point, int population) {
        super(point);
        this.population = population;
    }

    @Override
    public String toString() {
        return super.toString() + " " + String.valueOf(population);
    }
}
