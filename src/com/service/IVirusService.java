package com.service;

import com.game.GameStatus;
import com.game.board.Board;
import com.game.cell.Cell;
import com.game.player.Player;
import com.game.player.PlayerMoveType;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.ArrayList;

@WebService(serviceName = "com.service.IVirusService")
public interface IVirusService {
    @WebMethod
    Player connectPlayer(@WebParam(name = "name") String name) throws Exception;

    @WebMethod
    Board getBoard();

    @WebMethod
    boolean getTurn(@WebParam(name = "name") Player name);

    @WebMethod
    boolean isGameOver();

    @WebMethod
    GameStatus getGameStatus();

    @WebMethod
    void makeMove(@WebParam(name = "name") String name);

    @WebMethod
    ArrayList<Cell> getPlayerPlacePositions(@WebParam(name = "name") String name);

    @WebMethod
    void placePlayer(@WebParam(name = "name") String name, @WebParam(name = "row") int row, @WebParam(name = "col") int col) throws Exception;

    @WebMethod
    void skipTurn(@WebParam(name = "name") String name);

    @WebMethod
    ArrayList<Cell> getPlayerAttackPositions(@WebParam(name = "name") String name);

    @WebMethod
    void attack(@WebParam(name = "name") String name, @WebParam(name = "row") int row, @WebParam(name = "col") int col);
}
