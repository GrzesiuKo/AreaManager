package Graphic;

import Common.KeyPoint;
import Common.Point;
import Diagram.Diagram;
import Exceptions.IncorrectDefinitionUnknownTypeException;
import Exceptions.IncorrectLineException;
import Exceptions.IncorrectObjectLineException;
import Exceptions.InvalidContourException;
import Statistics.Statistics;
import javafx.application.Application;
import javafx.collections.FXCollections;
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
    private Diagram diagram = null;
    private DrawingLogic drawing = null;

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
        ChoiceBox selectWorkType = new ChoiceBox(FXCollections.observableArrayList("Wyświetlanie statystyk", "Dodawanie punktów konturu", "Usuwanie punktów konturu", "Dodawanie punktów kluczowych", "Usuwanie punktów kluczowych"));
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
        Alert errorAlert = new Alert(Alert.AlertType.WARNING);
        errorAlert.setTitle("Błąd");
        errorAlert.setHeaderText(null);
        errorAlert.setResizable(true);
        Alert statisticsWindow = new Alert(Alert.AlertType.INFORMATION);
        //Dodanie eventów
        selectFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Pliki tekstowe", "*.txt"));

            File file = fileChooser.showOpenDialog(primaryStage);

            if (file != null) {
                boolean exception = false;
                try {
                    diagram = new Diagram();
                    diagram.readFile(file);
                } catch (IncorrectLineException e) {
                    errorAlert.setContentText("W linii: " + String.valueOf(e.getLineNumber() + " pliku " + file.getName() + " wystąpił bład.\n Proszę wybrać inny plik lub naprawić błąd w podanej linii."));
                    exception = true;
                } catch (InvalidContourException e) {
                    errorAlert.setContentText("Zadeklarowany w pliku kontur nie jest wielobokiem wypukłym");
                    exception = true;
                } catch (IncorrectObjectLineException e) {
                    if (e.getDefinition() == null) {
                        errorAlert.setContentText("Obiekt deklarowany w linii: \"" + e.getLine() + "\" jest niezdefiniowany");
                    } else {
                        errorAlert.setContentText("Obiekt deklarowany w linii: \"" + e.getLine() + "\" jest niezgodny z definicją: \"" + e.getDefinition() + "\"");
                    }
                    exception = true;
                } catch (IncorrectDefinitionUnknownTypeException e) {
                    errorAlert.setContentText("Nieprawidłowy typ obiektu \"" + e.getType() + "\" w linii: \"" + e.getLine() + "\"");
                    exception = true;
                }
                if (exception) {
                    DrawingLogic.clear(gc);
                    errorAlert.showAndWait();
                } else {
                    drawing = new DrawingLogic(gc, diagram);
                    drawing.draw();
                }
            }
        });

        selectImageButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Obrazy", "*.png"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                InputStream targetStream;
                try {
                    targetStream = new FileInputStream(file);
                    Image image = new Image(targetStream, canvasPane.getWidth(), canvasPane.getHeight(), true, false);
                    BackgroundSize backgroundSize = new BackgroundSize(canvasPane.getWidth(), canvasPane.getHeight(), false, false, false, false);
                    BackgroundImage canvasBackground = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                    canvasPane.setBackground(new Background(canvasBackground));
                } catch (FileNotFoundException e) {
                    errorAlert.setContentText("Wczytywanie obrazu nie udało się");
                    errorAlert.showAndWait();
                }
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                me -> {
                    if (drawing != null) {
                        Point actual = new Point(me.getSceneX() / drawing.getScale(), me.getSceneY() / drawing.getScale());
                        if (selectWorkType.getValue() == null) {
                            errorAlert.setContentText("Nie wybrano trybu pracy");
                            errorAlert.showAndWait();
                        } else if (diagram == null) {
                            errorAlert.setContentText("Nie wczytano danych");
                            errorAlert.showAndWait();
                        } else if (selectWorkType.getValue().equals("Dodawanie punktów konturu")) {
                            diagram.addContourPoint(actual);
                            drawing = new DrawingLogic(gc, diagram);
                            drawing.draw();
                        } else if (!drawing.inContour(new Point(me.getSceneX(), me.getSceneY()))) {
                            errorAlert.setContentText("Wybrano punkt poza konturem");
                            errorAlert.showAndWait();
                        } else if (selectWorkType.getValue().equals("Wyświetlanie statystyk")) {
                            contextMenu.show(canvas, me.getScreenX(), me.getScreenY());
                            menuItem1.setOnAction(e -> {
                                try {
                                    StatisticOutput.print(Statistics.getInstance().printAllObjectList(actual, diagram.getKeyPoints()), statisticOutput);
                                } catch (Exception e1) {
                                    errorAlert.setContentText("Nie istnieje żaden punkt kluczowy");
                                    errorAlert.showAndWait();
                                }
                            });
                            menuItem2.setOnAction(e -> {
                                try {
                                    StatisticOutput.print(Statistics.getInstance().printGroupObjectList(actual, diagram.getKeyPoints()), statisticOutput);
                                } catch (Exception e1) {
                                    errorAlert.setContentText("Nie istnieje żaden punkt kluczowy");
                                    errorAlert.showAndWait();
                                }
                            });
                            menuItem3.setOnAction(e -> {
                                try {
                                    StatisticOutput.print(Statistics.getInstance().printResidentsNumber(actual, diagram.getKeyPoints()), statisticOutput);
                                } catch (Exception e1) {
                                    errorAlert.setContentText("Nie istnieje żaden punkt kluczowy");
                                    errorAlert.showAndWait();
                                }
                            });

                        } else if (selectWorkType.getValue().equals("Dodawanie punktów kluczowych")) {
                            diagram.addKeyPoint(new KeyPoint(Math.round(me.getSceneX() / drawing.getScale()), Math.round(me.getSceneY() / drawing.getScale())));
                            drawing = new DrawingLogic(gc, diagram);
                            drawing.draw();
                        } else if (selectWorkType.getValue().equals("Usuwanie punktów kluczowych")) {
                            KeyPoint point = drawing.findKeyPoint(new KeyPoint(Math.round(me.getSceneX() / drawing.getScale()), Math.round(me.getSceneY() / drawing.getScale())));
                            if (point != null) {
                                diagram.deleteKeyPoint(point);
                                drawing.draw();
                            }
                        } else if (selectWorkType.getValue().equals("Usuwanie punktów konturu")) {
                            Point point = drawing.findCounturPoint(new Point(Math.round(me.getSceneX() / drawing.getScale()), Math.round(me.getSceneY() / drawing.getScale())));
                            if (point != null) {
                                diagram.getContour().getContourPoints().remove(point);
                                drawing = new DrawingLogic(gc, diagram);
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
