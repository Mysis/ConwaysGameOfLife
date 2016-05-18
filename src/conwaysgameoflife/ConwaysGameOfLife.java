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
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ConwaysGameOfLife extends Application {

    private Group root;
    private HBox uiContainer;

    private CellContainer cells;
    
    private Timeline mainTimeline;
    BooleanProperty isPlaying = new SimpleBooleanProperty(false);
    
    private Button nextButton;
    private Button autoButton;
    private TextField timeField;

    @Override
    public void start(Stage primaryStage) {

        root = new Group();

        cells = new CellContainer(Constants.CELLS_PER_ROW, Constants.CELLS_PER_COLUMN);
        
        nextButton = new Button("Next Generation");
        nextButton.setOnAction(e -> {
            cells.toNextGeneration();
            cells.updateCells();
        });
        autoButton = new Button("Toggle Auto Advance");
        autoButton.textProperty().bind((Bindings.when(isPlaying).then("Stop Autoplay").otherwise("Start Autoplay")));
        autoButton.setOnAction(e -> {
            if (!isPlaying.get()) {
                isPlaying.set(true);
                mainTimeline = new Timeline(new KeyFrame(new Duration(Double.parseDouble(timeField.textProperty().get())), t -> {
                    cells.toNextGeneration();
                    cells.updateCells();
                }));
                mainTimeline.setCycleCount(Timeline.INDEFINITE);
                mainTimeline.playFromStart();
            } else {
                mainTimeline.stop();
                isPlaying.set(false);
            }
        });
        timeField = new TextField("1000");
        
        cells.addCellsToGroup(root);
        uiContainer = new HBox(nextButton, autoButton, timeField);
        uiContainer.setLayoutX(30);
        uiContainer.setLayoutY(Constants.MAIN_STAGE_HEIGHT + 12);
        uiContainer.setSpacing(10);
        root.getChildren().add(uiContainer);

        Scene scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, Color.GREY);

        primaryStage.setTitle("Conways Game Of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
