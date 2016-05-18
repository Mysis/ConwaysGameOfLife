/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conwaysgameoflife;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.shape.Rectangle;

public class Cell {

    private Rectangle shape;
    private BooleanProperty isAlive = new SimpleBooleanProperty();
    private boolean toKill;
    private boolean toRevive;

    public Cell(double x, double y, double cellSize, boolean shouldStartAlive) {
        isAlive.set(shouldStartAlive);
        
        shape = new Rectangle(x, y, cellSize, cellSize);
        shape.setOnMouseClicked(e -> {
            isAlive.set(!isAlive.get());
            //System.out.println(isAlive.get());
                });
        shape.setStroke(Constants.CELL_STROKE_COLOR);
        shape.fillProperty().bind(Bindings.when(isAlive).then(Constants.CELL_ALIVE_COLOR).otherwise(Constants.CELL_DEAD_COLOR));
    }

    public void setToKill() {
        toKill = true;
        toRevive = false;
    }

    public void setToRevive() {
        toRevive = true;
        toKill = false;
    }

    public void updateState() {
        if (toKill) {
            isAlive.set(false);
            //System.out.println("killing cell");
            //shape.setFill(Constants.CELL_DEAD_COLOR);
        } else if (toRevive) {
            isAlive.set(true);
            //shape.setFill(Constants.CELL_ALIVE_COLOR);
        }
        toKill = false;
        toRevive = false;
    }

    public boolean getIsAlive() {
        return isAlive.get();
    }

    public Rectangle getShape() {
        return shape;
    }
}
