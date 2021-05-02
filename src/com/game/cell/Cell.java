package com.game.cell;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "cell")
public class Cell implements ICell, Serializable {
    private int x;
    private int y;
    private CellType type;

    public Cell() {
        type = CellType.EMPTY;
    }

    public Cell(int col, int row, CellType type) {
        this.x = col;
        this.y = row;
        this.type = type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = CellType.EMPTY;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
}
