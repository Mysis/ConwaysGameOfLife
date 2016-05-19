/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conwaysgameoflife;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ConwaysGameOfLife extends Application {

    private Group root = new Group();
    private Group cellGroup = new Group();
    private Rectangle uiBackground;
    private VBox uiMainContainer;
    private HBox uiContainerA;
    private HBox uiContainerB;

    private CellContainer cells;

    private Timeline mainTimeline;
    private BooleanProperty isPlaying = new SimpleBooleanProperty(false);

    private Button nextButton;
    private Button autoButton;
    private Label timeLabel;
    private TextField timeField;
    private Button updateTime;
    private Button clearButton;
    private TextField changeCellSizeField;
    private Button changeCellSizeButton;

    @Override
    public void start(Stage primaryStage) {

        cells = new CellContainer((int)(Constants.MAIN_STAGE_WIDTH / Constants.CELL_SIZE) + Constants.CELLS_OUTSIDE_STAGE * 2, (int)(Constants.MAIN_STAGE_HEIGHT / Constants.CELL_SIZE) + Constants.CELLS_OUTSIDE_STAGE * 2, Constants.CELL_SIZE);
        cells.addCellsToGroup(cellGroup);
        cellGroup.setLayoutX(-Constants.CELLS_OUTSIDE_STAGE * Constants.CELL_SIZE);
        cellGroup.setLayoutY(-Constants.CELLS_OUTSIDE_STAGE * Constants.CELL_SIZE);

        nextButton = new Button("Next Generation");
        nextButton.setOnAction(e -> {
            cells.toNextGeneration();
        });
        autoButton = new Button("Toggle Auto Advance");
        autoButton.textProperty().bind((Bindings.when(isPlaying).then("Stop Autoplay").otherwise("Start Autoplay")));
        autoButton.setOnAction(e -> {
            if (!isPlaying.get()) {
                isPlaying.set(true);
                mainTimeline = new Timeline(new KeyFrame(new Duration(Double.parseDouble(timeField.textProperty().get())), t -> {
                    cells.toNextGeneration();
                }));
                mainTimeline.setCycleCount(Timeline.INDEFINITE);
                mainTimeline.playFromStart();
            } else {
                mainTimeline.stop();
                isPlaying.set(false);
            }
        });
        timeLabel = new Label("Delay (ms):");
        timeField = new TextField("1000");
        updateTime = new Button("Update time");
        updateTime.setOnAction(e -> {
            if (isPlaying.get()) {
                mainTimeline.stop();
                mainTimeline = new Timeline(new KeyFrame(new Duration(Double.parseDouble(timeField.textProperty().get())), t -> {
                    cells.toNextGeneration();
                }));
                mainTimeline.setCycleCount(Timeline.INDEFINITE);
                mainTimeline.playFromStart();
            }
        });
        clearButton = new Button("Clear");
        clearButton.setOnAction(e -> cells.clearBoard());
        changeCellSizeField = new TextField("25");
        changeCellSizeButton = new Button("Change size");
        changeCellSizeButton.setOnAction(e -> {
            double newSize = Double.parseDouble(changeCellSizeField.getText());
            if (newSize >= 5) {
                cellGroup.getChildren().clear();
                cells = new CellContainer((int)(Constants.MAIN_STAGE_WIDTH / newSize) + Constants.CELLS_OUTSIDE_STAGE * 2, (int)(Constants.MAIN_STAGE_HEIGHT / newSize) + Constants.CELLS_OUTSIDE_STAGE * 2, newSize);
                cells.addCellsToGroup(cellGroup);
                cellGroup.setLayoutX(-Constants.CELLS_OUTSIDE_STAGE * newSize);
                cellGroup.setLayoutY(-Constants.CELLS_OUTSIDE_STAGE * newSize);
                //cellGroup.setLayoutX(100);
                //cellGroup.setLayoutY(100);
            }
        });
        uiBackground = new Rectangle(0, 0, Constants.SCREEN_WIDTH, 500);
        uiBackground.setFill(Constants.MAIN_UI_COLOR);
        uiContainerA = new HBox(nextButton, autoButton, timeLabel, timeField, updateTime);
        uiContainerA.setSpacing(10);
        uiContainerB = new HBox(clearButton, changeCellSizeField, changeCellSizeButton);
        uiContainerB.setSpacing(10);
        uiMainContainer = new VBox(uiContainerA, uiContainerB, uiBackground);
        uiMainContainer.setSpacing(12);
        uiMainContainer.setLayoutX(30);
        uiMainContainer.setLayoutY(Constants.MAIN_STAGE_HEIGHT + 12);
        root.getChildren().add(cellGroup);
        root.getChildren().add(uiMainContainer);
        Scene scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, Color.GREY);

        primaryStage.setTitle("Conways Game Of Life");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
