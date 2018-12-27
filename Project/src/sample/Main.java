package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        HBox verticalSplit = new HBox(5);
        VBox canvasBox = new VBox(5);
        VBox controlBox = new VBox(5);
        HBox choosers = new  HBox(5);
        HBox workBox = new HBox(5);
        Canvas canvas = new Canvas(500,500);
        canvasBox.getChildren().addAll(canvas);
        Button selectFileButton = new Button("Wybierz plik danych");
        Button selectImageButton = new Button("Wybierz obraz tła");
        choosers.getChildren().addAll(selectFileButton, selectImageButton);
        Label workType = new Label("Wybierz tryb pracy: ");
        ChoiceBox selectWorkType = new ChoiceBox();
        workBox.getChildren().addAll(workType,selectWorkType);
        TextArea statisticOutput = new TextArea("Syyyyf");
        controlBox.getChildren().addAll(choosers, workBox ,statisticOutput);
        verticalSplit.getChildren().addAll(canvasBox , controlBox);

        Scene scene = new Scene(verticalSplit, 800, 600);
        primaryStage.setTitle("Project");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
