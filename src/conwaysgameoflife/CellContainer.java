/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conwaysgameoflife;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;

public class CellContainer {
    
    private List<List<Cell>> cellArray = new ArrayList();
    
    public CellContainer(int width, int height, double cellSize) {
        for (int i = 0; i < width; i++) {
            cellArray.add(new ArrayList());
            for (int j = 0; j < height; j++) {
                Cell newCell = new Cell(i * cellSize, j * cellSize, cellSize, false);
                cellArray.get(i).add(newCell);
            }
        }
    }
    
    public void toNextGeneration() {
        for (int i = 0; i < cellArray.size(); i++) {
            for (int j = 0; j < cellArray.get(i).size(); j++) {
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
    
    public void updateCells() {
        for (int i = 0; i < cellArray.size(); i++) {
            for (int j = 0; j < cellArray.get(i).size(); j++) {
                cellArray.get(i).get(j).updateState();
            }
        }
    }
    
    public void addCellsToGroup(Group group) {
        for (int i = 0; i < cellArray.size(); i++) {
            for (int j = 0; j < cellArray.get(i).size(); j++) {
                group.getChildren().add(cellArray.get(i).get(j).getShape());
            }
        }
    }
    
    public void clearBoard() {
        for (int i = 0; i < cellArray.size(); i++) {
            for (int j = 0; j < cellArray.get(i).size(); j++) {
                cellArray.get(i).get(j).setToKill();
            }
        }
        updateCells();
    }
}
