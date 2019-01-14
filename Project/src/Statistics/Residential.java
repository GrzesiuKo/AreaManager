package Statistics;

import Common.Point;

public class Residential extends UserObject {
    private int population = 0;

    public Residential(Point point, String objectName ,int population) {
        super(point , objectName);
        this.population = population;
    }

    @Override
    public String toString() {
        return "Budynek mieszkalny " + super.toString() + " Mieszkańców: " + String.valueOf(population);
    }
}
