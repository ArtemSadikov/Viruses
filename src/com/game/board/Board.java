package com.game.board;

import com.game.cell.Cell;
import com.game.cell.CellType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Arrays;

@XmlRootElement(name = "board")
public class Board implements IBoard, Serializable {
    private int size;
    private Cell[][] board;
    private boolean isGameOver;

    public Board() {
    }

    public Board(int size) {
        this.board = new Cell[size][size];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = new Cell(row, col);
            }
        }
        isGameOver = false;
        this.size = size;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getBoard() {
        return board;
    }

    @Override
    public Cell getCell(int x, int y) {
        return board[x][y];
    }

    @Override
    public void setCell(int x, int y, CellType type) {
        board[x][y].setType(type);
    }

    @Override
    public void printBoard() {
        System.out.print("  ");
        for (int row = 0; row < board.length; row++) {
            System.out.print(row + 1 + " ");
        }
        System.out.println();
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length + 1; column++) {
                if (column != 0) {
                    System.out.print(board[row][column - 1].getType().getType() + " ");
                } else {
                    System.out.print((row + 1) + " ");
                }
            }
            System.out.println();
        }
    }
}
