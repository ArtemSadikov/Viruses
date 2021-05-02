package com.game.cell;

public enum CellType {
    EMPTY('?'), CROSS('X'), CIRCLE('O');

    char type;
    CellType(char type) {
        this.type = type;
    }
    public char getType() { return type; }
}
