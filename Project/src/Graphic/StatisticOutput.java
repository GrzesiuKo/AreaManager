package Graphic;

import javafx.scene.control.Label;

import java.util.List;

public class StatisticOutput {

    public static void print(List<String> objects , Label statisticOutput) {
        StringBuilder sb = new StringBuilder();
        for (String object : objects) {
            sb.append(object);
            sb.append("\n");
        }
        statisticOutput.setText(sb.toString());
    }
}
