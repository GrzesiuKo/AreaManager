package Graphic;

import Statistics.UserObject;
import com.sun.jdi.connect.Connector;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class StatisticOutput {

    public static void print(List<UserObject> objects , Label statisticOutput) throws Exception {
        if(objects.isEmpty()){
            throw new Exception();
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < objects.size() ; i++){
            sb.append(objects.get(i).toString() );
            sb.append("\n");
        }
        statisticOutput.setText(sb.toString());
    }
}
