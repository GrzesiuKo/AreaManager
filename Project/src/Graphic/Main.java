package Graphic;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
        TextField statisticOutput = new TextField("Syyyyf");
        statisticOutput.setEditable(false);
        controlBox.getChildren().addAll(choosers, workBox ,statisticOutput);
        verticalSplit.getChildren().addAll(canvasBox , controlBox);

        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(primaryStage);
                if(file != null){
                   //Output for PARSER -
                }
            }
        });
        Scene scene = new Scene(verticalSplit, 800, 600);
        primaryStage.setTitle("Project");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
