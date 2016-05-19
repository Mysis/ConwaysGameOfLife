/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conwaysgameoflife;

import javafx.scene.paint.Color;

public final class Constants {
    
    public static final double MAIN_STAGE_WIDTH = 600;
    public static final double MAIN_STAGE_HEIGHT = 600;
    
    public static final double MAIN_UI_WIDTH = MAIN_STAGE_HEIGHT;
    public static final double MAIN_UI_HEIGHT = 100;
    public static final Color MAIN_UI_COLOR = Color.GRAY;
    
    public static final double SCREEN_WIDTH = MAIN_STAGE_WIDTH;
    public static final double SCREEN_HEIGHT = MAIN_STAGE_HEIGHT + MAIN_UI_HEIGHT;
    
    public static final double CELL_SIZE = 25;
    public static final Color CELL_ALIVE_COLOR = Color.WHITE;
    public static final Color CELL_DEAD_COLOR = Color.BLACK;
    public static final Color CELL_STROKE_COLOR = Color.LIGHTBLUE;
    public static final int CELLS_PER_ROW = (int)(MAIN_STAGE_WIDTH / CELL_SIZE);
    public static final int CELLS_PER_COLUMN = (int)(MAIN_STAGE_HEIGHT / CELL_SIZE);
    public static final int CELLS_OUTSIDE_STAGE = 3;
}
