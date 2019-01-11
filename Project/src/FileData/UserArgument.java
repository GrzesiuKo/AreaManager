package FileData;

public class UserArgument {
    String valueString;
    double valueDouble;
    int valueInt;

    int id;

    public UserArgument(int id){
        this.id = id;
    }

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
}
