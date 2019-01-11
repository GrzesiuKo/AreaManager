package Graphic;

import Common.KeyPoint;
import Common.Point;
import Diagram.Diagram;
import Diagram.IncorrectFileException;
import Diagram.InvalidContourException;
import Statistics.Statistics;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class Main extends Application {
    Diagram diagram = null;
    DrawingLogic drawing = null;

    public static void main(String[] args) {
        launch(args);
    }

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
        Canvas canvas = new Canvas(600, 600);
        canvas.setVisible(true);
        canvasPane.getChildren().addAll(canvas);
        Button selectFileButton = new Button("Wybierz plik danych");
        Button selectImageButton = new Button("Wybierz obraz tła");
        choosers.getChildren().addAll(selectFileButton, selectImageButton);
        Label workType = new Label("Wybierz tryb pracy: ");
        ChoiceBox selectWorkType = new ChoiceBox(FXCollections.observableArrayList("Wyświetlanie statystyk", "Edycja konturów", "Dodawanie punktów kluczowych" , "Usuwanie punktów kluczowych"));
        workBox.getChildren().addAll(workType, selectWorkType);
        Label statisticOutput = new Label();
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
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setTitle("Błąd");
        errorAlert.setHeaderText(null);
        //Dodanie eventów
        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Pliki tekstowe", "*.txt"));

                File file = fileChooser.showOpenDialog(primaryStage);

                if (file != null) {
                    try {
                        diagram = new Diagram();
                        diagram.readFile(file);
                    } catch (IncorrectFileException e) {
                        errorAlert.setContentText("W linii: " + String.valueOf(e.getLine() + " pliku " + file.getName() + " wystąpił bład.\n Proszę wybrać inny plik lub naprawić błąd w podanej linii."));
                        errorAlert.showAndWait();
                    } catch (InvalidContourException e) {
                        errorAlert.setContentText("Zadeklarowany w pliku kontur nie jest wielobokiem wypukłym");
                        errorAlert.showAndWait();
                    }
                    if (diagram != null) {
                        drawing = new DrawingLogic(gc, diagram);
                        drawing.draw();
                    }

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
                        Image image = new Image(targetStream, canvasPane.getWidth(), canvasPane.getHeight(), true, false );
                        BackgroundSize backgroundSize = new BackgroundSize(canvasPane.getWidth(),canvasPane.getHeight(),false,false, false , false);
                        BackgroundImage canvasBackground = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                        canvasPane.setBackground(new Background(canvasBackground));
                    } catch (FileNotFoundException e) {
                        errorAlert.setContentText("Wczytywanie obrazu nie udało się");
                        errorAlert.showAndWait();
                    }
                }
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        if (selectWorkType.getValue() == null) {
                            errorAlert.setContentText("Nie wybrano trybu pracy");
                            errorAlert.showAndWait();
                        } else if (diagram == null) {
                            errorAlert.setContentText("Nie wczytano danych");
                            errorAlert.showAndWait();
                        } else if (!drawing.inContour(new Point(me.getSceneX(), me.getSceneY()))) {
                            errorAlert.setContentText("Wybrano punkt poza konturem");
                            errorAlert.showAndWait();
                        } else if (selectWorkType.getValue().equals("Wyświetlanie statystyk")) {
                            contextMenu.show(canvas, me.getScreenX(), me.getScreenY());
                            menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent e) {
                                    try {
                                        StatisticOutput.print( Statistics.getInstance().printAllObjectList(new Point(me.getSceneX() / 6, me.getSceneY() / 6) , diagram.getKeyPoints()) , statisticOutput);
                                    } catch (Exception e1) {
                                        errorAlert.setContentText("Nie istnieją żaden punkt kluczowy");
                                        errorAlert.showAndWait();
                                    }
                                }
                            });
                            menuItem2.setOnAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent e) {
                                    Statistics.getInstance().printGroupObjectList(new Point(me.getSceneX(), me.getSceneY()));
                                }
                            });
                            menuItem3.setOnAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent e) {
                                    Statistics.getInstance().printResidentsNumber(new Point(me.getSceneX(), me.getSceneY()));
                                }
                            });

                        } else if (selectWorkType.getValue().equals("Edycja konturów")) {
                        } else if (selectWorkType.getValue().equals("Dodawanie punktów kluczowych")) {
                            diagram.addKeyPoint(new KeyPoint(Math.round(me.getSceneX() / drawing.getScale()), Math.round(me.getSceneY() / drawing.getScale())));
                            drawing = new DrawingLogic(gc, diagram);
                            drawing.draw();
                        }else if(selectWorkType.getValue().equals("Usuwanie punktów kluczowych")){
                            KeyPoint point = drawing.checkTwojaStara(new KeyPoint(Math.round(me.getSceneX() / drawing.getScale()), Math.round(me.getSceneY() / drawing.getScale())));
                            if(point != null){
                                diagram.deleteKeyPoint(point);
                                drawing.draw();
                            }
                        }
                    }
                });

        Scene scene = new Scene(verticalSplit);
        primaryStage.setTitle("Project");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
