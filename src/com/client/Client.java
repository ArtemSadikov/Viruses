package com.client;

import com.game.GameStatus;
import com.game.cell.Cell;
import com.game.player.Player;
import com.game.player.PlayerMoveType;
import com.service.IVirusService;
import jakarta.xml.ws.Service;

import javax.xml.namespace.QName;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class Client {
    static final int REQUEST_TIME = 5000;

    private static PlayerMoveType castToPlayerType(int value) {
        switch (value) {
            case 0:
                return PlayerMoveType.PLACE;
            case 1:
                return PlayerMoveType.ATTACK;
            case 2:
                return PlayerMoveType.SKIP;
        }

        return PlayerMoveType.ERROR;
    }

    public static void main(String[] args) {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(System.getProperty("user.dir") + "/src/com/sample.config"));
            String serverEndpoint = prop.getProperty("server.endpoint");
            URL url = new URL(serverEndpoint + "?wsdl");
            QName qName = new QName("http://service.com/", "VirusService");
            Service service = Service.create(url, qName);
            IVirusService virusService = service.getPort(IVirusService.class);
            Scanner userInput = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String name = userInput.nextLine();
            Player player = virusService.connectPlayer(name);
            System.out.println("You're connected!");
            PlayerMoveType moveType;
            boolean isWaiting = false;
            while (!virusService.isGameOver()) {
                if (virusService.getTurn(player) && virusService.getGameStatus() == GameStatus.PLAYING) {
                    isWaiting = false;
                    System.out.println("Your move!");
                    for (int turn = 0; turn < 3; turn++) {
                        virusService.getBoard().printBoard();
                        System.out.println("0 - PLACE, 1 - ATTACK, 2 - SKIP");
                        int inputValue = Integer.parseInt(userInput.nextLine());
                        moveType = castToPlayerType(inputValue);
                        ArrayList<Cell> positions = new ArrayList<>();
                        switch (moveType) {
                            case PLACE:
                                positions = virusService.getPlayerPlacePositions(name);
                                break;
                            case ATTACK:
                                positions = virusService.getPlayerAttackPositions(name);
                                break;
                            case SKIP:
                                turn = 3;
                                break;
                            case ERROR:
                                break;
                        }
                        if (turn != 3 && moveType != PlayerMoveType.ERROR) {
                            System.out.println("Possible variants");
                            for (Cell pos : positions) {
                                System.out.print((pos.getX() + 1) + "" + (pos.getY() + 1) + " ");
                            }
                            System.out.println();
                            System.out.print("Type position: ");
                            int move = Integer.parseInt(userInput.nextLine());
                            int row = move / 10 - 1;
                            int col = move % 10 - 1;
                            if (moveType == PlayerMoveType.PLACE) {
                                virusService.placePlayer(name, row, col);
                            } else if (moveType == PlayerMoveType.ATTACK) {
                                virusService.attack(name, row, col);
                            }
                        }
                    }
                    virusService.makeMove(name);
                    System.out.println("Turn ended!");
                } else {
                    if (!isWaiting) {
                        System.out.println("Waiting for another player...");
                        isWaiting = true;
                    }
                }
                Thread.sleep(REQUEST_TIME);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
