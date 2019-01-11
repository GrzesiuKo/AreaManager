package Graphic;

import Statistics.UserObject;
import com.sun.jdi.connect.Connector;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class StatisticOutput {

    public static void print(List<String> objects , Label statisticOutput) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < objects.size() ; i++){
            sb.append(objects.get(i) );
            sb.append("\n");
        }
        statisticOutput.setText(sb.toString());
    }
}
