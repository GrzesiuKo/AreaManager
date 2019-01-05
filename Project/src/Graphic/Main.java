package Graphic;

import Common.Point;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Rozmieszczenie kontrolek
        HBox verticalSplit = new HBox(5);
        VBox canvasBox = new VBox(5);
        VBox controlBox = new VBox(5);
        HBox choosers = new HBox(5);
        HBox workBox = new HBox(5);

        //Dodanie kontrolek
        Canvas canvas = new Canvas(500, 500);
        canvas.setVisible(true);
        canvasBox.getChildren().addAll(canvas);
        Button selectFileButton = new Button("Wybierz plik danych");
        Button selectImageButton = new Button("Wybierz obraz tła");
        choosers.getChildren().addAll(selectFileButton, selectImageButton);
        Label workType = new Label("Wybierz tryb pracy: ");
        ChoiceBox selectWorkType = new ChoiceBox();
        workBox.getChildren().addAll(workType, selectWorkType);
        TextField statisticOutput = new TextField("Syyyyf");
        statisticOutput.setEditable(false);
        controlBox.getChildren().addAll(choosers, workBox, statisticOutput);
        verticalSplit.getChildren().addAll(canvasBox, controlBox);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Dodanie eventów
        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Pliki tekstowe", "*.txt"));

                File file = fileChooser.showOpenDialog(primaryStage);

                if (file != null) {
                    //Output for PARSER -
                }
            }
        });

        selectImageButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Obrazy", "*.png"));
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    InputStream targetStream = null;
                    try {
                        targetStream = new FileInputStream(file);

                        BackgroundImage myBI = new BackgroundImage(new Image(targetStream, canvasBox.getWidth(), canvasBox.getHeight(), false, false),
                                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT);
                        canvasBox.setBackground(new Background(myBI));
                    } catch (FileNotFoundException e) {
                        //TODO Okienko błedu
                    }
                }
            }
        });

        Scene scene = new Scene(verticalSplit, 800, 600);
        List<Point> tmp = new ArrayList<>();
//        tmp.add(new Point(10,10));
//        tmp.add(new Point(100,100));
//        tmp.add(new Point(300,100));
//        DrawingLogic.drawCountour(gc,tmp);
        primaryStage.setTitle("Project");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
