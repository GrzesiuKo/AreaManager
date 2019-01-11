package FileData;

public class UserArgument {
    private String valueString;
    private double valueDouble;
    private int valueInt;
    private int id;

    public UserArgument(int id, String value){
        this.id = id;
        valueString = value;
    }

    public UserArgument(int id, double value){
        this.id = id;
        valueDouble = value;
    }

    public UserArgument(int id, int value){
        this.id = id;
        valueInt = value;
    }

    public int getId() {
        return id;
    }

    public String getString() {
        return valueString;
    }

    public double getDouble() {
        return valueDouble;
    }

    public int getInt() {
        return valueInt;
    }
}
