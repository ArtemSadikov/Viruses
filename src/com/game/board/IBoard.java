package com.game.board;

import com.game.cell.Cell;
import com.game.cell.CellType;

public interface IBoard {
    Cell getCell(int x, int y);
    void setCell(int x, int y, CellType type);
    void printBoard();
}
