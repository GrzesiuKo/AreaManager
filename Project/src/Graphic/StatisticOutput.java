package Graphic;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.util.List;

public class StatisticOutput {

    public static void print(List<String> objects , Alert statisticOutput) {
        StringBuilder sb = new StringBuilder();
        for (String object : objects) {
            sb.append(object);
            sb.append("\n");
        }
        statisticOutput.setContentText(sb.toString());
        statisticOutput.setHeaderText(null);
        statisticOutput.setTitle("Statystyki");
        statisticOutput.show();
    }
}
