package com.game.player;

import com.game.cell.Cell;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

@XmlRootElement(name = "player")
public class Player implements IPlayer, Serializable {
    String name;
    PlayerType type;
    ArrayList<Cell> positions = new ArrayList<>();
    ArrayList<Cell> possibleVariantsToPlace = new ArrayList<>();
    ArrayList<Cell> possibleVariantsToAttack = new ArrayList<>();

    public void setPossibleVariantsToPlace(ArrayList<Cell> possibleVariantsToPlace) {
        this.possibleVariantsToPlace = possibleVariantsToPlace;
    }

    public void setPossibleVariantsToAttack(ArrayList<Cell> possibleVariantsToAttack) {
        this.possibleVariantsToAttack = possibleVariantsToAttack;
    }

    public ArrayList<Cell> getPossibleVariantsToPlace() {
        return possibleVariantsToPlace;
    }

    public ArrayList<Cell> getPossibleVariantsToAttack() {
        return possibleVariantsToAttack;
    }

    public Player() {
    }

    public Player(PlayerType type, String name) {
        this.type = type;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(PlayerType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public PlayerType getType() {
        return type;
    }

    public ArrayList<Cell> getPositions() {
        return positions;
    }

    public void addPosition(Cell position) {
        positions.add(position);
    }

    public void setPositions(ArrayList<Cell> positions) {
        this.positions = positions;
    }
}
