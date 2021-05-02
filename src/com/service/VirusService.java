package com.service;

import com.game.GameStatus;
import com.game.board.Board;
import com.game.cell.Cell;
import com.game.cell.CellType;
import com.game.player.Player;
import com.game.player.PlayerType;
import jakarta.jws.WebService;

import java.util.ArrayList;

@WebService(endpointInterface = "com.service.IVirusService", serviceName = "VirusService")
public class VirusService implements IVirusService {
    Board board;
    ArrayList<Player> players;
    Player currentPlayer;
    GameStatus gameStatus;

    public VirusService() {
        board = new Board(6);
        players = new ArrayList<>(2);
        gameStatus = GameStatus.NEW_GAME;
    }

    private Player getPlayer(String name) {
        Player res = players.get(0);
        for (Player player : players) {
            if (player.getName().equals(name)) {
                res = player;
            }
        }
        return res;
    }

    @Override
    public Player connectPlayer(String name) throws Exception {
        if (players.size() == 2) {
            throw new Exception("Max players");
        }
        PlayerType type = players.size() == 1 ? PlayerType.CIRCLE : PlayerType.CROSS;
        Player player = new Player(type, name);
        if (players.size() == 0) {
            currentPlayer = player;
        }
        if (players.size() == 1 ) {
            gameStatus = GameStatus.PLAYING;
        }
        int x = players.size() == 1 ? board.getSize() - 1 : 0;
        int y = players.size() == 1 ? board.getSize() - 1 : 0;
        ArrayList<Cell> possibleVariants = new ArrayList<>();
        possibleVariants.add(new Cell(x, y));
        player.setPossibleVariantsToPlace(possibleVariants);
        players.add(player);
        return player;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public boolean getTurn(Player player) {
        return currentPlayer.getName().equals(player.getName());
    }

    @Override
    public void makeMove(String name) {
        for (Player player : players) {
            if (!player.getName().equals(name)) {
                currentPlayer = player;
            }
        }
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public ArrayList<Cell> getPlayerPlacePositions(String name) {
        return getPlayer(name).getPossibleVariantsToPlace();
    }

    @Override
    public void placePlayer(String name, int row, int col) throws Exception {
        if (isCorrectPosition(row, col)) {
            Player player = getPlayer(name);
            CellType type = player.getType() == PlayerType.CROSS ? CellType.CROSS : CellType.CIRCLE;
            Cell newPosition = new Cell(col, row, type);
            board.setCell(newPosition.getY(), newPosition.getX(), newPosition.getType());
            player.addPosition(newPosition);
            ArrayList<Cell> newPossiblePositionsToPlace = new ArrayList<>();
            ArrayList<Cell> newPossiblePositionsToAttack = new ArrayList<>();
            for (Cell position : player.getPositions()) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        int selectedRow = position.getX() + i;
                        int selectedCol = position.getY() + j;
                        if (isCorrectPosition(selectedCol, selectedRow)) {
                            Cell newCell = board.getCell(selectedCol, selectedRow);
                            if (newCell.getType() == CellType.EMPTY) {
                                if (!newPossiblePositionsToPlace.contains(newCell)) {
                                    newPossiblePositionsToPlace.add(newCell);
                                }
                            } else if (newCell.getType() != type) {
                                if (!newPossiblePositionsToAttack.contains(newCell)) {
                                    newPossiblePositionsToAttack.add(newCell);
                                }
                            }
                        }
                    }
                }
            }
            player.setPossibleVariantsToPlace(newPossiblePositionsToPlace);
            player.setPossibleVariantsToAttack(newPossiblePositionsToAttack);
        } else {
            throw new Exception("Failed to place");
        }
    }

    @Override
    public void skipTurn(String name) {

    }

    @Override
    public ArrayList<Cell> getPlayerAttackPositions(String name) {
        return getPlayer(name).getPossibleVariantsToAttack();
    }

    @Override
    public void attack(String name, int row, int col) {

    }

    private boolean isCorrectPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < board.getSize() && y < board.getSize();
    }
}
