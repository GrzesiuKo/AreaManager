package Graphic;

import Common.Point;
import Diagram.Diagram;
import FileData.FileReader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Rozmieszczenie kontrolek
        HBox verticalSplit = new HBox(5);
        VBox canvasBox = new VBox(5);
        Pane canvasPane = new Pane();
        VBox controlBox = new VBox(5);
        HBox choosers = new HBox(5);
        HBox workBox = new HBox(5);

        //Dodanie kontrolek
        Canvas canvas = new Canvas(300, 300);
        canvas.setVisible(true);
        canvasPane.getChildren().addAll(canvas);
        Button selectFileButton = new Button("Wybierz plik danych");
        Button selectImageButton = new Button("Wybierz obraz tła");
        choosers.getChildren().addAll(selectFileButton, selectImageButton);
        Label workType = new Label("Wybierz tryb pracy: ");
        ChoiceBox selectWorkType = new ChoiceBox(FXCollections.observableArrayList("Wyświetlanie statystyk", "Edycja konturów", "Edycja punktów kluczowych"));
        workBox.getChildren().addAll(workType, selectWorkType);
        TextField statisticOutput = new TextField("Syyyyf");
        statisticOutput.setEditable(false);
        controlBox.getChildren().addAll(choosers, workBox, statisticOutput);
        verticalSplit.getChildren().addAll(canvasPane, controlBox);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("lista obiektów należących do obszaru");
        MenuItem menuItem2 = new MenuItem("pogrupowana typami lista obiektów");
        MenuItem menuItem3 = new MenuItem("liczba mieszkańców danego obszaru");
        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem2);
        contextMenu.getItems().add(menuItem3);

        //Dodanie eventów
        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Pliki tekstowe", "*.txt"));

                File file = fileChooser.showOpenDialog(primaryStage);

                if (file != null) {
                    Diagram diagram = new Diagram(file);
                    DrawingLogic.draw(gc, diagram);
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

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        if (selectWorkType.getValue() == null) {
                            Alert workTypeAlert = new Alert(Alert.AlertType.INFORMATION);
                            workTypeAlert.setTitle("Błąd");
                            workTypeAlert.setHeaderText(null);
                            workTypeAlert.setContentText("Nie wybrano trybu pracy");
                            workTypeAlert.showAndWait();
                        } else if (selectWorkType.getValue().equals("Wyświetlanie statystyk")) {
                            contextMenu.show(canvas , me.getScreenX(), me.getScreenY());
                        } else if (selectWorkType.getValue().equals("Edycja konturów")) {

                        } else if (selectWorkType.getValue().equals("Edycja punktów kluczowych")) {

                        }
                    }
                });

        Scene scene = new Scene(verticalSplit);
        primaryStage.setTitle("Project");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
