/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conwaysgameoflife;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class CellContainer {
    
    private List<List<Cell>> cellArray = new ArrayList();
    
    private int width;
    private int height;
    
    public CellContainer(int startWidth, int startHeight, double cellSize) {
        width = startWidth;
        height = startHeight;
        for (int i = 0; i < width; i++) {
            cellArray.add(new ArrayList());
            for (int j = 0; j < height; j++) {
                Cell newCell = new Cell(i * cellSize, j * cellSize, cellSize, false);
                cellArray.get(i).add(newCell);
            }
        }
    }
    
    public void toNextGeneration() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell currentCell = cellArray.get(i).get(j);
                int neighbors = getNeighbors(i, j, currentCell.getIsAlive());
                if (currentCell.getIsAlive()) {
                    if (neighbors < 2) {
                        currentCell.setToKill();
                    } else if (neighbors > 3) {
                        currentCell.setToKill();
                    }
                } else {
                    if (neighbors == 3) {
                        currentCell.setToRevive();
                    }
                }
            }
        }
        updateCells();
        clearOuterBoundary();
    }
    
    private int getNeighbors (int xIndex, int yIndex, boolean isAlive) {
        int neighbors = isAlive? -1 : 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (cellArray.get(xIndex + i).get(yIndex + j).getIsAlive()) {
                        //System.out.println("i: " + i + " j: " + j + " x: " + xIndex + " y: " + yIndex);
                        neighbors += 1;
                    }
                } catch(IndexOutOfBoundsException e) {}
            }
        }
        return neighbors;
    }
    
    private void updateCells() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cellArray.get(i).get(j).updateState();
            }
        }
    }
    
    public void addCellsToGroup(Group group) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                group.getChildren().add(cellArray.get(i).get(j).getShape());
                if (i < Constants.CELLS_OUTSIDE_STAGE || i > width - Constants.CELLS_OUTSIDE_STAGE - 1 || j < Constants.CELLS_OUTSIDE_STAGE || j > width - Constants.CELLS_OUTSIDE_STAGE - 1 ) {
                    cellArray.get(i).get(j).getShape().setVisible(false);
                }
            }
        }
    }
    
    public void clearBoard() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cellArray.get(i).get(j).setToKill();
            }
        }
        updateCells();
    }
    
    public void clearOuterBoundary() {
        for (int i = 0; i < width; i++) {
            cellArray.get(0).get(i).setToKill();
            cellArray.get(height - 1).get(i).setToKill();
        }
        for (int i = 0; i < height; i++) {
            cellArray.get(i).get(0).setToKill();
            cellArray.get(width - 1).get(i).setToKill();
        }
        updateCells();
    }
}
